package com.demotask.repository

import androidx.lifecycle.MutableLiveData
import com.demotask.api.UsersApi
import com.demotask.models.Data
import com.demotask.models.User
import com.demotask.models.UsersResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    var usersApi: UsersApi
) {

    var data: Data? = null

    suspend fun getUsers(offset: Int, limit: Int): Data? {
        val apiResponse = usersApi.usersList(offset, limit)

        if (apiResponse.isSuccessful) {
            val usersResponse: UsersResponse? = apiResponse.body()

            usersResponse?.let { it ->
                if (it.status) {
                    //success response
                    data = it.data
                }
            }

        }
        return data
    }
}