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
import com.tdk.basic.log.task.Task
import com.tdk.basic.log.config.LogLevel
import com.tdk.basic.log.convert.LogcatConverter
import com.tdk.basic.log.iabs.IConfig
import com.tdk.basic.log.iabs.ILogConvert
import com.tdk.basic.log.iabs.IPrinter

abstract class AbsPrinter() : IPrinter {

    companion object {
        val lineseparator = System.lineSeparator()
        const val MAX_LENGTH = 4000
    }

    private var _config: IConfig? = null

    internal val tasks: MutableList<Task> = mutableListOf()

    fun tasks(): MutableList<Task> = tasks

    fun addTask(task: Task) = apply {
        tasks += task
    }

    @JvmName("-addTask") // Prefix with '-' to prevent ambiguous overloads from Java.
    inline fun addTask(crossinline block: (logLevel: LogLevel, tag: String?, msg: String) -> Unit) =
        addTask(Task { logLevel, tag, msg ->
            block(logLevel, tag, msg)
        })

    private var _logFormatter: ILogConvert = LogcatConverter().also {
        it.config = this.config
    }

    override var config: IConfig
        get() = _config ?: ConfigMap.getPrinterConfig<IConfig>(name).also { _config = it }
        set(value) {
            _config = value
        }

    override var logFormatter: ILogConvert
        get() = _logFormatter
        set(value) {
            this._logFormatter = value.also {
                it.config = this.config
            }
        }

    override fun dispatchLog(logLevel: LogLevel, tag: String?, vararg objs: Any) {

        if (!config.enable) {
            return
        }

        if (logLevel.logLevel < config.miniLevel.logLevel) {
            return
        }
        if (config.isUseDefaultTag) {
            printf(logLevel, config.defaultTag, logFormatter.convertMsgWithTag(tag, *objs))
        } else {
            printf(logLevel, tag, logFormatter.convertMsg(*objs))
        }
    }

    override fun printf(logLevel: LogLevel, tag: String?, msg: String) {
    }

    override val name: String
        get() = this.javaClass.name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbsPrinter) return false

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}