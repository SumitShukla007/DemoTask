package com.demotask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demotask.R
import com.demotask.models.User

class UsersAdapter(var context: Context, var usersList: ArrayList<User>) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    lateinit var onItemClicked: OnItemClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_even, parent, false)
        return UsersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: UsersAdapter.UsersViewHolder, position: Int) {
        val data = usersList[position]
        holder.bindData(data)
    }

    inner class UsersViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                if (::onItemClicked.isInitialized) {
                    onItemClicked.onClicked(usersList[adapterPosition])
                }
            }
        }

        fun bindData(result: User) {

        }
    }

    fun setOnListClickListener(onItemClicked: OnItemClicked) {
        this.onItemClicked = onItemClicked
    }

    interface OnItemClicked {
        fun onClicked(data: User)
    }

}