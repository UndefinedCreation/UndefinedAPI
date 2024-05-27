rootProject.name = "UndefinedAPI"
include("common", "v1_20_4", "api")
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}