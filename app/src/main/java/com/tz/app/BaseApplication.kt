package com.tz.app

import android.app.Application
import android.content.Context

abstract class BaseApplication : Application() {

    private val applicationDelegate by lazy<ApplicationDelegate> {
        getAppDelegate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        applicationDelegate.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        applicationDelegate.onCreate(this)
    }

    abstract fun getAppDelegate():ApplicationDelegate
}