 plugins {
    kotlin("jvm") version "1.9.22"
    id("xyz.jpenilla.run-paper") version "2.2.2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val versionVar = version
val groupIdVar = "com.redmagic"
val artifactIdVar = "UndefinedAPI"


repositories {
}



dependencies {

    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")

    implementation(project(":common"))
    implementation(project(":api"))
    implementation(project(":v1_20_4:", "reobf"))
    implementation(project(":v1_20_5:", "reobf"))
    implementation(project(":v1_21:", "reobf"))
    compileOnly(project(":v1_20_4"))
}



tasks {

    assemble {
        dependsOn(shadowJar)
    }

    val shadowJar by getting(com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class) {
        archiveFileName.set("UndefinedAPI-shadow.jar")


    }

    compileKotlin {
        kotlinOptions.jvmTarget = "21"
    }

    runServer {
        minecraftVersion("1.20.4")
    }

}

java {
    disableAutoTargetJvm()
}

kotlin{

    jvmToolchain(21)
}
