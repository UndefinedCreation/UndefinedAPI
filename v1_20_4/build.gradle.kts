import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.github.patrick.gradle.remapper.RemapTask

plugins {
    kotlin("jvm") version "1.9.22"
    id("io.github.patrick.remapper") version "1.4.2"
}

val versionVar = version
val groupIdVar = "com.redmagic"
val artifactIdVar = "UndefinedAPI"


repositories {
}


dependencies {
    compileOnly("org.spigotmc:spigot:1.20.4-R0.1-SNAPSHOT:remapped-mojang")

    implementation(project(":api"))
}



tasks {

    assemble {
        dependsOn("remap")
    }

    remap {
        version.set("1.20.4")
        action.set(RemapTask.Action.MOJANG_TO_SPIGOT)
        archiveName.set("${project.name}-${versionVar}-remapped.jar")
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

}
kotlin{
    jvmToolchain(17)
}
