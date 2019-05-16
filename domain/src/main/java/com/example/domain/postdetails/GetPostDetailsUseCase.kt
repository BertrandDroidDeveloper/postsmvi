package com.example.domain.postdetails

import com.example.domain.posts.DomainPost
import com.example.domain.model.ViewState
import io.reactivex.Observable

interface GetPostDetailsUseCase {
    fun execute(domainPost: DomainPost): Observable<ViewState>
}