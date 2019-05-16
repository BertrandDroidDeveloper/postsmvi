package com.example.data.repository

import com.example.data.BaseTest
import com.example.data.cache.LocalDataSource
import com.example.data.api.RemoteDataSource
import com.example.data.models.DataComment
import com.example.data.mapper.Mapper
import com.example.domain.comments.DomainComment
import com.example.domain.comments.CommentRepository
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class DomainCommentRepositoryTest : BaseTest() {

    private val remoteDataSource: RemoteDataSource = mock()
    private val commentsCache: LocalDataSource<DomainComment> = mock()
    private val domainCommentMapper: Mapper<DataComment, DomainComment> = mock()

    private lateinit var commentRepository: CommentRepository

    @Before
    fun setup() {
        commentRepository = CommentRepositoryImpl(remoteDataSource, commentsCache, domainCommentMapper)
    }

    @Test
    fun `attempts to get list of comments and updates cache when cache is empty`() {
        val commentResponseList = getCommentResponseList()
        given(remoteDataSource.getAllComments()).willReturn(Single.just(commentResponseList))
        val commentList = getCommentList()
        given(domainCommentMapper.mapList(commentResponseList)).willReturn(commentList)

        commentRepository.getAllComments().test()

        verify(commentsCache).getSize()
        verify(remoteDataSource).getAllComments()
        verify(commentsCache).addAll(commentList)
    }

    @Test
    fun `attempts to get list of comments and returns from cache`() {
        val commentList = getCommentList()
        given(commentsCache.getItems()).willReturn(commentList)
        given(commentsCache.getSize()).willReturn(commentList.size)

        commentRepository.getAllComments().test()

        verify(commentsCache).getSize()
        verify(commentsCache).getItems()
        verifyZeroInteractions(remoteDataSource, domainCommentMapper)
    }

    @Test
    fun `getAllComments returns mapped list`() {
        val commentResponseList = getCommentResponseList()
        given(remoteDataSource.getAllComments()).willReturn(Single.just(commentResponseList))
        val commentList = getCommentList()
        given(domainCommentMapper.mapList(commentResponseList)).willReturn(commentList)

        commentRepository.getAllComments().test().assertValue(commentList)
    }

    @Test
    fun `getAllComments returns error when retrieval fails`() {
        val exception = IOException()
        given(remoteDataSource.getAllComments()).willReturn(Single.error(exception))

        commentRepository.getAllComments().test().assertError(exception)

        verify(commentsCache).getSize()
        verifyZeroInteractions(domainCommentMapper)
        verifyNoMoreInteractions(commentsCache)
    }
}