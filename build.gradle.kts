import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.URI
import java.nio.file.Files
import java.nio.file.Paths

plugins {
    `java`
    `application`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

application {
    mainClass.set("com.chibiware.spacetraders.Main")
}

tasks.test {
    useJUnitPlatform()
}

// 2.1 Read properties from gradle.properties
val agentSymbol: String by project
val faction: String by project
val email: String by project
val accountExists: String by project
val agentToken: String by project

// 2.2 Create a custom task for SpaceTraders account creation
tasks.register("createSpaceTradersAccount") {
    group = "spacetraders"
    description = "If account does not exist, register and store the new token."

    doLast {
        if (accountExists.toBoolean()) {
            println("Account already exists (accountExists=true). Skipping registration.")
            return@doLast
        }

        println("Account does NOT exist. Attempting registration with agentSymbol='$agentSymbol', faction='$faction'")

        // Prepare JSON payload
        val payload = """
            {
              "symbol": "$agentSymbol",
              "faction": "$faction",
              "email": "$email"
            }
        """.trimIndent()

        // Send the request
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.spacetraders.io/v2/register"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(payload))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() == 201) {
            println("Registration successful! Parsing token...")

            // Very minimal parse to extract the token from JSON:
            // e.g. {"data":{"token":"xxxxxxxxxxxxx","agent":{"symbol":"MYAGENT999",...},...}}
            val body = response.body()
            val tokenRegex = """"token":"([^"]+)"""".toRegex()
            val match = tokenRegex.find(body)

            if (match != null) {
                val newToken = match.groups[1]?.value ?: ""
                println("Got token: $newToken")

                // Now we update gradle.properties with accountExists=true and agentToken=newToken
                // We'll parse the existing file lines, modify them in-memory, then overwrite
                val propFile = file("gradle.properties")
                val lines = propFile.readLines().toMutableList()

                // Helper function to replace or add a property
                fun setProperty(key: String, value: String) {
                    val index = lines.indexOfFirst { it.startsWith("$key=") }
                    if (index >= 0) {
                        lines[index] = "$key=$value"
                    } else {
                        lines.add("$key=$value")
                    }
                }

                setProperty("accountExists", "true")
                setProperty("agentToken", newToken)

                // Write lines back to gradle.properties
                propFile.writeText(lines.joinToString("\n"))
                println("Updated gradle.properties: accountExists=true, agentToken=$newToken")

            } else {
                println("ERROR: Could not find 'token' in the JSON response:\n$body")
            }
        } else {
            println("Registration failed. HTTP status: ${response.statusCode()}")
            println("Response body: ${response.body()}")
        }
    }
}
