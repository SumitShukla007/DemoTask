package com.demotask.models


data class UsersResponse(
    val data: Data,
    var message: Any? = null,
    val status: Boolean
)