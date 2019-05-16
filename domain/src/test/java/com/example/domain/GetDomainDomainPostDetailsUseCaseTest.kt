package com.example.domain

import com.example.domain.comments.DomainComment
import com.example.domain.model.PostDetailViewState
import com.example.domain.postdetails.DomainUser
import com.example.domain.model.ViewState
import com.example.domain.comments.CommentRepository
import com.example.domain.postdetails.UserRepository
import com.example.domain.postdetails.GetPostDetailsUseCase
import com.example.domain.postdetails.GetPostDetailsUseCaseImpl
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetDomainDomainPostDetailsUseCaseTest : BaseTest() {

    private val commentRepository: CommentRepository = mock()
    private val userRepository: UserRepository = mock()

    private val testPost = getPost()

    private lateinit var SUT: GetPostDetailsUseCase

    @Before
    override fun setup() {
        super.setup()
        SUT = GetPostDetailsUseCaseImpl(commentRepository, userRepository)
    }

    @Test
    fun `attempts to retrieve both list of users and comments`() {
        given(userRepository.getAllUsers()).willReturn(mock())
        given(commentRepository.getAllComments()).willReturn(mock())

        SUT.execute(testPost)

        verify(userRepository).getAllUsers()
        verify(commentRepository).getAllComments()
    }

    @Test
    fun `shows loading at the start`() {
        given(userRepository.getAllUsers()).willReturn(mock())
        given(commentRepository.getAllComments()).willReturn(mock())

        SUT.execute(testPost).test().assertValueAt(0) { it == ViewState.Loading }
    }

    @Test
    fun `returns success when data is successfully retrieved`() {
        val users = listOf(DomainUser("Hello", 1, "World"))
        val comments = listOf(DomainComment(1, 1, "Hello", "World"))

        given(userRepository.getAllUsers()).willReturn(Observable.just(users))
        given(commentRepository.getAllComments()).willReturn(Observable.just(comments))

        SUT.execute(testPost).test().assertValueAt(1)
        { (it as PostDetailViewState.Success).domainPostDetails.totalComments == "1" }

        SUT.execute(testPost).test().assertValueAt(1)
        { (it as PostDetailViewState.Success).domainPostDetails.title == "Hello" }

    }

    @Test
    fun `returns failure if execution fails due to error`() {
        val message = this.javaClass.simpleName
        given(userRepository.getAllUsers()).willReturn(mock())
        given(commentRepository.getAllComments()).willReturn(Observable.error(Throwable(message)))

        SUT.execute(testPost).test().assertValueAt(1)
        { (it as ViewState.Failure).message == message }
    }
}