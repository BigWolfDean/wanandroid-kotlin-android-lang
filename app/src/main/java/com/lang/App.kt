package com.lang

import android.app.Application

/**
 * Created by lang on 2018/3/30.
 */
class App : Application() {
    companion object {
        // 伴生对象
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}