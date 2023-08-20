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

package com.tdk.basic.log.config

import android.util.Log
import com.tdk.basic.log.config.LogLevel.ALL
import com.tdk.basic.log.config.LogLevel.D
import com.tdk.basic.log.config.LogLevel.E
import com.tdk.basic.log.config.LogLevel.I
import com.tdk.basic.log.config.LogLevel.NONE
import com.tdk.basic.log.config.LogLevel.RD
import com.tdk.basic.log.config.LogLevel.RE
import com.tdk.basic.log.config.LogLevel.RI
import com.tdk.basic.log.config.LogLevel.RV
import com.tdk.basic.log.config.LogLevel.RW
import com.tdk.basic.log.config.LogLevel.RWTF
import com.tdk.basic.log.config.LogLevel.V
import com.tdk.basic.log.config.LogLevel.W
import com.tdk.basic.log.config.LogLevel.WTF

enum class LogLevel(val describe: String, val logLevel: Int) {
    V("V", Log.VERBOSE),
    D("D", Log.DEBUG),
    I("I", Log.INFO),
    W("W", Log.WARN),
    E("E", Log.ERROR),
    WTF("WTF", Log.ERROR),

    /**有些时候想在release下依旧打印*/
    RV("RELEASE_V", Log.VERBOSE + 100),
    RD("RELEASE_D", Log.DEBUG + 100),
    RI("RELEASE_I", Log.INFO + 100),
    RW("RELEASE_W", Log.WARN + 100),
    RE("RELEASE_E", Log.ERROR + 100),
    RWTF("RELEASE_WTF", Log.ERROR + 100),

    NONE("NONE", Int.MAX_VALUE),
    ALL("ALL", Int.MIN_VALUE)
}

/**
 * 获取真正在android.util.Log中对应的层级
 * @receiver LogLevel
 * @return Int
 */
inline fun LogLevel.getRealPriority(): Int {

    return when (this) {
        V, RV -> Log.VERBOSE
        D, RD -> Log.DEBUG
        I, RI -> Log.INFO
        W, RW -> Log.WARN
        E, RE -> Log.ERROR
        WTF, RWTF -> Log.ERROR
        NONE -> Int.MAX_VALUE
        ALL -> Int.MIN_VALUE
    }
}