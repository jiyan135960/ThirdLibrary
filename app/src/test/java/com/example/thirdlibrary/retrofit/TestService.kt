package com.example.thirdlibrary.retrofit

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

//https://ghibliapi.herokuapp.com
interface TestService {
    @GET("/species")
    fun getSpecies(@Query("name") name: String): Observable<String>
}