package com.example.thirdlibrary.leet

import org.junit.Test

/**
 * Created by zyx on 19-10-19.
 */
class LFUCache(val maxCache: Int) {
    /**
    设计并实现最不经常使用（LFU）缓存的数据结构。它应该支持以下操作：get 和 put。

    get(key) - 如果键存在于缓存中，则获取键的值（总是正数），否则返回 -1。
    put(key, value) - 如果键不存在，请设置或插入值。当缓存达到其容量时，它应该在插入新项目之前，使最不经常使用的项目无效。在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，最近最少使用的键将被去除。

    进阶：
    你是否可以在 O(1) 时间复杂度内执行两项操作？

    示例：

    LFUCache cache = new LFUCache( 2 /* capacity (缓存容量) */ );

    cache.put(1, 1);
    cache.put(2, 2);
    cache.get(1);       // 返回 1
    cache.put(3, 3);    // 去除 key 2
    cache.get(2);       // 返回 -1 (未找到key 2)
    cache.get(3);       // 返回 3
    cache.put(4, 4);    // 去除 key 1
    cache.get(1);       // 返回 -1 (未找到 key 1)
    cache.get(3);       // 返回 3
    cache.get(4);       // 返回 4

    //测试
    ["LFUCache","put","put","get","put","get","get","put","get","get","get"]
    [[2],[1,1],[2,2],[1],[3,3],[2],[3],[4,4],[1],[3],[4]]
    //结果
    [null,null,null,1,null,-1,3,null,-1,3,4]
     */


    /**
     * put与get都算使用频率
     */
    private var minFrequency = 0//记录最小频率用于删除
    private val keyFrequency = HashMap<Int, Pair<Int, Int>>() //用于存储 key和(频率,位置)的map
    private val frequencyValues = HashMap<Int, MutableList<Pair<Int, Int>>>() //用于存储 频率和key-值列表


    fun get(key: Int): Int {
        val pair = keyFrequency[key] ?: return -1
        // 使用频率 +1
        val (value, keyFrequencyValue) = updateAddFrequencyMap(pair.first, pair.second)
        keyFrequency[key] = keyFrequencyValue
        return value
    }


    fun put(key: Int, value: Int) {
        if (keyFrequency.containsKey(key)) {
            val frequencyIndex = keyFrequency[key]!!
            updateAddFrequencyMap(frequencyIndex.first, frequencyIndex.second)
        } else {
            if (maxCache <= keyFrequency.size) {
                removeMinFrequency()
            }
            insertNew(key, value)
            updateMinFrequency(1)
        }
    }

    private fun removeMinFrequency() {
        val keyValues = frequencyValues[minFrequency] ?: return
        val keyValue = keyValues.removeAt(0)
        if (keyValues.isEmpty()) frequencyValues.remove(minFrequency)
        keyFrequency.remove(keyValue.first)
    }

    //更新并为频率加1
    private fun updateAddFrequencyMap(
        currentFrequency: Int,
        currentIndex: Int
    ): Pair<Int, Pair<Int, Int>> {
        val value = removeIndexValueOnFrequency(currentFrequency, currentIndex)
        val newFrequency = currentFrequency + 1
        val values = frequencyValues.getOrPut(newFrequency, { mutableListOf() })
        values.add(value)
        updateMinFrequency(newFrequency)
        return Pair(value.second, Pair(newFrequency, values.size - 1))
    }

    //更新最小频率
    private fun updateMinFrequency(newFrequency: Int) {
        minFrequency = if (frequencyValues.containsKey(minFrequency)) {
            Math.min(newFrequency, minFrequency)
        } else {
            newFrequency
        }
    }


    //移除频率map上的对应index的值,并移除key上
    private fun removeIndexValueOnFrequency(frequency: Int, index: Int): Pair<Int, Int> {
        val values = frequencyValues[frequency] ?: throw NullPointerException()
        val value = values.removeAt(index)
        if (values.isEmpty()) frequencyValues.remove(frequency)
        return value
    }


    //插入新的key
    private fun insertNew(key: Int, value: Int) {
        val values = frequencyValues.getOrPut(1, { mutableListOf() })
        values.add(Pair(key, value))
        keyFrequency[key] = Pair(1, values.size - 1)
    }
}

class TestLFU {


    @Test
    fun mainTest() {
        val lfu = LFUCache(2)
        //["LFUCache","put","put","put","put","put","get","put","get","get","get"]
        //[[2],[1,1],[1,11],[3,2],[3,3],[2,2],[1],[4,4],[1],[3],[4]]
        lfu.put(1,1)
        lfu.put(1,11)
        lfu.put(3,2)
        lfu.put(3,3)
        lfu.put(2,2)
        lfu.get(1)
        lfu.put(4,4)
        lfu.get(1)
        lfu.get(3)
        lfu.get(4)
    }

}