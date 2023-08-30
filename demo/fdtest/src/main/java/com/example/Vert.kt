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

import android.content.Context
import java.io.File

//
// Added by T on 2023/8/30.
//
class Vert {
    fun test(ctx: Context, srcName: String, destName: String) {
        val path = ctx.getExternalFilesDir("")
        val srcF = File(path, srcName)
        val dstF = File(path, destName)
//        if(!srcF.exists())srcF.createNewFile()
        if (!dstF.exists()) dstF.createNewFile()

        val fis = srcF.inputStream()
        val fos = dstF.outputStream()

        // 获取源文件和目标文件的文件描述符
        // 获取源文件和目标文件的文件描述符
        val srcFd = fis.fd
        val destFd = fos.fd

        NativeLib().copyFile(srcFd, destFd)
        // 关闭文件
        fis.close();
        fos.close();
    }
}