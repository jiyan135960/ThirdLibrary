package com.example.thirdlibrary.base

import android.app.Application
import com.tencent.mmkv.MMKV

/**
 * Created by zyx on 19-8-23.
 */
class MainApplication : Application() {

    companion object {
        lateinit var instance: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initMMKV()
    }

    private fun initMMKV() {
        MMKV.initialize(this)
    }

}