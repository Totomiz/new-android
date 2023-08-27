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

//
// Added by T on 2023/8/26.
//
internal class LineTxtWriter : TxtWriter() {

    // 函数用于在文件末尾写入文字
    override inline fun writeToLogFile(
        file: File,
        text: String,
        startLine: Int,
        maxFileSizeIn: Long
    ) {
        val currentSize = file.length()
        val maxSize = maxFileSizeIn
        // 检查文件大小是否超过maxSize
        if (currentSize >= maxSize) {
            // 删除第10行开始到m行的内容
            deleteLinesFromStartToM(file, startLine, text.length)
        }
        file.appendText(System.lineSeparator())
        // 追加写入文字到文件末尾
        file.appendText(text)
    }

    // 函数用于删除文件中从指定起始行到指定行数（根据内容大小计算）的内容
    inline fun deleteLinesFromStartToM(file: File, startLine: Int, textSize: Int) {
        val lines = file.bufferedReader().readLines().toMutableList()

        var cumulativeSize = 0
        var lineToDelete = startLine

        // 累计计算需要删除的行数
        while (cumulativeSize < textSize && lineToDelete <= lines.size) {
            cumulativeSize += lines[lineToDelete - 1].length
            lineToDelete++
        }

        // 删除指定行数的内容
        for (i in lineToDelete - 1 downTo startLine) {
            lines.removeAt(i)
        }

        // 将修改后的内容重新写入文件
        file.writeText(lines.joinToString(System.lineSeparator()))
    }
}