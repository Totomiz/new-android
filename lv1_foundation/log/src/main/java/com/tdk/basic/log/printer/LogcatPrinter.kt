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

import com.tdk.basic.log.config.LogLevel
import com.tdk.basic.log.config.getRealPriority

class LogcatPrinter() : AbsPrinter() {

    override fun printf(logLevel: LogLevel, tag: String?, msg: String) {
        super.printf(logLevel, tag, msg)
        val level = logLevel.getRealPriority()

        // Split by line, then ensure each line can fit into Log's maximum length.
//        var i = 0
//        val length = msg.length
//        while (i < length) {
//            var newline = msg.indexOf('\n', i)
//            newline = if (newline != -1) newline else length
//            do {
//                val end = Math.min(newline, i + MAX_LENGTH)
//                val part = msg.substring(i, end)
//                android.util.Log.println(level, tag, part)
//                i = end
//            } while (i < newline)
//            i++
//        }

//        var index = 0
//        val length = msg.length
//        val countOfSub = length / MAX_LENGTH
//        if (countOfSub > 0) {
//            for (i in 0 until countOfSub) {
//                val sub = msg.substring(index, index + MAX_LENGTH)
//                sub.lastIndexOf('\n')
//                android.util.Log.println(level, tag, sub)
//                index += MAX_LENGTH
//            }
//            android.util.Log.println(level, tag, msg.substring(index, length))
//        } else {
//            android.util.Log.println(level, tag, msg)
//        }

        val input = msg
        var startIndex = 0
        val chunkSize = MAX_LENGTH
        while (startIndex < input.length) {
            val endIndex = if (startIndex + chunkSize <= input.length) {
                findLastNewlineIndex(input, startIndex, startIndex + chunkSize)
            } else {
                input.length
            }
            val substring = input.substring(startIndex, endIndex)
            tasks.forEach {
                it.doTask(logLevel, tag, substring)
            }
            android.util.Log.println(level, tag, substring)
            startIndex = endIndex
        }
    }

//    fun splitLongString(input: String, chunkSize: Int) {
//        var startIndex = 0
//        while (startIndex < input.length) {
//            val endIndex = if (startIndex + chunkSize <= input.length) {
//                findLastNewlineIndex(input, startIndex, startIndex + chunkSize)
//            } else {
//                input.length
//            }
//            val substring = input.substring(startIndex, endIndex)
//            println(substring)
//            startIndex = endIndex
//        }
//    }

    inline fun findLastNewlineIndex(str: String, startIndex: Int, endIndex: Int): Int {
        for (i in endIndex - 1 downTo startIndex) {
            if (str[i] == '\n') {
                return i + 1
            }
        }
        return endIndex
    }
}