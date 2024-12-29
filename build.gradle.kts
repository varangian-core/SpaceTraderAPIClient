plugins {
    `java`
    `application`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    // Optionally, include more Jackson modules if needed, e.g.:
    // implementation("com.fasterxml.jackson.module:jackson-module-parameter-names:2.15.2")
    // implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

application {
    mainClass.set("com.chibiware.spacetraders.Main")
}

tasks.test {
    useJUnitPlatform()
}
