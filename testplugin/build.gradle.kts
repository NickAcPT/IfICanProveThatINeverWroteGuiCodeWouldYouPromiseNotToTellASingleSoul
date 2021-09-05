plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    implementation(rootProject)
}

tasks {
    shadowJar {
        destinationDirectory.set(File("""D:\IdeaProjects\IfICanProveThatINeverWroteGuiCodeWouldYouPromiseNotToTellASingleSoul\testplugin\run\plugins"""))
    }
}