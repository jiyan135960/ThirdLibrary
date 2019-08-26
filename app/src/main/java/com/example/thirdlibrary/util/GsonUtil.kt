package com.example.thirdlibrary.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by zyx on 19-8-23.
 */
object GsonUtil {
    val gson = Gson()
    inline fun <reified T> toJson(instance: T): String = gson.toJson(instance, object : TypeToken<T>() {}.type)
    inline fun <reified T> fromJson(jsonStr: String): T = gson.fromJson(jsonStr, object : TypeToken<T>() {}.type)
}

inline fun <reified T> T.toJson(): String = GsonUtil.toJson(this)
inline fun <reified T> String.toBean(): T = GsonUtil.fromJson(this)
