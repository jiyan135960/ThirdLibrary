package com.example.thirdlibrary.util

import com.tencent.mmkv.MMKV

/**
 * Created by zyx on 19-8-23.
 */
object MMKVUtil {
    val mmkv = MMKV.defaultMMKV()
    inline fun <reified T> getByKey(key: String): T = mmkv.getString(key, null)?.toBean<T>()
}