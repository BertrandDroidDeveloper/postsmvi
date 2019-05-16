package com.example.domain

import com.example.domain.posts.DomainPost
import io.reactivex.Scheduler
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

open class BaseTest {
    private val scheduler = object : Scheduler() {

        override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit) =
            super.scheduleDirect(run, 0, unit)

        override fun createWorker() =
            ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
    }

    @Before
    open fun setup() {
        RxJavaPlugins.setInitIoSchedulerHandler { scheduler }
    }

    fun getPost() = DomainPost(0, 1, "Hello", "World")
}