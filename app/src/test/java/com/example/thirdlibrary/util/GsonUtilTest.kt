package com.example.thirdlibrary.util

import com.example.thirdlibrary.base.CommResponse
import com.example.thirdlibrary.retrofit.MusicBean
import io.reactivex.rxkotlin.toObservable
import org.junit.Test

/**
 * Created by zyx on 19-8-24.
 */
class GsonUtilTest {

    @Test
    fun testFromJson() {
        val musicResponseListJson =
            "{\"code\":1,\"msg\":\"msg\",\"data\":[{\"album_title\":\"test\",\"author\":\"test\",\"info\":\"test\",\"language\":\"test\"," +
                    "\"pic_big\":\"test\",\"pic_huge\":\"test\",\"pic_premium\":\"test\",\"pic_singer\":\"test\",\"pic_small\":\"test\"," +
                    "\"publishtime\":\"test\",\"si_proxycompany\":\"test\",\"song_id\":\"test\",\"ting_uid\":\"test\",\"title\":\"test\"}]}"
        val bean = musicResponseListJson.toBean<CommResponse<List<MusicBean>>>()
        bean.data.toObservable().subscribe { println(it) }
        println(bean.msg)
        println(bean.code)
    }

    @Test
    fun testToJson() {
        val musicBean = MusicBean()
        val musicResponse = CommResponse(1, "msg", musicBean.toList())
        val musicResponseListJson = musicResponse.beanToMap("1").toJson()
        print(musicResponseListJson)
    }
}