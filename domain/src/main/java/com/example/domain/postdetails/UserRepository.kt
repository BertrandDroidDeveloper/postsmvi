package com.example.domain.postdetails

import io.reactivex.Observable

interface UserRepository {
    fun getAllUsers(): Observable<List<DomainUser>>
}