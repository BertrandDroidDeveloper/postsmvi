package com.example.data

import com.example.data.models.*
import com.example.domain.comments.DomainComment
import com.example.domain.posts.DomainPost
import com.example.domain.postdetails.DomainUser

open class BaseTest {

    open fun getPostList() = listOf(DomainPost(0, 0, "Hello", "World"))

    open fun getUserList() = listOf(DomainUser("Hello", 1, "World"))

    open fun getCommentList() = listOf(DomainComment(1, 1, "Hello", "World"))

    open fun getPostResponseList() = listOf(DataPost(0, 0, "Hello", "World"))

    open fun getUserResponseList(): List<DataUser> {
        val company = DataCompany("Hello", "World", "Again")
        val geo = DataGeo("10", "10")
        val address = DataAddress("London", geo, "Bond Street", "Kensington Palace", "ET2 XCF")
        val userResponse =
            DataUser(address, company, "spam@somedomain.com", 0, "Sam", "555", "Sam J", "https://www.example.com")
        return listOf(userResponse)
    }

    open fun getCommentResponseList() = listOf(DataComment("Hello World", "spam@somedomain.com", 0, "Sam", 0))

}