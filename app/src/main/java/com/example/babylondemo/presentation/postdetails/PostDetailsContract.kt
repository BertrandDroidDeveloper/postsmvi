package com.example.babylondemo.presentation.postdetails

import com.example.domain.posts.DomainPost
import com.example.domain.model.ViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface PostDetailsContract {
    interface View : MvpView {
        fun loadPostDetailsIntent(): Observable<DomainPost>
        fun render(viewState: ViewState)
    }
}