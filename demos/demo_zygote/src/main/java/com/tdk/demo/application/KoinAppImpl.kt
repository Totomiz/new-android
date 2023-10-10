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

package com.tdk.demo.application

import android.app.ActivityThread
import android.app.Application
import com.tdk.app.ApplicationDelegate
import com.tdk.basic.log.TLog
import com.tdk.basic.log.ConfigMap
import com.tdk.basic.log.config.LogLevel.ALL
import com.tdk.basic.log.config.LogLevel.RI
import com.tdk.basic.log.config.impl.FilePrinterConfig
import com.tdk.basic.log.config.impl.LogcatPrinterConfig
import com.tdk.demo.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KoinAppImpl(application: Application) : ApplicationDelegate(application) {
    init {
        val debug = true
        ConfigMap.registerDefaultLogcatPrinterConfig(LogcatPrinterConfig().apply {
            miniLevel = if (debug) ALL else RI
        })
        ConfigMap.registerDefaultFilePrinterConfig(FilePrinterConfig().apply {
            miniLevel = if (debug) ALL else RI
        })
    }

    override fun onCreate(application: Application) {
        TLog.d(TAG, "onCreate: ", application)
        TLog.d(TAG, "onCreate match: ", ActivityThread.currentApplication())

        startKoin {
            androidLogger()
            androidContext(this@KoinAppImpl.application)
            modules(appModules)
        }
    }
}