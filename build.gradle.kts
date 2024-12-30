plugins {
    `java`
    `application`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    // Optionally, add more Jackson modules or other libraries.

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

application {
    mainClass.set("com.chibiware.spacetraders.Main")
}

tasks.test {
    useJUnitPlatform()
}
