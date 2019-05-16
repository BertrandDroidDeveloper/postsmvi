package com.example.babylondemo.presentation.postdetails

import com.example.domain.model.ViewState
import com.example.domain.postdetails.GetPostDetailsUseCase
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class PostDetailsPresenter @Inject constructor(private val getPostDetailsUseCase: GetPostDetailsUseCase) :
    MviBasePresenter<PostDetailsContract.View, ViewState>() {

    override fun bindIntents() {
        subscribeViewState(
            intent { it.loadPostDetailsIntent() }
                .switchMap { getPostDetailsUseCase.execute(it) }
                .observeOn(AndroidSchedulers.mainThread())
        ) { view, viewState -> view.render(viewState) }
    }
}