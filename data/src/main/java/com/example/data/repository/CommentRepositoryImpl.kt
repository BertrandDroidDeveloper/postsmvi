package com.example.data.repository

import com.example.data.api.RemoteDataSource
import com.example.data.cache.LocalDataSource
import com.example.data.mapper.Mapper
import com.example.data.models.DataComment
import com.example.domain.comments.CommentRepository
import com.example.domain.comments.DomainComment
import io.reactivex.Observable

class CommentRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource<DomainComment>,
    private val domainCommentMapper: Mapper<DataComment, DomainComment>
) :
    CommentRepository {
    override fun getAllComments(): Observable<List<DomainComment>> =
        if (localDataSource.getSize() > 0) Observable.just(localDataSource.getItems()) else remoteDataSource.getAllComments()
            .map { domainCommentMapper.mapList(it) }
            .toObservable()
            .doOnNext { localDataSource.addAll(it) }
}
