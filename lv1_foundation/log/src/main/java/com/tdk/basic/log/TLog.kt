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

import com.tdk.basic.log.config.LogLevel.D
import com.tdk.basic.log.config.LogLevel.E
import com.tdk.basic.log.config.LogLevel.I
import com.tdk.basic.log.config.LogLevel.RI
import com.tdk.basic.log.config.LogLevel.V
import com.tdk.basic.log.config.LogLevel.W
import com.tdk.basic.log.iabs.ILogger
import com.tdk.basic.log.task.AndroidLogTask
import com.tdk.basic.log.task.FileLogTask
import com.tdk.basic.log.printer.LogcatPrinter

/**
 * <p>
 * Generally, use the v() d() i() w() and e() methods.
 * For some reason,You may want to see the log output regardless of the current build type of apk,use the TLog.rv()
 * TLog.rd() TLog.ri() TLog.rw() and TLog.re() methods.
 *
 *
 * </p>
 */
object TLog {

    var factory = DefaultFactory()

    var logger: ILogger = factory.create()

    fun i(any: Any) {
        logger.print(I, null, any)
    }

    fun i(tag: String, vararg objs: Any) {
        logger.print(I, tag, *objs)
    }

    fun v(any: Any) {
        logger.print(V, null, any)
    }

    fun v(tag: String, vararg objs: Any) {
        logger.print(V, tag, *objs)
    }

    fun d(any: Any) {
        logger.print(D, null, any)
    }

    fun d(tag: String, vararg objs: Any) {
        logger.print(D, tag, *objs)
    }

    fun w(any: Any) {
        logger.print(W, null, any)
    }

    fun w(tag: String, vararg objs: Any) {
        logger.print(W, tag, *objs)
    }

    fun e(any: Any) {
        logger.print(E, null, any)
    }

    fun e(tag: String, vararg objs: Any) {
        logger.print(E, tag, *objs)
    }

    fun ri(any: Any) {
        logger.print(RI, null, any)
    }

    fun ri(tag: String, vararg objs: Any) {
        logger.print(RI, tag, *objs)
    }
}

fun TLog.configure(setUp: (ConfigMap) -> Unit) {
    setUp(ConfigMap)
    logger = factory.create()
    LogcatPrinter()
        .addTask(AndroidLogTask())
        .addTask(FileLogTask())
        .addTask { logLevel, tag, msg ->

        }
}
