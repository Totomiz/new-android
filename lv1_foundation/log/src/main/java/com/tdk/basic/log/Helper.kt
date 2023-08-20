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

import java.util.regex.Pattern

private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")

fun StackTraceElement.place(
    preFix: String = "",
    clzName: Boolean = true,
    methodName: Boolean = true
): String? {
    return this.let {
        if (clzName) {
            if (methodName) {
                var tag = it.className.substringAfterLast('.')
                val m = ANONYMOUS_CLASS.matcher(tag)
                if (m.find()) {
                    tag = m.replaceAll("")
                }
                "$preFix${tag}(${it.fileName}:${it.lineNumber})"
//                "$preFix${it.className.substringAfterLast(".")}.${it.methodName}(${it.fileName}:${it.lineNumber}) ${Thread.currentThread().name}"
            } else {
                var tag = it.className.substringAfterLast('.')
                val m = ANONYMOUS_CLASS.matcher(tag)
                if (m.find()) {
                    tag = m.replaceAll("")
                }
                "$preFix${tag}(${it.fileName}:${it.lineNumber})"
//                "$preFix(${it.fileName}:${it.lineNumber}) ${Thread.currentThread().name}"
            }
        } else {
            if (methodName) {
                "$preFix${it.methodName}(${it.fileName}:${it.lineNumber}) ${Thread.currentThread().name}"
            } else {
                "$preFix(${it.fileName}:${it.lineNumber}) ${Thread.currentThread().name}"
            }
        }
    }
}