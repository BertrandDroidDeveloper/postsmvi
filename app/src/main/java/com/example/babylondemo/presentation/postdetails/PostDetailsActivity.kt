package com.example.babylondemo.presentation.postdetails

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import com.example.babylondemo.R
import com.example.babylondemo.ext.toDomain
import com.example.babylondemo.presentation.model.UiPost
import com.example.babylondemo.presentation.posts.PostsActivity
import com.example.domain.model.PostDetailViewState
import com.example.domain.model.ViewState
import com.example.domain.posts.DomainPost
import com.hannesdorfmann.mosby3.mvi.MviActivity
import dagger.android.AndroidInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_post_details.*
import javax.inject.Inject

class PostDetailsActivity : MviActivity<PostDetailsContract.View, PostDetailsPresenter>(), PostDetailsContract.View {

    @Inject
    lateinit var postDetailsPresenter: PostDetailsPresenter


    lateinit var model: DomainPost

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)
        TransitionManager.beginDelayedTransition(post_details_container)
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        model = intent.getParcelableExtra<UiPost>(PostsActivity.POST_KEY).toDomain()
        title = model.title
    }

    override fun loadPostDetailsIntent() = Observable.just(model)

    override fun render(viewState: ViewState) {
        when (viewState) {
            ViewState.Loading -> renderLoadingState()
            is PostDetailViewState.Success -> renderSuccessState(viewState)
            is ViewState.Failure -> renderFailureState(viewState)
            ViewState.Empty -> renderEmptyState()
        }
    }

    private fun renderEmptyState() {
        pbProgress.visibility = View.GONE
        groupMain.visibility = View.GONE
        tvErrorView.visibility = View.VISIBLE
    }

    private fun renderFailureState(failure: ViewState.Failure) {
        pbProgress.visibility = View.GONE
        groupMain.visibility = View.GONE
        tvErrorView.visibility = View.VISIBLE
        tvErrorView.text = failure.message
    }

    private fun renderSuccessState(success: PostDetailViewState.Success) {
        pbProgress.visibility = View.GONE
        groupMain.visibility = View.VISIBLE
        tvErrorView.visibility = View.GONE

        with(success.domainPostDetails) {
            tvBody.text = body
            tvAuthor.text = author
            tvTotalComments.text = resources.getString(R.string.comments_label, totalComments)
        }
    }

    private fun renderLoadingState() {
        tvErrorView.visibility = View.GONE
        groupMain.visibility = View.GONE
        pbProgress.visibility = View.VISIBLE
    }

    override fun createPresenter() = postDetailsPresenter
}