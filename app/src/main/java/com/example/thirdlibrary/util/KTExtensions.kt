package com.example.thirdlibrary.util

/**
 * Created by zyx on 2019/8/23.
 */

//mmkv 扩展方法

fun Boolean.putShare(key: String) {
}

//collection 扩展方法

fun <T> T.toList() = mutableListOf(this)

fun <T, K> T.beanToMap(key: K) = mutableMapOf(Pair(key, this))
