package com.example.data.repository

import com.example.data.BaseTest
import com.example.data.cache.LocalDataSource
import com.example.data.api.RemoteDataSource
import com.example.data.models.DataUser
import com.example.data.mapper.Mapper
import com.example.domain.postdetails.DomainUser
import com.example.domain.postdetails.UserRepository
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class DomainUserRepositoryTest : BaseTest() {

    private val remoteDataSource: RemoteDataSource = mock()
    private val usersCache: LocalDataSource<DomainUser> = mock()
    private val dataDomainUserMapper: Mapper<DataUser, DomainUser> = mock()

    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        userRepository = UserRepositoryImpl(remoteDataSource, usersCache, dataDomainUserMapper)
    }

    @Test
    fun `attempts to get list of users and updates cache when cache is empty`() {
        val userResponseList = getUserResponseList()
        given(remoteDataSource.getAllUsers()).willReturn(Single.just(userResponseList))
        val userList = getUserList()
        given(dataDomainUserMapper.mapList(userResponseList)).willReturn(userList)

        userRepository.getAllUsers().test()

        verify(usersCache).getSize()
        verify(remoteDataSource).getAllUsers()
        verify(usersCache).addAll(userList)
    }

    @Test
    fun `attempts to get list of users and returns from cache`() {
        val userList = getUserList()
        given(usersCache.getItems()).willReturn(userList)
        given(usersCache.getSize()).willReturn(userList.size)

        userRepository.getAllUsers().test()

        verify(usersCache).getSize()
        verify(usersCache).getItems()
        verifyZeroInteractions(remoteDataSource, dataDomainUserMapper)
    }

    @Test
    fun `getAllUsers returns mapped list`() {
        val userResponseList = getUserResponseList()
        given(remoteDataSource.getAllUsers()).willReturn(Single.just(userResponseList))
        val userList = getUserList()
        given(dataDomainUserMapper.mapList(userResponseList)).willReturn(userList)

        userRepository.getAllUsers().test().assertValue(userList)
    }

    @Test
    fun `getAllUsers returns error when retrieval fails`() {
        val exception = IOException()
        given(remoteDataSource.getAllUsers()).willReturn(Single.error(exception))

        userRepository.getAllUsers().test().assertError(exception)

        verify(usersCache).getSize()
        verifyZeroInteractions(dataDomainUserMapper)
        verifyNoMoreInteractions(usersCache)
    }
}