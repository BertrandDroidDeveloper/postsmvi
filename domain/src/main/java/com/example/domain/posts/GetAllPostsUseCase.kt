package com.example.domain.posts

import com.example.domain.model.ViewState
import io.reactivex.Observable

interface GetAllPostsUseCase {
    fun execute(): Observable<ViewState>
}