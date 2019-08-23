package com.example.thirdlibrary.base

import android.app.Application
import com.tencent.mmkv.MMKV

/**
 * Created by zyx on 19-8-23.
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initMMKV()
    }

    private fun initMMKV() {
        MMKV.initialize(this)
    }

}