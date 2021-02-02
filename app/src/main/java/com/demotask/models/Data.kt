package com.demotask.models


import com.google.gson.annotations.SerializedName

data class Data(
  @SerializedName("has_more")
  val hasMore: Boolean,
  @SerializedName("users")
  val users: List<User>
)