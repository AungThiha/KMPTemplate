rootProject.name = "PhotoAlbum"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(
    ":authentication:data",
    ":authentication:domain",
    ":authentication:presentation",
)
include(
    ":photos:data",
    ":photos:domain",
    ":photos:presentation",
)
include(":coroutines")
include(":composeApp")
include(":compose")
include(":operation")
include(":storage")
include(
    ":session:data",
    ":session:domain",
)
