package com.example.data.repository

import com.example.data.BaseTest
import com.example.data.cache.LocalDataSource
import com.example.data.api.RemoteDataSource
import com.example.data.models.DataPost
import com.example.data.mapper.Mapper
import com.example.domain.posts.DomainPost
import com.example.domain.posts.PostRepository
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class DomainPostRepositoryTest : BaseTest() {

    private val remoteDataSource: RemoteDataSource = mock()
    private val postsCache: LocalDataSource<DomainPost> = mock()
    private val domainPostMapper: Mapper<DataPost, DomainPost> = mock()

    private lateinit var postRepository: PostRepository

    @Before
    fun setup() {
        postRepository = PostRepositoryImpl(remoteDataSource, postsCache, domainPostMapper)
    }

    @Test
    fun `attempts to get list of posts and updates cache when cache is empty`() {
        val postResponseList = getPostResponseList()
        given(remoteDataSource.getAllPosts()).willReturn(Single.just(postResponseList))
        val postsList = getPostList()
        given(domainPostMapper.mapList(postResponseList)).willReturn(postsList)

        postRepository.getAllPosts().test()

        verify(postsCache).getSize()
        verify(remoteDataSource).getAllPosts()
        verify(postsCache).addAll(postsList)
    }

    @Test
    fun `attempts to get list of posts and returns from cache`() {
        val postsList = getPostList()
        given(postsCache.getSize()).willReturn(postsList.size)
        given(postsCache.getItems()).willReturn(postsList)

        postRepository.getAllPosts().test()

        verify(postsCache).getSize()
        verify(postsCache).getItems()
        verifyZeroInteractions(remoteDataSource, domainPostMapper)
    }

    @Test
    fun `getAllPosts returns mapped list`() {
        val postResponseList = getPostResponseList()
        given(remoteDataSource.getAllPosts()).willReturn(Single.just(postResponseList))
        val postsList = getPostList()
        given(domainPostMapper.mapList(postResponseList)).willReturn(postsList)

        postRepository.getAllPosts().test().assertValue(postsList)
    }

    @Test
    fun `getAllPosts returns error when retrieval fails`() {
        val exception = IOException()
        given(remoteDataSource.getAllPosts()).willReturn(Single.error(exception))

        postRepository.getAllPosts().test().assertError(exception)

        verify(postsCache).getSize()
        verifyZeroInteractions(domainPostMapper)
        verifyNoMoreInteractions(postsCache)
    }
}