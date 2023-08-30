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

import java.io.File
import java.io.RandomAccessFile

//
// Added by T on 2023/8/26.
//
internal class RandomTxtWriter : TxtWriter() {

    // 函数用于在文件末尾写入文字
    override inline fun writeToLogFile(
        filePath: String,
        text: String,
        startLine: Int,
        maxFileSizeIn: Long
    ) {
        val file = File(filePath)
        val currentSize = file.length()
        val maxSize = maxFileSizeIn

        if (currentSize >= maxSize) {
            deleteLinesFromStartToM(filePath, startLine, text)
        }

        RandomAccessFile(file, "rw").use { randomAccessFile ->
            randomAccessFile.seek(randomAccessFile.length())
            randomAccessFile.writeBytes(System.lineSeparator())
            randomAccessFile.writeBytes(text)
        }
    }

    internal inline fun deleteLinesFromStartToM(filePath: String, startLine: Int, text: String) {
        val file = File(filePath)
        val charset = Charsets.UTF_8 // 假设文件使用UTF-8编码

        val linesToDelete = text.lines().size
        val tempFile = File.createTempFile(file.name, ".temp")
        val ls = System.lineSeparator()
        val lsByte = ls.toByteArray(charset)
        tempFile.deleteOnExit()

        RandomAccessFile(file, "rw").use { randomAccessFile ->
            randomAccessFile.seek(0)
//            val outputStream = BufferedOutputStream(tempFile.outputStream())
            val writer = tempFile.bufferedWriter(Charsets.UTF_8)

            var lineCount = 1
            var deletedSize = 0L
            var line: String?
            while (lineCount < startLine) {
                line = randomAccessFile.readLine()
                line?.let {
                    writer.appendLine(it)
//                    writer.write(ls)
                    deletedSize += it.toByteArray(charset).size + lsByte.size
                } ?: break
                lineCount++
            }

            randomAccessFile.seek(deletedSize)
            while (lineCount <= startLine + linesToDelete) {
                line = randomAccessFile.readLine()
                line?.let {
                    deletedSize += it.toByteArray(charset).size + lsByte.size
                } ?: break
                lineCount++
            }

            while (true) {
                line = randomAccessFile.readLine()
                line?.let {
                    writer.appendLine(it)
                } ?: break
            }

            writer.close()
//            outputStream.close()
            file.delete()
            tempFile.renameTo(file)
        }
    }

    // 函数用于删除文件中从指定起始行到指定行数（根据内容大小计算）的内容
//    internal inline fun deleteLinesFromStartToM(file: File, startLine: Int, text: String) {
//        val linesToDelete = text.lines().size
//        val tempFile = File.createTempFile(file.name, ".temp")
//        tempFile.deleteOnExit()
//
//        RandomAccessFile(file, "rw").use { randomAccessFile ->
//            randomAccessFile.seek(0)
//            val outputStream = tempFile.outputStream()
//
//            var lineCount = 1
//            var deletedSize = 0L
//            var line: String?
//            while (lineCount < startLine) {
//                line = randomAccessFile.readLine()
//                line?.let {
//                    val bytes = it.toByteArray()
//                    outputStream.write(bytes)
//                    outputStream.write(System.lineSeparator().toByteArray())
//                    deletedSize += bytes.size + System.lineSeparator().length
//                } ?: break
//                lineCount++
//            }
//
//            randomAccessFile.seek(deletedSize)
//            while (lineCount <= startLine + linesToDelete) {
//                line = randomAccessFile.readLine()
//                line?.let {
//                    val bytes = it.toByteArray()
//                    deletedSize += bytes.size + System.lineSeparator().length
//                } ?: break
//                lineCount++
//            }
//
//            while (true) {
//                line = randomAccessFile.readLine()
//                line?.let {
//                    val bytes = it.toByteArray()
//                    outputStream.write(bytes)
//                    outputStream.write(System.lineSeparator().toByteArray())
//                } ?: break
//            }
//
//            outputStream.close()
//            file.delete()
//            tempFile.renameTo(file)
//        }
//    }
}