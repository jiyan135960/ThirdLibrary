package com.example.thirdlibrary.retrofit

import com.example.thirdlibrary.function.recommend.RecommendService
import com.example.thirdlibrary.util.RetrofitUtil
import com.example.thirdlibrary.util.fieldLoop
import com.example.thirdlibrary.util.subscribeWithError
import org.junit.Test


class RetrofitUtilTest {

    @Test
    fun testRecommendMusicRealRequest() {
        val recommendService: RecommendService = RetrofitUtil.createService()
        val recommendMusic = recommendService.recommendMusic()
        recommendMusic.fieldLoop().subscribeWithError { println("${it.first}:${it.second}") }
    }


}