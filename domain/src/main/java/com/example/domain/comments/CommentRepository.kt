package com.example.domain.comments

import io.reactivex.Observable

interface CommentRepository {
    fun getAllComments(): Observable<List<DomainComment>>
}