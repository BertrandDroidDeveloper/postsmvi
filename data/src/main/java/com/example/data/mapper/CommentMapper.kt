package com.example.data.mapper

import com.example.data.models.DataComment
import com.example.domain.comments.DomainComment
import javax.inject.Inject

class CommentMapper @Inject constructor() : Mapper<DataComment, DomainComment> {

    override fun map(item: DataComment) = DomainComment(
        postId = item.postId,
        id = item.id,
        name = item.name,
        body = item.body
    )
}