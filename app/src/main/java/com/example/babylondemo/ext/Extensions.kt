package com.example.babylondemo.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.example.babylondemo.presentation.model.UiPost
import com.example.domain.posts.DomainPost

fun ViewGroup.inflate(@LayoutRes layoutId: Int): View =
    LayoutInflater.from(context).inflate(layoutId, this, false)

fun List<DomainPost>.toUiList() = orEmpty().map { it.toUi() }

fun DomainPost.toUi() = UiPost(id = userId, postId = postId, title = title, body = body)

fun UiPost.toDomain() = DomainPost(userId = id, postId = postId, title = title, body = body)
