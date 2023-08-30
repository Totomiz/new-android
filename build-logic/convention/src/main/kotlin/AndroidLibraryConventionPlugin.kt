/*
 * Copyright 2022 The Android Open Source Project
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
 */

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.newandroid.kscript.configAndroidCommonLibs
import com.newandroid.kscript.configAndroidLibDefaultConfig
import com.newandroid.kscript.configTestLibs
import com.newandroid.kscript.configureFlavors
import com.newandroid.kscript.configureGradleManagedDevices
import com.newandroid.kscript.configureKotlinAndroid
import com.newandroid.kscript.configureNdkDefaultConfig
import com.newandroid.kscript.configurePrintApksTask
import com.newandroid.kscript.disableUnnecessaryAndroidTests
import com.newandroid.kscript.libs
import com.newandroid.task.RenameLibResourceTask.addRenameResTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureFlavors(this)
                configureGradleManagedDevices(this)
                configureNdkDefaultConfig(this)
            }

            this.configAndroidLibDefaultConfig()


            extensions.configure<LibraryAndroidComponentsExtension> {
                configurePrintApksTask(this)
                disableUnnecessaryAndroidTests(target)
            }
            configurations.configureEach {
                resolutionStrategy {
                    force(libs.findLibrary("junit4").get())
                    // Temporary workaround for https://issuetracker.google.com/174733673
                    force("org.objenesis:objenesis:2.6")
                }
            }
            configAndroidCommonLibs()
            configTestLibs()
            addRenameResTask()//library添加重命名资源任务,不够完善，待优化
        }
    }
}


