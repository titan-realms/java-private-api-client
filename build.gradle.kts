plugins {
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.freefair.lombok") version "6.4.3"
}

group = "net.titanrealms.api"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    api("redis.clients:jedis:4.2.2")

    implementation("com.google.guava:guava:31.1-jre")

    api("com.google.code.gson:gson:2.9.0")
    api("org.mongodb:bson:4.6.0")

    api("org.jetbrains:annotations:23.0.0")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "net.titanrealms.api"
            artifactId = "java-client"
            version = "1.0"

            from(components["java"])
        }
    }
}