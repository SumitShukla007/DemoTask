package com.demotask.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demotask.adapter.UsersAdapter
import com.demotask.databinding.ActivityMainBinding
import com.demotask.models.User
import com.demotask.utils.CheckInternet
import com.demotask.viewmodels.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName
    var currentOffset = 10
    var currentLimit = 10

    //binding
    lateinit var binding: ActivityMainBinding
    var usersList = mutableListOf<User>()

    //viewmodel
    val usersViewModel: UsersViewModel by viewModels()

    //recycler adapter
    lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        getUsersData()
    }

    private fun initViews() {
        binding.apply {
            rvUsersList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                usersAdapter = UsersAdapter(this@MainActivity, usersList)
                adapter = usersAdapter
            }
        }
    }

    private fun getUsersData() {
        if (CheckInternet.isConnected(this)) {
            usersViewModel.getUsersList(currentOffset, currentLimit)
                .observe(this, Observer { listData ->
                    if (listData.isEmpty()) {
                        Log.d(TAG, "List is empty!")
                    } else {
                        Log.d(TAG, "getUsersData: $listData")
                        usersList.addAll(listData)
                        usersAdapter.notifyDataSetChanged()
                    }
                })
        }
    }
}