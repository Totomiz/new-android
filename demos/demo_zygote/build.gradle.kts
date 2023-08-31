/*
 * Copyright 2023-Now T Open Source Project .
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
 */


import com.newandroid.kscript.configAndroidxLibs

plugins {
    id("tdk.demo.app")
    id("newandroid.android.application.compose")
    id("newandroid.android.application.flavors")
    id("newandroid.android.application.jacoco")
    id("newandroid.android.hilt")
    id("jacoco")
//    id("newandroid.android.application.firebase")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.tdk.demo.${name}"
    println("namespace === $namespace")

    defaultConfig {
        applicationId = namespace
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

configAndroidxLibs()

dependencies {

}