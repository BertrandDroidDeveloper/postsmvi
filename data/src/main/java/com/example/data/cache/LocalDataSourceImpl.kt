package com.example.data.cache

open class LocalDataSourceImpl<T> : LocalDataSource<T> {

    private val items = mutableListOf<T>()

    override fun getItems(): List<T> = items

    override fun addItem(item: T) {
        items.add(item)
    }

    override fun getSize() = items.size

    override fun addAll(items: List<T>) {
        this.items.addAll(items)
    }
}

