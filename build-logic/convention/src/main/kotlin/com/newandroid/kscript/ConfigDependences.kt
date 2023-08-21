/*
 * Copyright 2023  T Open Source Project . All rights reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *   Created by T on 2023/8/22.
 */

package com.newandroid.kscript

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

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
        if (!name.contains("hideapi")) {
            "implementation"(project(":lv1_foundation:hideapi"))
        }
        implementationDependencies.forEach { dependency ->
            add("implementation", dependency)
        }
    }
}

fun Project.configTestLibs() {
    val libs = mylibs
    dependencies {
        add("androidTestImplementation", kotlin("test"))
        add("testImplementation", kotlin("test"))
        "androidTestImplementation"(libs.androidx.navigation.testing)
        "androidTestImplementation"(libs.accompanist.testharness)
        "androidTestImplementation"(kotlin("test"))
        "androidTestImplementation"(libs.androidx.test.core)
        "androidTestImplementation"(libs.androidx.test.espresso.core)
        "androidTestImplementation"(libs.androidx.test.rules)
        "androidTestImplementation"(libs.androidx.test.runner)
        "androidTestImplementation"(libs.androidx.test.ext)
        "androidTestImplementation"(libs.hilt.android.testing)
        "androidTestImplementation"(libs.junit4)
        "androidTestImplementation"(libs.kotlinx.coroutines.test)
        "androidTestImplementation"(libs.turbine)
    }
}

fun Project.configAndroidxLibs() {
    val libs = mylibs

    dependencies {

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

fun Project.configAllAsAppLibs() {
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

        "debugImplementation"(libs.androidx.compose.ui.testManifest)
        "androidTestImplementation"(libs.androidx.compose.ui.test)
    }
}