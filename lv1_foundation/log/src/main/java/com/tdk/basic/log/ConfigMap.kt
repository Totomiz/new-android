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
 *   Created by T on 2023/8/21.
 */

package com.tdk.basic.log

import com.tdk.basic.log.iabs.IConfig
import com.tdk.basic.log.iabs.IPrinter
import com.tdk.basic.log.printer.CrashLogPrinter
import com.tdk.basic.log.printer.FileLogPrinter
import com.tdk.basic.log.printer.LogcatPrinter
import java.util.concurrent.ConcurrentHashMap

object ConfigMap {
    private val printerConfigMap = ConcurrentHashMap<Class<out IPrinter>, IConfig>()

    fun registerConfig(clz: Class<out IPrinter>, config: IConfig) {
        printerConfigMap[clz] = config
    }

    fun registerDefaultLogcatPrinterConfig(config: IConfig) {
        printerConfigMap[LogcatPrinter::class.java] = config
    }

    fun registerDefaultFilePrinterConfig(config: IConfig) {
        printerConfigMap[FileLogPrinter::class.java] = config
    }

    fun registerDefaultCrashPrinterConfig(config: IConfig) {
        printerConfigMap[CrashLogPrinter::class.java] = config
    }

    fun <T : IConfig> getPrinterConfig(clz: Class<out IPrinter>): T {
        val iConfig = printerConfigMap[clz]
            ?: throw NotFoundException("can't find config for [${clz.name}] printer, Please registry a config for that before use")
        return iConfig as T
    }

    fun <T : IConfig> getPrinterConfig(name: String): T {
        val iConfig = printerConfigMap.entries.find { it.key.name == name }?.value
            ?: throw NotFoundException("can't find config for [$name] printer, Please registry a config for that before use")
        return iConfig as T
    }
}

class NotFoundException : RuntimeException {
    constructor()
    constructor(name: String?) : super(name)
    constructor(name: String?, cause: Exception?) : super(name, cause)
}