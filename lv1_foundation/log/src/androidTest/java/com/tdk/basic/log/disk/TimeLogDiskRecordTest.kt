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

package com.tdk.basic.log.disk

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import com.tdk.basic.log.TLogRegister
import com.tdk.basic.log.appCtx
import com.tdk.basic.log.config.LogLevel.ALL
import com.tdk.basic.log.config.LogLevel.I
import com.tdk.basic.log.config.LogLevel.RI
import com.tdk.basic.log.config.impl.LogcatPrinterConfig
import com.tdk.basic.log.defaultLogDir
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

//
// Added by T on 2023/8/27.
//
class TimeLogDiskRecordTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private var test_block_string = ""
    val blockSize = 4 * 1024 * 1024 // 4 MB

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

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

        val blockSizeBytes = blockSize * 2 // 2 bytes per character assuming UTF-8 encoding
        val outputStream = ByteArrayOutputStream(blockSizeBytes)
        val index = AtomicInteger()

        while (outputStream.size() < blockSizeBytes) {
            val line = "this is a test string index of ${index.getAndIncrement()}"
            outputStream.write(line.toByteArray(Charsets.UTF_8))
            outputStream.write(System.lineSeparator().toByteArray(Charsets.UTF_8))
        }

        test_block_string = outputStream.toString(Charsets.UTF_8)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

//    fun getMemoryUsage(): MemoryUsage {
//        val memoryMXBean = ManagementFactory.getMemoryMXBean()
//        return memoryMXBean.heapMemoryUsage
//    }
//
//    fun getCpuUsage(): Double {
//        val operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean
//        return operatingSystemMXBean.processCpuLoad
//    }

    @Test
    fun testPrintf() = runBlocking {
        delay(30000)
        println("defaultLogDir === $defaultLogDir")
        val recoder = TimeLogDiskRecord(fileName = "test1.log", txtWriter = FileTxtWriter())

        val totalExecutionTime = measureTimeMillis {
            launch(Dispatchers.IO) {  // Will be launched in the mainThreadSurrogate dispatcher

                val a = Exception("aaa")
                val index = AtomicInteger()
                while (true) {
                    delay(1000)
                    recoder.printf(I, "tag${index.getAndIncrement()}", test_block_string)
                }
            }
        }
        println("totalExecutionTime ==${totalExecutionTime}")
    }
}