package com.demotask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demotask.databinding.ListItemImgsBinding

class ImagesAdapter(var context: Context, var imgsList: List<String>) :
    RecyclerView.Adapter<ImagesAdapter.ImgViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
       return ImgViewHolder(ListItemImgsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return imgsList.size
    }

    override fun onBindViewHolder(holder: ImagesAdapter.ImgViewHolder, position: Int) {
        val data = imgsList[position]
        holder.bindData(data)
    }

    inner class ImgViewHolder(var binding: ListItemImgsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(result: String) {
            Glide.with(context).load(result).into(binding.userImg)
        }
    }

}