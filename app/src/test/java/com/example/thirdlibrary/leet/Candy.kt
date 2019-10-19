package com.example.thirdlibrary.leet

import org.junit.Test

/**
 * Created by zyx on 2019/10/17.
 */
class Candy {
    /**
    老师想给孩子们分发糖果，有 N 个孩子站成了一条直线，老师会根据每个孩子的表现，预先给他们评分。

    你需要按照以下要求，帮助老师给这些孩子分发糖果：

    每个孩子至少分配到 1 个糖果。
    相邻的孩子中，评分高的孩子必须获得更多的糖果。
    那么这样下来，老师至少需要准备多少颗糖果呢？

    示例 1:

    输入: [1,0,2]
    输出: 5
    解释: 你可以分别给这三个孩子分发 2、1、2 颗糖果。
    示例 2:

    输入: [1,2,2]
    输出: 4
    解释: 你可以分别给这三个孩子分发 1、2、1 颗糖果。
    第三个孩子只得到 1 颗糖果，这已满足上述两个条件。
     */

    fun candy(ratings: IntArray): Int {
        val leftCandies = IntArray(ratings.size) { 1 }
        val rightCandies = IntArray(ratings.size) { 1 }
        ratings.reduceIndexed { index, acc, i ->
            if (acc >= i) leftCandies[index] = 1
            else leftCandies[index] = leftCandies[index - 1] + 1
            return@reduceIndexed i
        }
        ratings.reduceRightIndexed { index, acc, i ->
            if (i >= acc) rightCandies[index] = 1
            else rightCandies[index] = rightCandies[index+1] + 1
            return@reduceRightIndexed acc
        }
        var count = 0
        for (index in ratings.indices) {
            count += Math.max(leftCandies[index],rightCandies[index])
        }
        return count
    }

    @Test
    fun testCandy() {
        print("candy:${candy(intArrayOf(1,0,2))}")
    }
}