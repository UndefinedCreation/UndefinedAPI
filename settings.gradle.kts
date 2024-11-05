rootProject.name = "UndefinedAPI"
include("common", "v1_20_4", "api", "v1_20_6", "server", "v1_21", "v1_21_3")
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}