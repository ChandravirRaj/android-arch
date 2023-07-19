package com.androboy.androidarch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ArchApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}