package com.example.thirdlibrary.util

import com.example.thirdlibrary.base.CommResponse
import com.example.thirdlibrary.bean.MusicBean
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.rxkotlin.toObservable
import org.junit.Assert
import org.junit.Test
import java.lang.reflect.Field

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
        bean.data.toObservable()
            .flatMapIterable(Function<MusicBean, Iterable<Field>> { it.javaClass.declaredFields.toMutableList() },
                BiFunction<MusicBean, Field, String> { musicBean, field ->
                    field.isAccessible = true
                    return@BiFunction field.get(musicBean).toString()
                })
            .subscribe { Assert.assertEquals("data", "test", it) }
        Assert.assertEquals("code", 1, bean.code)
        Assert.assertEquals("msg", "msg", bean.msg)
    }

    @Test
    fun testToJson() {
        val musicBean = MusicBean()
        val musicResponse = CommResponse(1, "msg", musicBean.toList())
        val musicResponseListJson = musicResponse.toJson()
        val expectedMusicResponseListJson =
            "{\"code\":1,\"msg\":\"msg\",\"data\":[{\"album_title\":\"\",\"author\":\"\",\"info\":\"\"," +
                    "\"language\":\"\",\"pic_big\":\"\",\"pic_huge\":\"\",\"pic_premium\":\"\",\"pic_singer\":\"\",\"pic_small\":\"\"," +
                    "\"publishtime\":\"\",\"si_proxycompany\":\"\",\"song_id\":\"\",\"ting_uid\":\"\",\"title\":\"\"}]}"
        Assert.assertEquals("musicBean", expectedMusicResponseListJson, musicResponseListJson)
    }
}