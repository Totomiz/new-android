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

package com.newandroid.kscript

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

//
// Added by T on 2023/8/26.
//

/**
 * android app default config
 * @receiver Project
 */
inline fun Project.configAndroidDefaultConfig() {
    this.extensions.getByType<ApplicationExtension>().apply {
        defaultConfig {
            targetSdk = ConfigValue.TARGET_SDK_VERSION
            minSdk = ConfigValue.MIN_SDK_VERSION
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                useSupportLibrary = true
            }
        }
    }
}

/**
 * android lib default config
 * @receiver Project
 */
inline fun Project.configAndroidLibDefaultConfig() {
    this.extensions.getByType<LibraryExtension>().apply {
        defaultConfig.targetSdk = ConfigValue.TARGET_SDK_VERSION
        defaultConfig.minSdk = ConfigValue.MIN_SDK_VERSION
        defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        defaultConfig.vectorDrawables {
            useSupportLibrary = true
        }
        namespace = ConfigValue.BaseNameSpace + "." + name
        resourcePrefix = ConfigValue.BaseResPrefix + name + "_"
    }
}

internal fun Project.configureNdkDefaultConfig(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        defaultConfig {
            ndkVersion = "23.0.7599858"
            externalNativeBuild {
                cmake {
                    cppFlags += "-std=c++11"
                }
            }
            ndk {
                abiFilters.add("armeabi-v7a")
                abiFilters.add("arm64-v8a") // 替换为你需要的 ABI 架构类型
                abiFilters.add("x86_64")
                abiFilters.add("x86")
            }
        }

        val cmakeListsFile = file("src/main/cpp/CMakeLists.txt")
        if (cmakeListsFile.exists()) {
            externalNativeBuild {
                cmake {
                    path = cmakeListsFile
                    version = "3.22.1"
                }
            }
        }

    }
}