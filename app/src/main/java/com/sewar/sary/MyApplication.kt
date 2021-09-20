package com.sewar.sary

import android.app.Application
import android.content.pm.ApplicationInfo
import android.webkit.WebView
import com.jakewharton.threetenabp.AndroidThreeTen
import com.sewar.sary.injection.component.ApplicationComponent
import com.sewar.sary.injection.component.DaggerApplicationComponent
import timber.log.Timber

class MyApplication : Application() {
    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugReportingTree())
        }

        AndroidThreeTen.init(this)

        if (applicationInfo.flags != 0 and ApplicationInfo.FLAG_DEBUGGABLE) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }

    internal open class DebugReportingTree : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String {
            val className = element.className
            return if ("." in className) {
                className.substring(className.lastIndexOf(".") + 1)
            } else {
                className
            }
        }
    }
}
