package com.tiorisnanto.myapplication.ui.home.fragment.message.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tiorisnanto.myapplication.R
import com.tiorisnanto.myapplication.ui.home.fragment.message.model.ArticlesItem

class SliderAdapter(val dataNews: List<ArticlesItem?>?):RecyclerView.Adapter<SliderAdapter.MyViewHolder>(){
    class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        val imgSlider: ImageView = view.findViewById(R.id.img_slider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slider,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(dataNews?.get(position)?.urlToImage).into(holder.imgSlider)
    }

    override fun getItemCount(): Int {
        return dataNews?.size!!
    }

}