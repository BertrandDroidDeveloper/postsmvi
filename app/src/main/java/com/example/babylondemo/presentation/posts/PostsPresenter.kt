package com.example.babylondemo.presentation.posts

import com.example.domain.model.ViewState
import com.example.domain.posts.GetAllPostsUseCase
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class PostsPresenter @Inject constructor(private val getAllPostsUseCase: GetAllPostsUseCase) :
    MviBasePresenter<PostsContract.View, ViewState>() {

    override fun bindIntents() {
        subscribeViewState(intent { it.loadAllPostsIntent() }
            .switchMap { getAllPostsUseCase.execute() }
            .observeOn(AndroidSchedulers.mainThread())) { view, viewState -> view.render(viewState) }
    }
}