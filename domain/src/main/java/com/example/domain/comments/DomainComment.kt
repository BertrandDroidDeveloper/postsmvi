package com.example.domain.comments

data class DomainComment(
    val postId: Int,
    val id: Int,
    val name: String,
    val body: String
)