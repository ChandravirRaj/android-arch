package com.androboy.androidarch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ArchApp: Application() {
    companion object{
        var INSTANCE: ArchApp = ArchApp()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}