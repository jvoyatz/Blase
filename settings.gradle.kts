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
//include(":common")
include(":core:domain")
include(":data:activities:repo")
include(":data:activities:net-source")
//include(":data:activities:db-source")
include(":core:network:v1")
