package com.demotask.adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demotask.databinding.ListItemUserListingBinding
import com.demotask.models.User
import kotlinx.android.synthetic.main.list_item_user_listing.view.*

class UsersAdapter(var context: Context, var usersList: List<User>) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var imagesAdapter: ImagesAdapter

    override fun getItemCount(): Int = usersList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            ListItemUserListingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bindData(usersList[position])
    }

    inner class UsersViewHolder(var binding: ListItemUserListingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(result: User) {
            imagesAdapter = ImagesAdapter(context, result.items!!)
            binding.userLayout.txtName.text = result.name
            binding.rvImgs.apply {
                gridLayoutManager = GridLayoutManager(context, 2)
                if (result.items!!.size % 2 == 0) {
                    gridLayoutManager.spanSizeLookup = object :
                        GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int = 2
                    }
                } else {
                    gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int =
                            if (position % 2 == 0) 1 else 2
                    }
                }

                layoutManager = gridLayoutManager
                adapter = imagesAdapter
            }
        }
    }
}