package com.example.thirdlibrary.function.recommend

import com.example.thirdlibrary.base.CommResponse
import com.example.thirdlibrary.function.recommend.bean.RecommendMusic
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by zyx on 19-8-30.
 */
interface RecommendService {

    @GET("api/music/recommend/list ")
    fun recommendMusic(): Observable<CommResponse<List<RecommendMusic>>>

}