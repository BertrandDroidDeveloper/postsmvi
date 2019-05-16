package com.example.data.mapper

import com.example.data.models.DataUser
import com.example.domain.postdetails.DomainUser
import javax.inject.Inject

class UserMapper @Inject constructor() : Mapper<DataUser, DomainUser> {

    override fun map(item: DataUser) = DomainUser(
        username = item.name,
        id = item.id,
        companyName = item.company.name
    )
}