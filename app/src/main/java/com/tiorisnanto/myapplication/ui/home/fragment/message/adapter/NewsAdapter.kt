package com.tiorisnanto.myapplication.ui.home.fragment.message.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tiorisnanto.myapplication.R
import com.tiorisnanto.myapplication.ui.home.fragment.message.model.ArticlesItem

class NewsAdapter(val dataNews: List<ArticlesItem?>?) :
    RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imgNews = view.findViewById<View>(R.id.imgNews) as ImageView
        val txtNews = view.findViewById<View>(R.id.nameNews) as TextView
        val txtDate = view.findViewById<View>(R.id.publishNews) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_news, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtNews.text = dataNews?.get(position)?.title
        holder.txtDate.text = dataNews?.get(position)?.publishedAt
        Glide.with(holder.imgNews.context)
            .load(dataNews?.get(position)?.urlToImage)
            .into(holder.imgNews)
    }

    override fun getItemCount(): Int {
        return dataNews?.size!!
    }
}