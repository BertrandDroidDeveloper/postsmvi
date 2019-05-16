package com.example.babylondemo.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiPost(val id: Int, val postId: Int, val title: String, val body: String) : Parcelable
