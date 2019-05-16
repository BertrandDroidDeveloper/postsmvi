package com.example.data.api

import com.example.data.BuildConfig
import com.example.data.models.DataComment
import com.example.data.models.DataPost
import com.example.data.models.DataUser
import io.reactivex.Single
import retrofit2.http.GET

interface PostsApi {
    @GET(BuildConfig.POSTS_ENDPOINT)
    fun getAllPosts(): Single<List<DataPost>>

    @GET(BuildConfig.USERS_ENDPOINT)
    fun getAllUsers(): Single<List<DataUser>>

    @GET(BuildConfig.COMMENTS_ENDPOINT)
    fun getAllComments(): Single<List<DataComment>>
}