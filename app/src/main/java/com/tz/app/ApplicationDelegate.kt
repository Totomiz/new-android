package com.tz.app

import android.app.Application
import android.content.Context

abstract class ApplicationDelegate(val application: Application) {
    fun attachBaseContext(base: Context?) {}
    abstract fun onCreate(application: Application)
}