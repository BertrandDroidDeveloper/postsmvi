package com.example.data.api

import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val postsApi: PostsApi) : RemoteDataSource {


    override fun getAllPosts() = postsApi.getAllPosts()

    override fun getAllUsers() = postsApi.getAllUsers()

    override fun getAllComments() = postsApi.getAllComments()

}