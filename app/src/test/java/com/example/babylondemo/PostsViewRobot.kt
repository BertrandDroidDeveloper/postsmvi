package com.example.babylondemo

import com.example.babylondemo.presentation.posts.PostsContract
import com.example.babylondemo.presentation.posts.PostsPresenter
import com.example.domain.model.ViewState
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.TimeUnit


class PostsViewRobot(postsPresenter: PostsPresenter) {

    private val loadAllPostsSubject: PublishSubject<Boolean> = PublishSubject.create()
    private val renderEvents: MutableList<ViewState> = CopyOnWriteArrayList()
    private val renderEventSubject: ReplaySubject<ViewState> = ReplaySubject.create()

    private val view = object : PostsContract.View {
        override fun loadAllPostsIntent(): Observable<Boolean> = loadAllPostsSubject

        override fun render(viewState: ViewState) {
            renderEvents.add(viewState)
            renderEventSubject.onNext(viewState)
        }
    }

    init {
        postsPresenter.attachView(view)
    }

    fun fireLoadAllPostsIntent() {
        loadAllPostsSubject.onNext(true)
    }

    fun assertViewStateRendered(vararg viewStates: ViewState) {
        val eventsCount = viewStates.size.toLong()

        renderEventSubject.take(eventsCount)
            .timeout(10, TimeUnit.SECONDS)
            .blockingSubscribe()

        if (renderEventSubject.values.size > eventsCount) {
            fail(
                "Expected to wait for " + eventsCount + ", but there were " + renderEventSubject.values.size
                        + " Events in total, which is more than expected"
            )
        }

        assertEquals(viewStates.toList(), renderEvents)
    }
}