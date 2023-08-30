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


import com.newandroid.kscript.NiaBuildType
import com.newandroid.kscript.configAndroidxLibs

plugins {
    id("newandroid.android.application.demo")
    id("newandroid.android.application.compose")
    id("newandroid.android.application.flavors")
    id("newandroid.android.application.jacoco")
    id("newandroid.android.hilt")
    id("jacoco")
//    id("newandroid.android.application.firebase")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.tz.mad"

    defaultConfig {
        applicationId = "com.tz.app"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        debug {
            applicationIdSuffix = NiaBuildType.DEBUG.applicationIdSuffix
        }
        val release by getting {
            isMinifyEnabled = true
            applicationIdSuffix = NiaBuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            signingConfig = signingConfigs.getByName("debug")
        }
        create("benchmark") {
            // Enable all the optimizations from release build through initWith(release).
            initWith(release)
            matchingFallbacks.add("release")
            // Debug key signing is available to everyone.
            signingConfig = signingConfigs.getByName("debug")
            // Only use benchmark proguard rules
            proguardFiles("benchmark-rules.pro")
            isMinifyEnabled = true
            applicationIdSuffix = NiaBuildType.BENCHMARK.applicationIdSuffix
        }
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
}

configAndroidxLibs()

dependencies {
    implementation(project(":lv1_foundation:uires"))
    implementation(project(":lv1_foundation:log"))
    implementation(project(":lv1_foundation:appcontext"))
}