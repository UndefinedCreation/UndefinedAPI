rootProject.name = "UndefinedAPI"
include("common", "v1_20_4", "api", "v1_20_5", "server")
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}