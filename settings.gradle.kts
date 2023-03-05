pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Blasé"
include(":app")
include(":features:getactivity")
include(":features:favorite_activities")
include(":core:logging")
include(":core:domain")
include(":data:activities:repo")
include(":core:network:v1")
include(":core:common")
include(":core:database")
include(":core:testing")
