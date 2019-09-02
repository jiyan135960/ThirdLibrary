package com.example.thirdlibrary.retrofit

import com.example.thirdlibrary.function.recommend.RecommendService
import com.example.thirdlibrary.util.RetrofitUtil
import com.example.thirdlibrary.util.fieldLoop
import com.example.thirdlibrary.util.subscribeWithError
import io.reactivex.Observable
import org.junit.Test
import retrofit2.http.*


class RetrofitUtilTest {

    @Test
    fun testRecommendMusicRealRequest() {
        val recommendService: RecommendService = RetrofitUtil.createService()
        val recommendMusic = recommendService.recommendMusic()
        recommendMusic.fieldLoop().subscribeWithError { println("${it.first}:${it.second}") }
    }

    @Test
    fun testPost() {
        val testRetrofit: TestRetrofit = RetrofitUtil.createService()
        val testRetrofitService = testRetrofit.testPost("head", "a", 123, "query")
        testRetrofitService.subscribeWithError { }

    }
}

interface TestRetrofit {
    @FormUrlEncoded
    @POST("/music/recommend")
    fun testPost(@Header("head") head: String, @Field("name") name: String, @Field("old") old: Int, @Query("query") query: String): Observable<Map<String, String>>

}
