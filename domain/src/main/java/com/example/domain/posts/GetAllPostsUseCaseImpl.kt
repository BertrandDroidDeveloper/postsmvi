package com.example.domain.posts

import com.example.domain.model.AllPostsViewState
import com.example.domain.model.ViewState
import com.example.domain.model.ViewState.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetAllPostsUseCaseImpl @Inject constructor(private val postsRepository: PostRepository) :
    GetAllPostsUseCase {

    override fun execute(): Observable<ViewState> = postsRepository.getAllPosts()
        .subscribeOn(Schedulers.io())
        .map { if (it.isEmpty()) Empty else AllPostsViewState.Success(it) }
        .onErrorReturn { Failure(it.localizedMessage ?: "Unknown Error") }
        .startWith(Loading)
}