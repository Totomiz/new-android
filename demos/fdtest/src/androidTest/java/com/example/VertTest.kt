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

package com.example

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import com.tdk.basic.log.appCtx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

//
// Added by T on 2023/8/30.
//
class VertTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private var test_block_string = ""
    val blockSize = 1 * 1024 * 1024 // 4 MB

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

        val context =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application
        appCtx = context
        val debug = true

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

    @Test
    fun test1() {
        val context =
            InstrumentationRegistry.getInstrumentation().targetContext
        val path = context.getExternalFilesDir("")

        val srcf = "src_a.txt"
        val dstf = "dst_a.txt"
        val addFile = File(path, srcf)
        if (!addFile.exists()) {
            addFile.createNewFile()
            addFile.writeText(test_block_string)
        }
        println("path->> ${addFile.absolutePath}")


        Vert().test(context, srcf, dstf)
    }
}