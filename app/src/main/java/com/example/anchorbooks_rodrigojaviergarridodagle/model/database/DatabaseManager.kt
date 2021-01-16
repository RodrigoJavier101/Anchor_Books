package com.rodrigo.javier.eox.hackermen.proyectorepeticionpruebamock.model_.sqlitedatabase

import androidx.lifecycle.LiveData
import com.example.anchorbooks_rodrigojaviergarridodagle.model.entities.BookDetail


class DatabaseManager(private val dao: BookDao) {
    val allProducts: LiveData<MutableList<BookDetail>>

    init {
        allProducts = dao.getProductsList()
    }

    suspend fun deleteItemWithDatabaseManager(book: BookDetail) {
        dao.deleteItem(book)
    }

    suspend fun insertDetailInDDBB(book: BookDetail) {
        dao.insertDetail(
            book
        )
    }
}
