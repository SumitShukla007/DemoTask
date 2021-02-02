package com.demotask.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demotask.adapter.UsersAdapter
import com.demotask.databinding.ActivityMainBinding
import com.demotask.models.User
import com.demotask.utils.CheckInternet
import com.demotask.utils.gone
import com.demotask.utils.show
import com.demotask.viewmodels.UsersViewModel
import com.demotask.views.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

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

        usersViewModel.usersList.observe(this, Observer { listData ->
            if (listData.isEmpty()) {
                Log.d(TAG, "List is empty!")
            } else {
                binding.txtNoData.visibility = View.GONE
                usersList.addAll(usersList.size,listData)
                usersAdapter.notifyItemRangeInserted(usersList.size,listData.size)
                Log.d(TAG, "getUsersData: $currentOffset $usersList")
            }
        })
    }

    private fun initViews() {
        binding.apply {
            rvUsersList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                addItemDecoration(SpacesItemDecoration(4))
                usersAdapter = UsersAdapter(this@MainActivity, usersList)
                adapter = usersAdapter

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        val lManager = rvUsersList.layoutManager as LinearLayoutManager
                        val lastItemPosition = lManager.findLastCompletelyVisibleItemPosition()
                        val numItems: Int = rvUsersList.adapter!!.itemCount

                        //last item reached
                        if (dy > 0) {
                            Log.d(TAG, "onScrolled:$lastItemPosition and $numItems")
                            if (lastItemPosition == numItems - 1) {
                                Log.d(TAG, "match:$lastItemPosition  $numItems")

                                Handler(Looper.getMainLooper()).postDelayed({
                                    progress.show()
                                    currentOffset++
                                    getRemoteUsersList(currentOffset, currentLimit)
                                }, 2000)
                            }
                        }
                    }
                })
            }
        }
    }

    private fun getUsersData() {
        if (CheckInternet.isConnected(this)) {
            getRemoteUsersList(currentOffset, currentLimit)
        }
    }

    private fun getRemoteUsersList(offset: Int, limit: Int) {
        binding.progress.gone()
        usersViewModel.getUsersList(offset, limit)
    }
}