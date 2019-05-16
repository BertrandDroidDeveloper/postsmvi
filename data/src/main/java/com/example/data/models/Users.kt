package com.example.data.models

data class DataUser(
    val address: DataAddress,
    val company: DataCompany,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)

data class DataCompany(
    val bs: String,
    val catchPhrase: String,
    val name: String
)

data class DataAddress(
    val city: String,
    val geo: DataGeo,
    val street: String,
    val suite: String,
    val zipcode: String
)

data class DataGeo(
    val lat: String,
    val lng: String
)