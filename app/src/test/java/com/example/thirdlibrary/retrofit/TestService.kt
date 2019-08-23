package com.example.thirdlibrary.retrofit

import com.example.thirdlibrary.base.CommResponse
import io.reactivex.Observable
import retrofit2.http.GET

//https://www.mxnzp.com/api
interface TestService {
    //获取每日推荐列表
    @GET("/music/recommend/list ")
    fun musicRecommendList(): Observable<CommResponse<MusicBean>>
}

/**
 * pic_huge： 最大的图片地址
 * ting_uid： 歌手id
 * si_proxycompany： 公司信息
 * author： 歌手姓名
 * info： 音乐描述
 * album_title： 专辑名称
 * title： 音乐名称
 * language： 音乐语言
 * pic_big： 歌曲大图
 * pic_singer：歌手图片
 * publishtime： 发布时间
 * pic_premium： 歌曲图片-中
 * pic_small： 歌曲图片-小
 * song_id： 歌曲id，查询歌曲详情需要用到
 */
data class MusicBean(
    val album_title: String,
    val author: String,
    val info: String,
    val language: String,
    val pic_big: String,
    val pic_huge: String,
    val pic_premium: String,
    val pic_singer: String,
    val pic_small: String,
    val publishtime: String,
    val si_proxycompany: String,
    val song_id: String,
    val ting_uid: String,
    val title: String
)
