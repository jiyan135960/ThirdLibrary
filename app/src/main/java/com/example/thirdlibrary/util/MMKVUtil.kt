package com.example.thirdlibrary.util

import com.tencent.mmkv.MMKV

/**
 * Created by zyx on 19-8-23.
 */
object MMKVUtil {
    val mmkv = MMKV.defaultMMKV()
    inline fun <reified T> getByKey(key: String): T? =
        mmkv.getString(key, null)?.toBean<T>()
}

inline fun <reified T> String.getFromMMKV(): T? = MMKVUtil.getByKey(this)

fun Float.putMMKV(key: String): Boolean = MMKVUtil.mmkv.putFloat(key, this).commit()

fun Int.putMMKV(key: String): Boolean = MMKVUtil.mmkv.putInt(key, this).commit()

fun String.putMMKV(key: String): Boolean = MMKVUtil.mmkv.putString(key, this).commit()

fun Set<String>.putMMKV(key: String): Boolean = MMKVUtil.mmkv.putStringSet(key, this).commit()

fun Boolean.putMMKV(key: String): Boolean = MMKVUtil.mmkv.putBoolean(key, this).commit()

fun Long.putMMKV(key: String): Boolean = MMKVUtil.mmkv.putLong(key, this).commit()

fun Any.putMMKV(key: String): Boolean = MMKVUtil.mmkv.putString(key, this.toJson()).commit()