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

    implementation(project(":api"))
    implementation(project(":v1_20_4"))
}


tasks {

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

}
kotlin{
    jvmToolchain(17)
}
