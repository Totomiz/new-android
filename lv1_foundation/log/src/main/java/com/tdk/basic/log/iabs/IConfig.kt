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

package com.tdk.basic.log.iabs

import com.tdk.basic.log.config.LogLevel
import com.tdk.basic.log.config.LogLevel.ALL

abstract class IConfig() {
    var enable: Boolean = true
    var miniLevel: LogLevel = ALL
    var defaultTag: String = "TLog"
    var isUseDefaultTag: Boolean = true
    var stackTrace: Int = 7
    var showPlace: Boolean = false
    var showClzName: Boolean = true
    var showMethodName: Boolean = false
    var placePrefixString: String = " "
}