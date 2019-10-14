package com.example.thirdlibrary.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thirdlibrary.R
import com.example.thirdlibrary.function.recommend.RecommendService
import com.example.thirdlibrary.util.RetrofitUtil
import com.example.thirdlibrary.util.request
import io.reactivex.rxkotlin.toObservable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_main.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = MainAdapter()
        rv_main.adapter = adapter

        val service = RetrofitUtil.createService<RecommendService>()
        service.recommendMusic().flatMap { it.data.toObservable() }.map { it.pic_small }
            .toList().request {
                adapter.data = it
            }
    }
}
