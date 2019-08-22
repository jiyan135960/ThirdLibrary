package com.example.thirdlibrary.retrofit

import com.example.thirdlibrary.util.RetrofitUtil
import org.junit.Test

class RetrofitUtilTest {

    @Test
    fun main() {
        val testService: TestService = RetrofitUtil.createService(TestService::class)
        testService.getSpecies("spirit")
    }

}