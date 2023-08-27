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

package com.tdk.basic.log

import android.app.ActivityThread
import android.os.StatFs
import androidx.core.content.pm.PackageInfoCompat
import java.io.File
import java.util.regex.Pattern

internal val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
//internal val appCtx = ActivityThread.currentApplication()

//包名
internal val PACKAGE_NAME by lazy { appCtx.packageName }

fun StackTraceElement.place(
    preFix: String = "",
    clzName: Boolean = true,
    methodName: Boolean = true
): String? {
    return this.let {
        if (clzName) {
            if (methodName) {
                var tag = it.className.substringAfterLast('.')
                val m = ANONYMOUS_CLASS.matcher(tag)
                if (m.find()) {
                    tag = m.replaceAll("")
                }
                "$preFix${tag}(${it.fileName}:${it.lineNumber})"
//                "$preFix${it.className.substringAfterLast(".")}.${it.methodName}(${it.fileName}:${it.lineNumber}) ${Thread.currentThread().name}"
            } else {
                var tag = it.className.substringAfterLast('.')
                val m = ANONYMOUS_CLASS.matcher(tag)
                if (m.find()) {
                    tag = m.replaceAll("")
                }
                "$preFix${tag}(${it.fileName}:${it.lineNumber})"
//                "$preFix(${it.fileName}:${it.lineNumber}) ${Thread.currentThread().name}"
            }
        } else {
            if (methodName) {
                "$preFix${it.methodName}(${it.fileName}:${it.lineNumber}) ${Thread.currentThread().name}"
            } else {
                "$preFix(${it.fileName}:${it.lineNumber}) ${Thread.currentThread().name}"
            }
        }
    }
}

internal var appCtx = ActivityThread.currentApplication()

internal val defaultLogDir by lazy {

    val path = appCtx.getExternalFilesDir("")?.absolutePath + File.separator + "tlog"
    val file = File(path)
    if (!file.exists() || !file.isDirectory) {
        file.mkdirs()
    }
    debugLog("log dir:${file.absolutePath}")
    return@lazy file.absolutePath
}

internal val LOG_HEARD_INFO by lazy {
    val builder = StringBuilder()
    builder.append("APP Version Name：${appVerName()}\n")
    builder.append("APP Version Code：${appVerCode()}\n")
    builder.append("System Version Code: ${android.os.Build.VERSION.RELEASE}\n")
    builder.append("System API: ${android.os.Build.VERSION.SDK_INT}\n")
    builder.append("Product Name: ${android.os.Build.PRODUCT}\n")
    builder.append("Manufacturer Name: ${android.os.Build.MANUFACTURER}\n")
    builder.append("Device Model: ${android.os.Build.MODEL}\n")
    builder.append("\n\n")

    return@lazy builder.toString()
}

internal fun appVerName(pkgName: String? = null): String {
    val info = appCtx.packageManager.getPackageInfo(pkgName ?: appCtx.packageName, 0)
    return info.versionName ?: ""
}

internal fun appVerCode(pkgName: String? = null): Int {
    val info = appCtx.packageManager.getPackageInfo(pkgName ?: appCtx.packageName, 0)
    return PackageInfoCompat.getLongVersionCode(info).toInt()
}

/**
 * 获取全部存储空间
 *  单位B
 */
internal fun getTotalStore(logDir: String): Long {
    val sf = StatFs(logDir)
    val blockSize = sf.blockSizeLong
    val blockCount = sf.blockCountLong
    val size = blockSize * blockCount
    return size
}

/**
 * 获取空余存储空间
 *  单位B
 */
internal fun getFreeStore(logDir: String): Long {
    val sf = StatFs(logDir)
    val blockSize = sf.availableBlocksLong
    val blockCount = sf.blockCountLong
    val size = blockSize * blockCount
    return size
}

const val isPrint = true
internal fun debugLog(msg: String) {
    if (!isPrint) return
    System.out.println("[TLog][Debug]：${msg}")
}

internal fun errorLog(msg: String) {
    if (!isPrint) return
    System.out.println("[TLog][Error]：${msg}")
}