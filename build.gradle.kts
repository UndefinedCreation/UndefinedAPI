import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    java
    `maven-publish`
    kotlin("jvm") version "1.9.22"
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"

}

apply(plugin = "maven-publish")
val versionVar = "0.5.02"
val groupIdVar = "com.redmagic"
val artifactIdVar = "UndefinedAPI"

version = groupIdVar

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
        }
    }
}


allprojects {

    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    group = groupIdVar
    version = versionVar


    publishing {
        publications{
            register<MavenPublication>("pubMaven") {
                groupId = groupIdVar
                artifactId = artifactIdVar
                version = versionVar

                from(components["java"])
            }
        }
    }

    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven {
            name = "sonatype"
            url = uri("https://oss.sonatype.org/content/groups/public/")
        }
    }

    dependencies {

        compileOnly("org.spigotmc:spigot-api:1.20.6-R0.1-SNAPSHOT")

        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation("net.kyori:adventure-platform-bukkit:4.3.2")
        implementation("net.kyori:adventure-text-minimessage:4.16.0")
        implementation("org.json:json:20231013")
        implementation("com.googlecode.json-simple:json-simple:1.1.1")
    }

}


dependencies {
    implementation(project(":common"))
    implementation(project(":v1_20_4:", "reobf"))
    implementation(project(":v1_20_5:", "reobf"))
    implementation(project(":v1_20_6:", "reobf"))
    implementation(project(":api"))
}

tasks {

    assemble{
        dependsOn("shadowJar")
    }

    jar.configure {
        dependsOn("shadowJar")
        archiveClassifier.set("dev")
    }

    withType<ShadowJar> {
        archiveClassifier.set("mapped")
        archiveFileName.set("${project.name}-${project.version}.jar")
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "21"
    }
}


kotlin{
    jvmToolchain(21)
}
