package com.example.data.repository

import com.example.data.api.RemoteDataSource
import com.example.data.cache.LocalDataSource
import com.example.data.mapper.Mapper
import com.example.data.models.DataPost
import com.example.domain.posts.DomainPost
import com.example.domain.posts.PostRepository
import io.reactivex.Observable

class PostRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource<DomainPost>,
    private val domainPostMapper: Mapper<DataPost, DomainPost>
) : PostRepository {

    override fun getAllPosts(): Observable<List<DomainPost>> =
        if (localDataSource.getSize() > 0) Observable.just(localDataSource.getItems()) else remoteDataSource.getAllPosts()
            .map { domainPostMapper.mapList(it) }
            .toObservable()
            .doOnNext { localDataSource.addAll(it) }

}