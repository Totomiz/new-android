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

package com.tdk.basic.log.disk

import com.tdk.basic.log.config.LogLevel
import java.io.File
import java.text.SimpleDateFormat
import java.util.concurrent.Executor
import kotlin.LazyThreadSafetyMode.SYNCHRONIZED

abstract class DiskRecord {

    open val executor: Executor by lazy(SYNCHRONIZED) {
        IOLogExecutor()
    }

    companion object {

    }

    open val logFileNameDateFormat = SimpleDateFormat("yyyy_MM_dd")

    abstract fun getLogHeader(): String?

    abstract fun getLogDir(): String

    /**
     * 日志文件名生成，不包含路径
     * @return String? 为空则创建失败
     */
    abstract fun createLogFile(parentDir: File, timeInMillis: Long): File?

    //记录日志
    abstract fun printf(logLevel: LogLevel, tag: String?, msg: String)
}

