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

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tdk.basic.log.config.LogLevel.ALL
import com.tdk.basic.log.config.LogLevel.RI
import com.tdk.basic.log.config.impl.LogcatPrinterConfig
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

//
// Added by T on 2023/8/27.
//
@RunWith(AndroidJUnit4::class)
class SomeTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
//        ApplicationProvider.getApplicationContext()
        val context =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application
        appCtx = context
        val debug = true
//        TLogRegister.registerDefaultLogcatPrinterConfig(LogcatPrinterConfig().apply {
//            miniLevel = if (debug) ALL else RI
//        })
        TLogRegister.registerDefaultFilePrinterConfig(LogcatPrinterConfig().apply {
            miniLevel = if (debug) ALL else RI
        })
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun testCheckSessionExpiry() = runBlocking {
        val mainViewModel = MainViewModel()

        val totalExecutionTime = measureTimeMillis {
            val isSessionExpired = mainViewModel.checkSessionExpiry()
            assertTrue(isSessionExpired)
        }

        print("Total Execution Time: $totalExecutionTime")
    }

    @Test
    fun testSomeUI() = runBlocking {
        println("defaaa === $defaultLogDir")
        val totalExecutionTime = measureTimeMillis {
            launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
                delay(1_000)

                println("1. ${System.currentTimeMillis()}") // 1000
                delay(200)
                println("2. ${System.currentTimeMillis()}") // 1200
                delay(2_000)
                println(
                    "4" +
                        ". ${System.currentTimeMillis()}"
                ) // 3200

                val a = Exception("aaa")
                val index = AtomicInteger()
                while (true) {
                    delay(100)
                    TLog.i("aaa", "onCreate: ")
                    TLog.i("onCreate: ")
                    TLog.i("onCreate: ", this)
                    TLog.i(this)
                    TLog.e("onCreate: ")
                    TLog.d("onCreate: ")
                    TLog.w("onCreate: ")
                    TLog.e("onCreate: ", Exception("aaa"))
                    TLog.e("onCreate: ", "AAAA", Exception("CCCC"), "GGGGGG$")
                    TLog.d("launch${index.getAndIncrement()}", "启动一个协程", a)
                    TLog.d("launch${index.getAndIncrement()}", "启动一个协程", a)
                    TLog.d("launch${index.getAndIncrement()}", "启动一个协程", a)
                    TLog.d("launch${index.getAndIncrement()}", "启动一个协程", a)
                    TLog.d("launch${index.getAndIncrement()}", "启动一个协程", a)
                    TLog.d("launch${index.getAndIncrement()}", "启动一个协程", a)
                    TLog.d("launch${index.getAndIncrement()}", "启动一个协程", a)
                }
            }
        }


        println("Total Execution Time: $totalExecutionTime")

    }
}

class MainViewModel : ViewModel() {

    private var isSessionExpired = false

    suspend fun checkSessionExpiry(): Boolean {
        withContext(Dispatchers.IO) {
            delay(5_000) // to simulate a heavy weight operations
            isSessionExpired = true
        }
        return isSessionExpired
    }
}