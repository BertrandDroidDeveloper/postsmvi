package com.example.babylondemo

import com.example.babylondemo.presentation.posts.PostsPresenter
import com.example.domain.model.AllPostsViewState
import com.example.domain.posts.DomainPost
import com.example.domain.model.ViewState
import com.example.domain.posts.GetAllPostsUseCase
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class PostsPresenterTest {

    private val responseList = listOf(DomainPost(0, 0, "Hello", "World"))
    private val getAllPostsUseCase: GetAllPostsUseCase = mock()

    private lateinit var presenter: PostsPresenter
    private lateinit var postsViewRobot: PostsViewRobot

    init {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Before
    fun setup() {
        presenter = PostsPresenter(getAllPostsUseCase)
        postsViewRobot = PostsViewRobot(presenter)
    }

    @Test
    fun `event propagation when retrieval is successful`() {
        val successState = AllPostsViewState.Success(responseList)
        val stateList = listOf(ViewState.Loading, successState)
        given(getAllPostsUseCase.execute()).willReturn(Observable.fromIterable(stateList))

        postsViewRobot.fireLoadAllPostsIntent()

        postsViewRobot.assertViewStateRendered(*stateList.toTypedArray())
    }

    @Test
    fun `event propagation when retrieval fails`() {
        val stateList = listOf(ViewState.Loading, ViewState.Failure("Unknown Error"))
        given(getAllPostsUseCase.execute()).willReturn(Observable.fromIterable(stateList))

        postsViewRobot.fireLoadAllPostsIntent()

        postsViewRobot.assertViewStateRendered(*stateList.toTypedArray())
    }

    @Test
    fun `event propagation when result is empty`() {
        val stateList = listOf(ViewState.Loading, ViewState.Empty)
        given(getAllPostsUseCase.execute()).willReturn(Observable.fromIterable(stateList))

        postsViewRobot.fireLoadAllPostsIntent()

        postsViewRobot.assertViewStateRendered(*stateList.toTypedArray())
    }
}