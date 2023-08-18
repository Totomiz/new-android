package com.tz.mad

import android.app.Application
import android.util.Log
import com.tz.app.ApplicationDelegate
import com.tz.app.BaseApplication
import com.tz.mad.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

private const val TAG = "MadApp"

class MadApp : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun getAppDelegate() = KoApp(this)


}

class KoApp(application: Application) : ApplicationDelegate(application) {
    override fun onCreate(application: Application) {
        Log.d(TAG, "onCreate: ")
        startKoin {
            androidLogger()
            androidContext(this@KoApp.application)
            modules(appModules)
        }
    }

}