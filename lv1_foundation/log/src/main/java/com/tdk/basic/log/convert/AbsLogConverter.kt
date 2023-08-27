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

package com.tdk.basic.log.convert

import android.util.Log
import com.tdk.basic.log.iabs.IConfig
import com.tdk.basic.log.iabs.ILogConvert
import com.tdk.basic.log.place

abstract class AbsLogConverter() : ILogConvert {

    companion object {
        val lineSeparator = System.lineSeparator()
        val MAX_LENGTH = 4000
    }

    override var config: IConfig? = null
    val sb = StringBuilder()

    override fun convertMsgWithTag(tag: String?, vararg objs: Any): String {
        return buildMsg(tag, *objs)
    }

    override fun convertMsg(vararg objs: Any): String {
        return buildMsg(null, *objs)
    }

    fun buildMsg(tag: String?, vararg objs: Any): String {
        synchronized(sb) {
            sb.clear()
            if (!tag.isNullOrEmpty()) {
                sb.append(tag)
                sb.append(" ")
            }

            if (objs.size == 1) {
                val msg = objs[0]
                if (msg is Throwable) {
                    sb.append(lineSeparator)
                    sb.append(Log.getStackTraceString(msg))
                } else {
                    sb.append(msg)
                }
            } else {

                for ((index, any) in objs.withIndex()) {
                    val msg = any
                    if (msg is Throwable) {
                        sb.append(lineSeparator)
                        sb.append(Log.getStackTraceString(msg))
                    } else {
                        sb.append(msg)
                        if (index == 0) {
                            sb.append(" ")
                        }
                    }
                }
            }
            if (config!!.showPlace) {
                val stackTraceElement = Thread.currentThread().stackTrace[config!!.stackTrace]
                sb.append(
                    stackTraceElement.place(
                        preFix = config!!.placePrefixString,
                        clzName = config!!.showClzName, methodName = config!!.showMethodName
                    )
                )
            }

            return sb.toString()
        }
    }
}