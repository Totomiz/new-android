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

package com.tdk.basic.log

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.measureTime

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("king.basic.log.test", appContext.packageName)
    }

    @Test
    fun testLine() {
        val line = System.lineSeparator()

        val strA = StringBuilder().append(line).append("a").append(line).toString()
        val strB = StringBuilder().append(line).append("abcd").append(line).toString()

        val strC = StringBuilder().append(line).toString()
        val strD = StringBuilder().append("A").toString()

        assertEquals(3, strA.lines().size)
        assertEquals(3, strB.lines().size)
        assertEquals(2, strC.lines().size)
        assertEquals(1, strD.lines().size)

        println("header size = ${LOG_HEARD_INFO?.lines()?.size ?: 1}")
    }

    @Test
    fun testDefaultLogDir() {

        // 进一步进行其他验证操作
    }

    @Test
    fun unconfinedTest() = runTest(UnconfinedTestDispatcher()) {

        val a = measureTime {
            launch {
                val a = Exception("aaa")
                val index = AtomicInteger()
                while (index.get() < 50) {
                    delay(500)
                    TLog.i("aaa", "onCreate: ")
                    TLog.i("onCreate: ")
                    TLog.i("onCreate: ", this)
                    TLog.i(this)
                    TLog.e("onCreate: ")
                    TLog.d("onCreate: ")
                    TLog.w("onCreate: ")
                    TLog.e("onCreate: ", Exception("aaa"))
                    TLog.e("onCreate: ", "AAAA", Exception("CCCC"), "GGGGGG$")
                    TLog.d("launch${index.getAndDecrement()}", "启动一个协程", a)
                }
                delay(1_000)
                println("1. $currentTime") // 1000
                delay(200)
                println("2. $currentTime") // 1200
                delay(2_000)
                println("4. $currentTime") // 3200
            }
        }
        println("time ==${a}")
    }
}