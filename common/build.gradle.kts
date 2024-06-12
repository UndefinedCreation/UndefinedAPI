plugins {
    kotlin("jvm") version "1.9.22"
}

val versionVar = version
val groupIdVar = "com.redmagic"
val artifactIdVar = "UndefinedAPI"


repositories {
}


dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.6-R0.1-SNAPSHOT")

    implementation(project(":api"))
    implementation(project(":v1_20_4"))
    implementation(project(":v1_20_5"))
}


tasks {

    compileKotlin {
        kotlinOptions.jvmTarget = "21"
    }

}
kotlin{
    jvmToolchain(21)
}
