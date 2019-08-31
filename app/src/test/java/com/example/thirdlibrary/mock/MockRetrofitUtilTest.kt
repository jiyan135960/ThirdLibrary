package com.example.thirdlibrary.mock

import com.example.thirdlibrary.function.recommend.bean.RecommendMusic
import io.reactivex.Observable
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

/**
 * Created by zyx on 19-8-31.
 */
class MockRetrofitUtilTest {

    val retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl("https://www.mxnzp.com/api/")
        .build()

    val networkBehavior = NetworkBehavior.create()
    val mockRetrofit = MockRetrofit.Builder(retrofit)
        .networkBehavior(networkBehavior)
        .build()

    val behaviorDelegate = mockRetrofit.create(RecommendMusic::class.java)


    @Test
    fun mockRecommendMusic(): Observable<List<RecommendMusic>> {

    }
}