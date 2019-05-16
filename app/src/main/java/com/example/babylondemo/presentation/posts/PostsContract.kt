package com.example.babylondemo.presentation.posts

import com.example.domain.model.ViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface PostsContract {
    interface View : MvpView {
        fun loadAllPostsIntent(): Observable<Boolean>
        fun render(viewState: ViewState)
    }
}