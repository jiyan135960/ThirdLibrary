package com.example.thirdlibrary.leet

import org.junit.Test

/**
 * Created by zyx on 2019/10/15.
 */
class TwoSumTest {

    @Test
    fun testTwoSum() {
        val intArray = intArrayOf(3,3)
        val result = twoSum2(intArray, 6)
        println("${result[0]},${result[1]}")
    }

    fun twoSum(nums: IntArray, target: Int): IntArray {
        val numberIndexMap = linkedMapOf<Int, Int>()
        nums.forEachIndexed { index, num ->
            val resultKey = target - num
            val isContainsSum = numberIndexMap.contains(resultKey)
            if (isContainsSum && index != numberIndexMap[resultKey] ?: 0) {
                val result = IntArray(2)
                result[0] = numberIndexMap[resultKey] ?: 0
                result[1] = index
                return result
            } else {
                numberIndexMap[num] = index
            }
        }
        throw NullPointerException()
    }

    fun twoSum2(nums: IntArray, target: Int): IntArray {
        nums.forEachIndexed { index, num ->
            for (internalIndex  in index+1 until nums.size) {
                if (nums[internalIndex] == target - num) {
                    return intArrayOf(index,internalIndex)
                }
            }
        }
        throw IllegalArgumentException()
    }
}