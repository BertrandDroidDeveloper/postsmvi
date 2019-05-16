package com.example.domain

import com.example.domain.model.AllPostsViewState
import com.example.domain.model.ViewState
import com.example.domain.posts.PostRepository
import com.example.domain.posts.GetAllPostsUseCase
import com.example.domain.posts.GetAllPostsUseCaseImpl
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.net.SocketException

@RunWith(MockitoJUnitRunner::class)
class GetAllPostsUseCaseTest : BaseTest() {

    private val postRepository: PostRepository = mock()

    private lateinit var SUT: GetAllPostsUseCase

    @Before
    override fun setup() {
        super.setup()
        SUT = GetAllPostsUseCaseImpl(postRepository)
    }

    @Test
    fun `attempts to retrieve the list of all posts`() {
        given(postRepository.getAllPosts()).willReturn(mock())

        SUT.execute().test()

        verify(postRepository).getAllPosts()
    }

    @Test
    fun `shows loading at the start`() {
        given(postRepository.getAllPosts()).willReturn(mock())

        SUT.execute().test().assertValueAt(0) { it == ViewState.Loading }
    }

    @Test
    fun `returns success when list of posts are successfully retrieved`() {
        val result = listOf(getPost())

        given(postRepository.getAllPosts()).willReturn(Observable.just(result))

        SUT.execute().test().assertValueAt(1) { (it as AllPostsViewState.Success).domainPosts == result }
    }

    @Test
    fun `returns empty when retrieved list has zero items`() {
        given(postRepository.getAllPosts()).willReturn(Observable.just(emptyList()))

        SUT.execute().test().assertValueAt(1) { it == ViewState.Empty }
    }

    @Test
    fun `returns failure if retrieval fails due to error`() {
        val message = "Operation Timed Out"
        given(postRepository.getAllPosts()).willReturn(Observable.error(SocketException(message)))

        SUT.execute().test().assertValueAt(1) { (it as ViewState.Failure).message == message }
    }
}