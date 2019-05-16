package com.example.data.cache

import com.example.domain.comments.DomainComment
import com.example.domain.posts.DomainPost
import com.example.domain.postdetails.DomainUser
import javax.inject.Inject

class UsersLocalDataSource @Inject constructor() : LocalDataSourceImpl<DomainUser>()

class PostLocalDataSource @Inject constructor() : LocalDataSourceImpl<DomainPost>()

class CommentLocalDataSource @Inject constructor() : LocalDataSourceImpl<DomainComment>()