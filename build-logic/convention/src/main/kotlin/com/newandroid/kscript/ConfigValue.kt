package com.newandroid.kscript

import org.gradle.api.JavaVersion

object ConfigValue {
    private const val SDK_VERSION = 34
    const val MIN_SDK_VERSION = 21
    const val COMPILE_SDK_VERSION = SDK_VERSION
    const val TARGET_SDK_VERSION = SDK_VERSION

    val JAVA_VERSION = JavaVersion.VERSION_17
}