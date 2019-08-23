package com.example.thirdlibrary.retrofit

import com.example.thirdlibrary.util.RetrofitUtil
import org.junit.Test

class RetrofitUtilTest {

    @Test
    fun testRetrofitUtilInit() {
        val testService: TestService = RetrofitUtil.createService(TestService::class)
        val recommendList = testService.musicRecommendList()
    }

}