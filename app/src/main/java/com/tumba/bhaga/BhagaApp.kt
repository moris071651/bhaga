package com.tumba.bhaga

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BhagaApp : Application() {
    companion object {
        lateinit var instance: BhagaApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
