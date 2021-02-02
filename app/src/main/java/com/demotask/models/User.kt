package com.demotask.models


data class User(
    val image: String,
    val items: List<String>? = null,
    val name: String
)