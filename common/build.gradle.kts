plugins {
    kotlin("jvm") version "1.9.22"
}

val versionVar = version
val groupIdVar = "com.redmagic"
val artifactIdVar = "UndefinedAPI"


repositories {
}


dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")

    compileOnly(project(":api"))
    compileOnly(project(":v1_20_4"))
    compileOnly(project(":v1_20_5"))
    compileOnly(project(":v1_20_6"))
    compileOnly(project(":v1_21"))
}


tasks {

    compileKotlin {
        kotlinOptions.jvmTarget = "21"
    }

}
kotlin{
    jvmToolchain(21)
}
