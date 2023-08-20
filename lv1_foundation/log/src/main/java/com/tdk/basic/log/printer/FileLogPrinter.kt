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
import com.tdk.basic.log.formatter.FilelogFormatter
import com.tdk.basic.log.iabs.ILogFormatter

class FileLogPrinter() : AbsPrinter() {

    override var logFormatter: ILogFormatter = FilelogFormatter()

    override fun printf(logLevel: LogLevel, tag: String?, msg: String) {
    }
}