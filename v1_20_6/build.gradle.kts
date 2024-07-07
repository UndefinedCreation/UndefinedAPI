plugins{
    kotlin("jvm") version "1.9.22"
    id("com.undefinedcreation.mapper") version "0.0.3"
}

dependencies {

    compileOnly("org.spigotmc:spigot:1.20.6-R0.1-SNAPSHOT:remapped-mojang")

    compileOnly(project(":api"))
}

tasks {

    compileKotlin {
        kotlinOptions.jvmTarget = "21"
    }

    remap {
        mcVersion.set("1.20.6")
    }

}

java {
    disableAutoTargetJvm()
}

kotlin{
    jvmToolchain(21)
}





