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

package com.tz.app

import android.app.ActivityThread
import android.app.Application
import com.tdk.basic.log.TLog
import com.tdk.basic.log.TLogRegister
import com.tdk.basic.log.config.LogLevel.ALL
import com.tdk.basic.log.config.LogLevel.RI
import com.tdk.basic.log.config.impl.LogcatPrinterConfig
import com.tz.mad.TAG
import com.tz.mad.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KoAppImpl(application: Application) : com.tdk.app.ApplicationDelegate(application) {
    init {
        val debug = true
        TLogRegister.registerDefaultLogcatPrinterConfig(LogcatPrinterConfig().apply {
            miniLevel = if (debug) ALL else RI
        })
    }

    override fun onCreate(application: Application) {
        TLog.d(TAG, "onCreate: ", application)
        TLog.d(TAG, "onCreate match: ", ActivityThread.currentApplication())

        startKoin {
            androidLogger()
            androidContext(this@KoAppImpl.application)
            modules(appModules)
        }
    }
}