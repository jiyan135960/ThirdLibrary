package com.example.thirdlibrary.main

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.thirdlibrary.R

/**
 * Created by zyx on 2019/9/4.
 */
class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView = itemView.findViewById<ImageView>(R.id.iv_main_item)
}