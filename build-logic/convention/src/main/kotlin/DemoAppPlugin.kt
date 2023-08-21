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

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.newandroid.kscript.ConfigValue
import com.newandroid.kscript.configAndroidCommonLibs
import com.newandroid.kscript.configAndroidxLibs
import com.newandroid.kscript.configMyComposeLibs
import com.newandroid.kscript.configTestLibs
import com.newandroid.kscript.configureAndroidCompose
import com.newandroid.kscript.configureGradleManagedDevices
import com.newandroid.kscript.configureKotlinAndroid
import com.newandroid.kscript.configurePrintApksTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginManager
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class DemoAppPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            pluginManager.applyPlugin()
            val extension = extensions.getByType<ApplicationExtension>()
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = ConfigValue.TARGET_SDK_VERSION
                configureGradleManagedDevices(this)
            }

            extensions.configure<ApplicationAndroidComponentsExtension> {
                configurePrintApksTask(this)
            }
            configureAndroidCompose(extension)
            configAndroidCommonLibs()
            configMyComposeLibs()
            configTestLibs()
            configAndroidxLibs()
        }
    }

    private fun PluginManager.applyPlugin() {
        apply("com.android.application")
        apply("org.jetbrains.kotlin.android")
    }
}