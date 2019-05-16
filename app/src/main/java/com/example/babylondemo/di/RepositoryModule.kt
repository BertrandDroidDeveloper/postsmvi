package com.example.babylondemo.di

import com.example.data.api.RemoteDataSource
import com.example.data.cache.LocalDataSource
import com.example.data.mapper.Mapper
import com.example.data.models.DataComment
import com.example.data.models.DataPost
import com.example.data.models.DataUser
import com.example.data.repository.CommentRepositoryImpl
import com.example.data.repository.PostRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.comments.CommentRepository
import com.example.domain.comments.DomainComment
import com.example.domain.postdetails.DomainUser
import com.example.domain.postdetails.UserRepository
import com.example.domain.posts.DomainPost
import com.example.domain.posts.PostRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providePostRepository(
        remoteDataSource: RemoteDataSource,
        postsCache: LocalDataSource<DomainPost>,
        domainPostMapper: Mapper<DataPost, DomainPost>
    ): PostRepository =
        PostRepositoryImpl(remoteDataSource, postsCache, domainPostMapper)

    @Provides
    @Singleton
    fun provideUserRepository(
        remoteDataSource: RemoteDataSource,
        usersCache: LocalDataSource<DomainUser>,
        dataDomainUserMapper: Mapper<DataUser, DomainUser>
    ): UserRepository =
        UserRepositoryImpl(remoteDataSource, usersCache, dataDomainUserMapper)

    @Provides
    @Singleton
    fun provideCommentRepository(
        remoteDataSource: RemoteDataSource,
        commentsCache: LocalDataSource<DomainComment>,
        domainCommentMapper: Mapper<DataComment, DomainComment>
    ): CommentRepository =
        CommentRepositoryImpl(remoteDataSource, commentsCache, domainCommentMapper)
}