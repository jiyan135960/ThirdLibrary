package com.example.thirdlibrary.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thirdlibrary.R
import com.example.thirdlibrary.base.MainApplication

/**
 * Created by zyx on 2019/9/4.
 */
class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var data = mutableListOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(MainApplication.instance).inflate(R.layout.main_item, null)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        Glide.with(holder.itemView).load(data[position]).into(holder.imageView)
    }

}

