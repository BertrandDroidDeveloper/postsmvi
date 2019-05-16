package com.example.data.models

import com.google.gson.annotations.SerializedName

data class DataPost(
    val userId: Int,
    @SerializedName("id") val postId: Int,
    val title: String,
    val body: String
)