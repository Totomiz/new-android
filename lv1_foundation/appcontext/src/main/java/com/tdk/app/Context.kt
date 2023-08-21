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

package com.tdk.app

import android.app.ActivityThread
import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.os.Build
import java.util.Locale

/**
 *  from hide api
 */
val appCtx = ActivityThread.currentApplication()

val appRes = appCtx.resources

val systemLanguage: String
    get() {
        return Locale.getDefault().language
    }

val Application.currentProcessName: String
    get() {
        if (Build.VERSION.SDK_INT >= 28)
            return Application.getProcessName()

        return try {
            ActivityThread.currentProcessName()
        } catch (throwable: Throwable) {
            packageName
        }
    }

val PackageInfo.isSystemApp: Boolean
    get() {
        val i = applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM
        return i == ApplicationInfo.FLAG_SYSTEM
    }