package com.cvt.admin

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CVTApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
