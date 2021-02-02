package com.demotask.adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demotask.databinding.ListItemUserListingBinding
import com.demotask.models.User
import com.demotask.views.SpacesItemDecoration
import kotlinx.android.synthetic.main.list_item_user_listing.view.*

class UsersAdapter(var context: Context, var usersList: List<User>) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

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
            Glide.with(context).load(result.image).circleCrop().into(binding.userLayout.userImg)
            binding.rvImgs.apply {
                addItemDecoration(SpacesItemDecoration(2))
                val gridLayoutManager =
                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                //for even
                if (result.items.size % 2 == 0) {
                    gridLayoutManager.spanSizeLookup = object :
                        GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int = 1
                    }
                } else { //for odd
                    gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int =
                            if (position == 0) 2 else 1
                    }
                }

                layoutManager = gridLayoutManager
                adapter = imagesAdapter
            }
        }
    }
}