package com.example.data.mapper

import com.example.data.BaseTest
import com.example.data.models.DataPost
import com.example.domain.posts.DomainPost
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DomainPostMapperTest : BaseTest() {
    private lateinit var domainPostMapper: Mapper<DataPost, DomainPost>

    @Before
    fun setup() {
        domainPostMapper = PostMapper()
    }

    @Test
    fun `post response gets mapped to post`() {
        val postResponseList = getPostResponseList()
        val postsList = domainPostMapper.mapList(postResponseList)
        val postResponse = postResponseList[0]
        val post = postsList[0]

        assertEquals(postResponse.postId, post.postId)
        assertEquals(postResponse.userId, post.userId)
        assertEquals(postResponse.title, post.title)
        assertEquals(postResponse.body, post.body)
    }
}