package com.example.anchorbooks_rodrigojaviergarridodagle

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.anchorbooks_rodrigojaviergarridodagle.model.entities.BookDetail
import com.rodrigo.javier.eox.hackermen.proyectorepeticionpruebamock.model_.sqlitedatabase.BookDao
import com.rodrigo.javier.eox.hackermen.proyectorepeticionpruebamock.model_.sqlitedatabase.BooksDatabase
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import retrofit2.Call
import retrofit2.Callback


class UnitTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: BookDao
    private lateinit var database: BooksDatabase

    @Test
    suspend fun deleteWithViewModel(book: BookDetail) {
        // Given
        val book = BookDetail(1, "Unknown")

        // When
        val response = deleteWithViewModel(book)

        // Then
        assert("Unknown" == book.author)
    }

}