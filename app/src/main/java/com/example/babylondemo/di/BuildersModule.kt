package com.example.babylondemo.di

import com.example.babylondemo.di.scope.PostDetailScope
import com.example.babylondemo.di.scope.PostsScope
import com.example.babylondemo.presentation.postdetails.PostDetailsActivity
import com.example.babylondemo.presentation.posts.PostsActivity
import com.example.data.api.*
import com.example.data.cache.CommentLocalDataSource
import com.example.data.cache.LocalDataSource
import com.example.data.cache.PostLocalDataSource
import com.example.data.cache.UsersLocalDataSource
import com.example.data.models.DataComment
import com.example.data.models.DataPost
import com.example.data.models.DataUser
import com.example.data.mapper.CommentMapper
import com.example.data.mapper.Mapper
import com.example.data.mapper.PostMapper
import com.example.data.mapper.UserMapper
import com.example.domain.comments.DomainComment
import com.example.domain.posts.DomainPost
import com.example.domain.postdetails.DomainUser
import com.example.domain.posts.GetAllPostsUseCase
import com.example.domain.posts.GetAllPostsUseCaseImpl
import com.example.domain.postdetails.GetPostDetailsUseCase
import com.example.domain.postdetails.GetPostDetailsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    @PostsScope
    abstract fun postsActivity(): PostsActivity

    @ContributesAndroidInjector
    @PostDetailScope
    abstract fun postDetailsActivity(): PostDetailsActivity

    @Binds
    abstract fun bindGetAllPostsUseCase(getAllPostsUseCaseImpl: GetAllPostsUseCaseImpl): GetAllPostsUseCase

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    abstract fun bindGetPostDetailsUseCase(getPostDetailsUseCaseImpl: GetPostDetailsUseCaseImpl): GetPostDetailsUseCase

    @Binds
    abstract fun bindUsersCache(usersLocalDataSource: UsersLocalDataSource): LocalDataSource<DomainUser>

    @Binds
    abstract fun bindPostsCache(postsCache: PostLocalDataSource): LocalDataSource<DomainPost>

    @Binds
    abstract fun bindCommentsCache(commentLocalDataSource: CommentLocalDataSource): LocalDataSource<DomainComment>

    @Binds
    abstract fun bindCommentMapper(commentMapper: CommentMapper): Mapper<DataComment, DomainComment>

    @Binds
    abstract fun bindPostMapper(postMapper: PostMapper): Mapper<DataPost, DomainPost>

    @Binds
    abstract fun bindUserMapper(userMapper: UserMapper): Mapper<DataUser, DomainUser>
}