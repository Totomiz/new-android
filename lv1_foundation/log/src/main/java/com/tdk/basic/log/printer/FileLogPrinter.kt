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

package com.tdk.basic.log.printer

import com.tdk.basic.log.ConfigMap
import com.tdk.basic.log.config.LogLevel
import com.tdk.basic.log.config.getRealPriority
import com.tdk.basic.log.convert.FileLogConverter
import com.tdk.basic.log.disk.DiskRecord
import com.tdk.basic.log.disk.TimeLogDiskRecord
import com.tdk.basic.log.iabs.IConfig
import com.tdk.basic.log.iabs.ILogConvert

open class FileLogPrinter(
    var logFileDiskRecord: DiskRecord = TimeLogDiskRecord(),
) : AbsPrinter() {

    override fun printf(logLevel: LogLevel, tag: String?, msg: String) {
        val level = logLevel.getRealPriority()
        val input = msg
        var startIndex = 0
        val chunkSize = MAX_LENGTH
        while (startIndex < input.length) {
            val endIndex = if (startIndex + chunkSize <= input.length) {
                findLastNewlineIndex(input, startIndex, startIndex + chunkSize)
            } else {
                input.length
            }
            var substring = input.substring(startIndex, endIndex)
            android.util.Log.println(level, tag, substring)
            logFileDiskRecord.printf(logLevel, tag, substring)
            startIndex = endIndex
        }
    }

    inline fun findLastNewlineIndex(str: String, startIndex: Int, endIndex: Int): Int {
        for (i in endIndex - 1 downTo startIndex) {
            if (str[i] == '\n') {
                return i + 1
            }
        }
        return endIndex
    }
}