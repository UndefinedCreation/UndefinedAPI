plugins {
    java
    kotlin("jvm") version "1.9.22"
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.papermc.paperweight.userdev") version "1.5.10"
    id("xyz.jpenilla.run-paper") version "2.2.2"
}

apply(plugin = "maven-publish")

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.redmagic"
            artifactId = "UndefinedAPI"
            version = "1.0.0"

            from(components["java"])
        }
    }
}

group = "com.redmagic"
version = "1.0.1"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.reflections:reflections:0.9.11")
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