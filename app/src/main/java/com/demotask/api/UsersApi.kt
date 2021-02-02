package com.demotask.api

import com.demotask.models.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {

    @GET("users")
    suspend fun usersList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Response<UsersResponse>
}