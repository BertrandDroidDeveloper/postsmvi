package com.example.domain.posts

import io.reactivex.Observable

interface PostRepository {
    fun getAllPosts(): Observable<List<DomainPost>>
}