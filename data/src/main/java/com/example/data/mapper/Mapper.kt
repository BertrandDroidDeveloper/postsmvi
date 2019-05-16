package com.example.data.mapper

interface Mapper<T, R> {
    fun mapList(items: List<T>): List<R> = items.map { map(it) }
    fun map(item: T): R
}