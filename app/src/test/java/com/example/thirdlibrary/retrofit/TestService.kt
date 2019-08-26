package com.example.thirdlibrary.retrofit

import com.example.thirdlibrary.base.CommResponse
import com.example.thirdlibrary.bean.MusicBean
import io.reactivex.Observable
import retrofit2.http.GET

//https://www.mxnzp.com/api
interface TestService {
    //获取每日推荐列表
    @GET("/music/recommend/list ")
    fun musicRecommendList(): Observable<CommResponse<MusicBean>>
}

