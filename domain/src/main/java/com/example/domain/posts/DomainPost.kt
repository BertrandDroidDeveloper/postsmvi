package com.example.domain.posts

data class DomainPost(
    val userId: Int,
    val postId: Int,
    val title: String,
    val body: String
)
