pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "new-android"
include(":app")
include(":lv1_foundation:hideapi")
include(":lv1_foundation:appcontext")
include(":lv1_foundation:log")
include(":lv1_foundation:ui")
include(":lv1_foundation:net")
include(":lv2_ability:login")
include(":lv3_feature:business1")