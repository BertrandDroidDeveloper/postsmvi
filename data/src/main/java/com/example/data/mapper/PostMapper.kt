package com.example.data.mapper

import com.example.data.models.DataPost
import com.example.domain.posts.DomainPost
import javax.inject.Inject

class PostMapper @Inject constructor() : Mapper<DataPost, DomainPost> {

    override fun map(item: DataPost) = DomainPost(
        userId = item.userId,
        postId = item.postId,
        title = item.title,
        body = item.body
    )
}