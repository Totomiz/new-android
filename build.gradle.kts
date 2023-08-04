// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Lists all plugins used throughout the project without applying them.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.secrets) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.android.gradlePlugin)
        classpath(libs.kotlin.gradlePlugin)
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}


inline fun <reified T> Project.applyAndroid(block: T.() -> Unit) {
    (extensions.getByName("android") as T).apply(block)
}

subprojects {
    //跳过目录名
    val skipProjectName = listOf<String>("lv1_foundation","lv2_ability","lv3_feature")
    if (skipProjectName.contains(name)) {
        return@subprojects
    }

    val isApp = name == "app"

    apply(plugin = if (isApp) "com.android.application" else "com.android.library")

    if (isApp) {
        with(project) {
            applyAndroid<com.android.build.gradle.internal.dsl.BaseAppModuleExtension> {
                namespace = "com.tdk.${project.name}"
            }
        }
    } else {
        with(project) {
            applyAndroid<com.android.build.gradle.LibraryExtension> {
                namespace = "com.tdk.lib.${project.name}"
            }
        }
    }

    dependencies {
        val libs = rootProject.libs

        val implementationDependencies = listOf(
            libs.androidx.appcompat,
            libs.androidx.core.ktx,
            libs.kotlinx.coroutines.android,
        )

        implementationDependencies.forEach { dependency ->
            add("implementation", dependency)
        }
    }
}
