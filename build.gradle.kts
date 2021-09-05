plugins {
    java
    `java-library`
}

group = "io.github.nickacpt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    api("com.github.stefvanschie.inventoryframework:IF:0.10.1")
    compileOnly("org.jetbrains:annotations:16.0.2")
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
}