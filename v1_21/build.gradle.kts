plugins{
    kotlin("jvm") version "1.9.22"
    id("com.undefinedcreation.mapper") version "0.0.4"
}


dependencies {

    compileOnly("org.spigotmc:spigot:1.21-R0.1-SNAPSHOT:remapped-mojang")

    compileOnly(project(":api"))
}

tasks {

    build {
        dependsOn(remap)
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "21"
    }

    remap {
        mcVersion.set("1.21")
    }


}

java {
    disableAutoTargetJvm()
}

kotlin{
    jvmToolchain(21)
}





