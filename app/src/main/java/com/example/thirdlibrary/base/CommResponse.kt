package com.example.thirdlibrary.base

/**
 * Created by zyx on 19-8-23.
 * 通用数据结构
 */
data class CommResponse<T>(val code: Int, val msg: String, val data: T)
