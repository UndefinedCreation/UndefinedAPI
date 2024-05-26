plugins {
    java
    kotlin("jvm") version "1.9.22"
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.2.2"
    id("org.jetbrains.dokka") version "1.9.20"
    id("io.papermc.paperweight.userdev") version "1.5.12"
}

apply(plugin = "maven-publish")
val versionVar = "0.4.41"
val groupIdVar = "com.redmagic"
val artifactIdVar = "UndefinedAPI"

publishing {
    repositories {
        maven {
            name = "repo.undefinedcreation.com"
            url = uri("https://repo.undefinedcreation.com/repo")
            credentials(PasswordCredentials::class) {
                username = System.getenv("MAVEN_NAME")
                password = System.getenv("MAVEN_SECRET")
            }
        }
    }

    publications {
        register<MavenPublication>("maven") {
            groupId = groupIdVar
            artifactId = artifactIdVar
            version = versionVar
            from(components["java"])
            artifact(tasks.reobfJar) {
                classifier = "spigotMapped"
            }
        }
    }
}

group = groupIdVar
version = versionVar

repositories {
    mavenCentral()
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")

    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.reflections:reflections:0.9.11")
    implementation("net.kyori:adventure-platform-bukkit:4.3.2")
    implementation("net.kyori:adventure-text-minimessage:4.16.0")
    implementation("org.json:json:20171018")

    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
}


tasks {

    assemble {
        dependsOn(reobfJar)
    }

    shadowJar {
        archiveFileName.set("UndefinedAPI-shadow.jar")
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    runServer {
        minecraftVersion("1.20.4")
    }
}




kotlin{
    jvmToolchain(17)
}
