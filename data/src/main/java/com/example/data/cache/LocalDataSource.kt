package com.example.data.cache

interface LocalDataSource<T> {
    fun getItems(): List<T>
    fun addItem(item: T)
    fun getSize(): Int
    fun addAll(items: List<T>)
}