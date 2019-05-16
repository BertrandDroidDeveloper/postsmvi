package com.example.domain.postdetails

import com.example.domain.comments.CommentRepository
import com.example.domain.comments.DomainComment
import com.example.domain.model.PostDetailViewState
import com.example.domain.model.ViewState
import com.example.domain.posts.DomainPost
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetPostDetailsUseCaseImpl @Inject constructor(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository
) : GetPostDetailsUseCase {

    override fun execute(domainPost: DomainPost): Observable<ViewState> =
        Observable.zip(commentRepository.getAllComments(),
            userRepository.getAllUsers(),
            BiFunction { domainComments: List<DomainComment>, domainUsers: List<DomainUser> ->
                generatePostDetail(
                    domainPost,
                    domainComments,
                    domainUsers
                )
            })
            .subscribeOn(Schedulers.io())
            .flatMap { Observable.just(PostDetailViewState.Success(it) as ViewState) }
            .onErrorReturn { ViewState.Failure(it.localizedMessage ?: "Unknown Message") }
            .startWith(ViewState.Loading)

    private fun generatePostDetail(
        domainPost: DomainPost,
        domainComments: List<DomainComment>,
        domainUsers: List<DomainUser>
    ) =
        DomainPostDetails(
            title = domainPost.title,
            body = domainPost.body,
            author = domainUsers.firstOrNull { it.id == domainPost.userId }?.username ?: "",
            totalComments = domainComments.filter { it.postId == domainPost.postId }.size.toString()
        )

}