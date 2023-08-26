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

package com.newandroid.task

import com.newandroid.kscript.applyAndroid
import org.gradle.api.Project
import java.io.File

//
// Added by T on 2023/8/26.
//

object RenameLibResourceTask {
    //自定义批量修改文件名字，待优化
    fun Project.addRenameResTask() {
        applyAndroid<com.android.build.gradle.LibraryExtension> {

            tasks.register("renameResourcesName") {
                group = "custom"
                description = "Rename resources name with resourcePrefix"

                doLast {
                    sourceSets.getByName("main").res.srcDirs.forEach {

                        println("Welcome to Gradle==" + it)
                        renameResourcesWithPrefix(it.absolutePath, resourcePrefix + "")
                    }
                }
            }

            tasks.register("revertResourcesName") {
                group = "custom"
                description = "Revert resources name with resourcePrefix"
                doLast {
                    sourceSets.getByName("main").res.srcDirs.forEach {
                        println("Welcome to Gradle==" + it)
                        revertResourcesWithPrefix(it.absolutePath, resourcePrefix + "")
                    }
                }
            }
        }
    }

    fun revertResourcesWithPrefix(directory: String, prefix: String) {
        val rootDir = File(directory)
        println("revert$directory")
        // 定义要重命名的目录前缀列表
        val optDirs = listOf("drawable", "mipmap", "xml", "layout")
        val skipType = listOf(".png", ".webp", ".jpg", ".jpeg")
        val mapping = mutableMapOf<File, File>()
        rootDir.walkTopDown().forEach { file ->

            if (file.isFile && file.name.startsWith(prefix)) {
                val parentDirectoryName = file.parentFile.name

                // 检查父目录前缀是否匹配要重命名的目录前缀列表
                if (optDirs.any { parentDirectoryName.startsWith(it) }) {
                    val oldFileName = file.name
                    val newFileName = oldFileName.substringAfter(prefix)
                    val newFilePath = File(file.parentFile, newFileName)
                    println(file.name + " ->> " + newFileName)
                    // 重命名文件
                    file.renameTo(newFilePath)
                    mapping.put(File(file.parentFile, oldFileName), newFilePath)
                }
            }
        }

        val replace = mapping.keys
            .map { it.name.substringBeforeLast(".") }
            .toSet()

        mapping.forEach { oF, nF ->
            // 更新文件中的资源引用
            if (!skipType.any { nF.name.endsWith(it) }) {
                replace.forEach {
                    updateResourceReferences(nF, it, nF.name)
                }
            }
        }
    }

    fun renameResourcesWithPrefix(directory: String, prefix: String) {
        val rootDir = File(directory)
        println("rename$directory")
        // 定义要重命名的目录前缀列表
        val optDirs = listOf("drawable", "mipmap", "xml", "layout")
        val skipType = listOf(".png", ".webp", ".jpg", ".jpeg")
        val mapping = mutableMapOf<File, File>()
        rootDir.walkTopDown().forEach { file ->

            if (file.isFile && !file.name.startsWith(prefix)) {
                val parentDirectoryName = file.parentFile.name

                // 检查父目录前缀是否匹配要重命名的目录前缀列表
                if (optDirs.any { parentDirectoryName.startsWith(it) }) {
                    val oldFileName = file.name
                    val newFileName = prefix + oldFileName
                    val newFilePath = File(file.parentFile, newFileName)
                    println(oldFileName + " ->> " + newFileName)
                    // 重命名文件
                    file.renameTo(newFilePath)

                    mapping.put(File(file.parentFile, oldFileName), newFilePath)
                }
            }
        }

        val replace = mapping.keys
            .map { it.name.substringBeforeLast(".") }
            .toSet()

        mapping.forEach { oF, nF ->
            // 更新文件中的资源引用
            if (!skipType.any { nF.name.endsWith(it) }) {
                replace.forEach {
                    updateResourceReferences(nF, it, nF.name)
                }
            }
        }
    }

    fun updateResourceReferences(file: File, originalFileName: String, newFileName: String) {

        val content = file.readText()

        // 在文件内容中替换旧文件名为新文件名
        val updatedContent = content.replace(
            originalFileName.substringBeforeLast("."),
            newFileName.substringBeforeLast(".")
        )

        file.writeText(updatedContent)
    }

//fun parseXml(inputStream: InputStream) {
//    val factory = XmlPullParserFactory.newInstance()
//    factory.isNamespaceAware = true
//    val parser: XmlPullParser = factory.newPullParser()
//    parser.setInput(inputStream, null)
//
//    var eventType: Int = parser.eventType
//    while (eventType != XmlPullParser.END_DOCUMENT) {
//        when (eventType) {
//            XmlPullParser.START_TAG -> {
//                val tagName: String = parser.name
//                val attributesCount = parser.attributeCount
//                for (i in 0 until attributesCount) {
//                    val attributeName: String = parser.getAttributeName(i)
//                    val attributeValue: String = parser.getAttributeValue(i)
//
//                    if (attributeValue.startsWith("@color/")) {
//                        // 输出满足条件的标签及其属性内容
//                        println("Tag: $tagName, Attribute: $attributeName=$attributeValue")
//                    }
//                }
//            }
//        }
//        eventType = parser.next()
//    }
//}
}

