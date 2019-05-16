package com.example.data.repository

import com.example.data.api.RemoteDataSource
import com.example.data.cache.LocalDataSource
import com.example.data.mapper.Mapper
import com.example.data.models.DataUser
import com.example.domain.postdetails.DomainUser
import com.example.domain.postdetails.UserRepository
import io.reactivex.Observable

class UserRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource<DomainUser>,
    private val dataDomainUserMapper: Mapper<DataUser, DomainUser>
) : UserRepository {

    override fun getAllUsers(): Observable<List<DomainUser>> =
        if (localDataSource.getSize() > 0) Observable.just(localDataSource.getItems()) else
            remoteDataSource.getAllUsers()
                .map { dataDomainUserMapper.mapList(it) }
                .toObservable()
                .doOnNext { localDataSource.addAll(it) }

}