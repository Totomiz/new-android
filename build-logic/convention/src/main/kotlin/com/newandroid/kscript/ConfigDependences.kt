package com.newandroid.kscript

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

object ConfigValue {
    private const val SDK_VERSION = 34
    const val MIN_SDK_VERSION = 21
    const val COMPILE_SDK_VERSION = SDK_VERSION
    const val TARGET_SDK_VERSION = SDK_VERSION
    const val BaseNameSpace = "com.tdk.libs"
    const val BaseResPrefix = "lib_"

    val JAVA_VERSION = JavaVersion.VERSION_17
}

//常用基本库
fun Project.configAndroidCommonLibs() {
    dependencies {
        val libs = mylibs

        val implementationDependencies = listOf(
            libs.androidx.appcompat,
            libs.androidx.core.ktx,
            libs.kotlinx.coroutines.android,
            libs.koin.android,
            libs.koin.core,
            libs.google.gson,
        )

        implementationDependencies.forEach { dependency ->
            add("implementation", dependency)
        }
    }
}

fun Project.configTestLibs() {
    dependencies {
        add("androidTestImplementation", kotlin("test"))
        add("testImplementation", kotlin("test"))
    }
}

fun Project.configAsAppLibs() {
    val libs = mylibs
    dependencies {
        "androidTestImplementation"(libs.androidx.navigation.testing)
        "androidTestImplementation"(libs.accompanist.testharness)
        "androidTestImplementation"(kotlin("test"))
        "debugImplementation"(libs.androidx.compose.ui.testManifest)
        "androidTestImplementation"(libs.androidx.compose.ui.test)
        "androidTestImplementation"(libs.androidx.test.core)
        "androidTestImplementation"(libs.androidx.test.espresso.core)
        "androidTestImplementation"(libs.androidx.test.rules)
        "androidTestImplementation"(libs.androidx.test.runner)
        "androidTestImplementation"(libs.hilt.android.testing)
        "androidTestImplementation"(libs.junit4)
        "androidTestImplementation"(libs.kotlinx.coroutines.test)
        "androidTestImplementation"(libs.turbine)

        "implementation"(libs.androidx.activity.compose)
        "implementation"(libs.androidx.appcompat)
        "implementation"(libs.google.android.material)
        "implementation"(libs.androidx.core.ktx)
        "implementation"(libs.androidx.core.splashscreen)
        "implementation"(libs.androidx.compose.runtime)
        "implementation"(libs.androidx.lifecycle.runtimeCompose)
        "implementation"(libs.androidx.compose.runtime.tracing)
        "implementation"(libs.androidx.compose.material3.windowSizeClass)
        "implementation"(libs.androidx.hilt.navigation.compose)
        "implementation"(libs.androidx.navigation.compose)
        "implementation"(libs.androidx.window.manager)
        "implementation"(libs.androidx.profileinstaller)
        "implementation"(libs.kotlinx.coroutines.guava)
        "implementation"(libs.coil.kt)
    }

}

fun Project.configMyComposeLibs() {

    dependencies {
        val libs = mylibs
        val bom = libs.androidx.compose.bom

        add("implementation", platform(bom))
        add("androidTestImplementation", platform(bom))
        val implementationDependencies = listOf(
            libs.androidx.compose.foundation,
            libs.androidx.compose.foundation.layout,
            libs.androidx.compose.material.iconsExtended,
            libs.androidx.compose.material3,
            libs.androidx.compose.runtime,
            libs.androidx.compose.ui.tooling.preview,
            libs.androidx.compose.ui.util,
        )

        implementationDependencies.forEach { dependency ->
            add("implementation", dependency)
        }
        "debugImplementation"(libs.androidx.compose.ui.tooling)
        "implementation"(libs.coil.kt.compose)
    }
}