package com.example.thirdlibrary.leet

import org.junit.Test

/**
 * Created by zyx on 19-10-16.
 */
class KthOrder {


    @Test
    fun testKthOrder() {
        println("prefix: ${testKthOrder(681692778, 351251360)}")
    }

    fun testKthOrder(n: Int, k: Int): Int {
        var pointerNum = 1L
        var result = 1L
        while (pointerNum != k.toLong()) {
            val count = getCount(result, n.toLong())
            if (pointerNum + count > k) {
                result *= 10
                pointerNum++
            } else {
                result++
                pointerNum += count
            }
        }
        return result.toInt()
    }

    fun getCount(prefix: Long, n: Long): Long {
        var count = 0L
        var current = prefix
        var next = prefix + 1
        while (current <= n) {
            count += Math.min(n + 1, next) - current
            current *= 10
            next *= 10
        }
        return count
    }
}