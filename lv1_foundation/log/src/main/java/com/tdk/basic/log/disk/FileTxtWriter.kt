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
import java.io.FileWriter

//
// Added by T on 2023/8/26.
//
internal class FileTxtWriter : TxtWriter() {

    // 函数用于在文件末尾写入文字
    override inline fun writeToLogFile(
        file: File,
        text: String,
        startLine: Int,
        maxFileSizeIn: Long
    ) {
        val currentSize = file.length()
        val maxSize = maxFileSizeIn

        if (currentSize >= maxSize) {
            deleteLinesFromStartToM(file, startLine, text.length)
        }

        FileWriter(file, true).use { writer ->
            writer.appendLine()
            writer.append(text)
        }
    }

    // 函数用于删除文件中从指定起始行到指定行数（根据内容大小计算）的内容
    inline fun deleteLinesFromStartToM(file: File, startLine: Int, textSize: Int) {
        val lines = file.useLines { it.toList().toMutableList() }

        var cumulativeSize = 0
        var lineToDelete = startLine

        while (cumulativeSize < textSize && lineToDelete <= lines.size) {
            cumulativeSize += lines[lineToDelete - 1].length
            lineToDelete++
        }

        val sublistToRemove = lines.subList(startLine - 1, lineToDelete - 1)
        sublistToRemove.clear()

        FileWriter(file).use { writer ->
            writer.write(lines.joinToString(System.lineSeparator()))
        }
    }
}