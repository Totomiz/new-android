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

import com.android.build.api.dsl.ApplicationExtension
import com.newandroid.kscript.configureGradleManagedDevices
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.newandroid.kscript.ConfigValue
import com.newandroid.kscript.configAndroidCommonLibs
import com.newandroid.kscript.configureKotlinAndroid
import com.newandroid.kscript.configurePrintApksTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginManager
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.applyPlugin()

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = ConfigValue.TARGET_SDK_VERSION
                configureGradleManagedDevices(this)
            }
            extensions.configure<ApplicationAndroidComponentsExtension> {
                configurePrintApksTask(this)
            }
            configAndroidCommonLibs()
        }
    }

    private fun PluginManager.applyPlugin() {
        apply("com.android.application")
        apply("org.jetbrains.kotlin.android")
    }
}