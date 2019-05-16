package com.example.data.api

import com.example.data.models.DataComment
import com.example.data.models.DataPost
import com.example.data.models.DataUser
import io.reactivex.Single

interface RemoteDataSource {
    fun getAllPosts(): Single<List<DataPost>>
    fun getAllComments(): Single<List<DataComment>>
    fun getAllUsers(): Single<List<DataUser>>
}
