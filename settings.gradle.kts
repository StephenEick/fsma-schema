rootProject.name = "fsma-server"

dependencyResolutionManagement {

    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            files("./gradle/libs.versions.toml")
        }
    }
}
