package com.example.babylondemo.presentation.posts

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.babylondemo.R
import com.example.babylondemo.ext.toUi
import com.example.babylondemo.presentation.adapter.PostsAdapter
import com.example.babylondemo.presentation.postdetails.PostDetailsActivity
import com.example.domain.model.AllPostsViewState
import com.example.domain.model.ViewState
import com.example.domain.model.ViewState.*
import com.example.domain.posts.DomainPost
import com.hannesdorfmann.mosby3.mvi.MviActivity
import dagger.android.AndroidInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class PostsActivity : MviActivity<PostsContract.View, PostsPresenter>(), PostsContract.View {

    @Inject
    lateinit var postsPresenter: PostsPresenter

    private lateinit var postsAdapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvPosts.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        postsAdapter = PostsAdapter(::onPostSelected)
        title = getString(R.string.posts_toolbar_title)
        rvPosts.adapter = postsAdapter
    }

    override fun loadAllPostsIntent() = Observable.just(true)

    override fun createPresenter(): PostsPresenter = postsPresenter

    override fun render(viewState: ViewState) {
        when (viewState) {
            Loading -> renderLoadingState()
            is AllPostsViewState.Success -> renderSuccessState(viewState)
            is ViewState.Failure -> renderFailureState(viewState)
            Empty -> renderEmptyState()
        }
    }

    private fun renderSuccessState(successState: AllPostsViewState.Success) {
        pbProgress.visibility = View.GONE
        tvErrorView.visibility = View.GONE
        rvPosts.visibility = View.VISIBLE
        postsAdapter.setData(successState.domainPosts)
    }

    private fun renderEmptyState() {
        pbProgress.visibility = View.GONE
        rvPosts.visibility = View.GONE
        tvErrorView.visibility = View.VISIBLE
    }

    private fun renderFailureState(failure: Failure) {
        pbProgress.visibility = View.GONE
        rvPosts.visibility = View.GONE
        tvErrorView.visibility = View.VISIBLE
        tvErrorView.text = failure.message
    }

    private fun renderLoadingState() {
        rvPosts.visibility = View.INVISIBLE
        tvErrorView.visibility = View.GONE
        pbProgress.visibility = View.VISIBLE
    }

    private fun onPostSelected(domainPost: DomainPost) {
        startActivity(
            Intent(this, PostDetailsActivity::class.java)
                .putExtra(POST_KEY, domainPost.toUi()),
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        )
    }

    companion object {
        const val POST_KEY = "post"
    }
}
