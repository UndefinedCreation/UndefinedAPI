plugins {
    kotlin("jvm") version "1.9.22"
    id("io.papermc.paperweight.userdev") version "1.7.1"
}

val versionVar = version
val groupIdVar = "com.redmagic"
val artifactIdVar = "UndefinedAPI"

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.REOBF_PRODUCTION

repositories {
}



dependencies {


    paperweight.paperDevBundle("1.20.5-R0.1-SNAPSHOT")

    implementation(project(":api"))
}



tasks {

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

}

java {
    disableAutoTargetJvm()
}

kotlin{

    jvmToolchain(17)
}
