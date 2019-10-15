package com.example.thirdlibrary.hash

import com.example.thirdlibrary.function.recommend.bean.RecommendMusic
import org.junit.Test

/**
 * Created by zyx on 19-10-15.
 */
class TestHashCode {

    @Test
    fun testHashCode() {
        var testString = "a"
        printAnyHashCode(testString)
        testString = "b"
        printAnyHashCode(testString)
        val recommendMusic = RecommendMusic("a","b")
        //-483269983 ,-483269983
        printAnyHashCode(recommendMusic)
    }

    fun printAnyHashCode(any: Any) {
        println("string $any hash code: ${any.hashCode()}")
    }


}