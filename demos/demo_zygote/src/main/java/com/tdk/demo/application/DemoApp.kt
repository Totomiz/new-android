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

package com.tdk.demo.application

/*
 *  Created by T on 2023/8/21.
 */
import com.tdk.app.BaseApplication

const val TAG = "MadApp"

class DemoApp : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun getAppDelegate() = KoinAppImpl(this)
}

