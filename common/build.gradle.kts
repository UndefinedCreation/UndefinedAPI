plugins {
    kotlin("jvm") version "1.9.22"
}

val versionVar = version
val groupIdVar = "com.redmagic"
val artifactIdVar = "UndefinedAPI"


repositories {
}


dependencies {
    implementation(project(":api"))
    implementation(project(":v1_20_4"))
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
