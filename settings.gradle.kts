pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Yandex_School_App"
include(":app")
include(":navigation")
include(":features")
include(":common")
include(":features:settings")
include(":features:category")
include(":features:cash_account")
include(":network")
include(":features:expense")
include(":features:income")
include(":database")
include(":charts")
include(":charts:pie")
include(":charts:line")
