package com.example.domain.model

import com.example.domain.posts.DomainPost
import com.example.domain.postdetails.DomainPostDetails

sealed class ViewState {
    object Loading : ViewState()
    object Empty : ViewState()
    data class Failure(val message: String) : ViewState()
}

sealed class AllPostsViewState : ViewState() {
    data class Success(val domainPosts: List<DomainPost>) : AllPostsViewState()
}

sealed class PostDetailViewState : ViewState() {
    data class Success(val domainPostDetails: DomainPostDetails) : PostDetailViewState()
}