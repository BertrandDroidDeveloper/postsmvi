package com.example.babylondemo.presentation.adapter

import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.babylondemo.R
import com.example.babylondemo.ext.inflate
import com.example.domain.posts.DomainPost
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_post.*

class PostsAdapter(private val onPostSelectedListener: (DomainPost) -> Unit) :
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    private val posts = mutableListOf<DomainPost>()

    fun setData(data: List<DomainPost>) {
        posts.clear()
        posts.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PostViewHolder(parent.inflate(R.layout.item_post))

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position], onPostSelectedListener)
    }

    class PostViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            TransitionManager.beginDelayedTransition(containerView as ViewGroup)
        }

        fun bind(domainPost: DomainPost, onPostSelectedListener: (DomainPost) -> Unit) {
            tvTitle.text = domainPost.title
            "https://lorempixel.com/800/200/nature/" + (0..10).random() + "/".let { url ->
                Picasso.get().load(url).into(ivBanner)
            }
            containerView.setOnClickListener { onPostSelectedListener(domainPost) }
        }
    }
}