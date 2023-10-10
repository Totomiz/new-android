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

package com.tdk.basic.log.task

import com.tdk.basic.log.config.LogLevel

//
// Added by T on 2023/9/3.
//
fun interface Task {

    fun doTask(logLevel: LogLevel, tag: String?, msg: String): Unit

    companion object {

        inline operator fun invoke(crossinline block: (logLevel: LogLevel, tag: String?, msg: String) -> Unit): Task =
            Task { logLevel, tag, msg ->
                block(logLevel, tag, msg)
            }

//        inline operator fun invoke(crossinline block: (chain: Chain) -> Unit): Interceptor =
//            Interceptor { block(it) }
    }

//    interface Chain {
//
//        fun proceed(logLevel: LogLevel, tag: String?, msg: String): Unit
//
//    }
}

typealias ChainTask = (logLevel: LogLevel, tag: String?, msg: String) -> Unit