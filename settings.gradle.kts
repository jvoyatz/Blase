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
include(":data:activities:repo")
include(":core:logging")
//include(":common")
include(":core:domain")
include(":data:activities:network")
include(":data:activities:database")
