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

package com.tdk.basic.log.disk

import com.tdk.basic.log.LOG_HEARD_INFO
import com.tdk.basic.log.config.LogLevel
import com.tdk.basic.log.defaultLogDir
import com.tdk.basic.log.errorLog
import java.io.File
import java.util.concurrent.TimeUnit

//
// Added by T on 2023/8/25.
//
open class TimeLogDiskRecord(
    val logDirPath: String = defaultLogDir,
    val logFileHeader: String? = LOG_HEARD_INFO,
    val logKeepTime: Long = TimeUnit.DAYS.toSeconds(7),
    val createInterval: Long = TimeUnit.HOURS.toSeconds(1),
    val maxFileSizeIn: Long = (10 * 1024 * 1024).toLong(), // 最大文件大小为10M，可根据需求修改
    val fileName: String = "myapp2.log"
) : DiskRecord() {

    private var currentLogFilePath: String? = null
    private val sb = StringBuilder()
    private val txtWriter: TxtWriter = LineTxtWriter()
    override fun getLogHeader(): String? = logFileHeader

    override fun getLogDir(): String = logDirPath

    private fun writeToLogFile(file: File, text: String) {
        val startLine = logFileHeader?.lines()?.size ?: 1
        txtWriter.writeToLogFile(file, text, startLine, maxFileSizeIn)
    }

    override fun createLogFile(parent: File, timeInMillis: Long): File? {
        val file = File(parent, fileName)
        if (file.exists()) {
            if (!file.isFile || !file.canRead() || !file.canWrite()) {
                errorLog("can't file read write!")
                return null
            }
            return file
        }

        return file.takeIf {
            it.createNewFile().also { created ->
                if (created && logFileHeader != null) {
                    it.printWriter().use {
                        it.append(logFileHeader)
                    }
                }
            }
        }
    }

    override fun printf(logLevel: LogLevel, tag: String?, msg: String) {
        executor.execute {
            synchronized(sb) {
                sb.clear()
                val logdir = File(getLogDir())

                if (!logdir.exists() || !logdir.isDirectory) {
                    val created = logdir.mkdirs()
                    if (!created) {
                        errorLog("can't create log dir: ${logdir.absolutePath}")
                        return@execute
                    }
                }

                if (!logdir.canRead() || !logdir.canWrite()) {
                    errorLog("can't read or write log dir: ${logdir.absolutePath}")
                    return@execute
                }

                val logFile = createLogFile(logdir, System.currentTimeMillis())
                if (logFile != null) {
                    if (tag != null) {
                        sb.append(tag)
                        sb.append(" ")
                    }
                    sb.append(msg)
                    sb.append(System.lineSeparator())

                    writeToLogFile(logFile, sb.toString())
                } else {
                    errorLog("can't createLogFile: ${logFile?.absolutePath}")
                }
            }
        }
    }
}