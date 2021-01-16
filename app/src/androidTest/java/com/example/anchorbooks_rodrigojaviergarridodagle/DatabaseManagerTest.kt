package com.example.anchorbooks_rodrigojaviergarridodagle

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rodrigo.javier.eox.hackermen.proyectorepeticionpruebamock.model_.sqlitedatabase.BookDao
import com.rodrigo.javier.eox.hackermen.proyectorepeticionpruebamock.model_.sqlitedatabase.BooksDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import com.example.anchorbooks_rodrigojaviergarridodagle.model.entities.BookDetail as BookDetail

class DatabaseManagerTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: BookDao
    private lateinit var database: BooksDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, BooksDatabase::class.java).build()
        dao = database.getDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    suspend fun insertDetailInDDBB(book: BookDetail) = runBlocking {
        // Given
//        val books = BookDetail

        // When
        dao.insertDetail(book)

        // Then
        dao.getProductsList().observeForever {
            assertThat(it).isNotNull()
            assertThat(it).isEmpty()
        }
    }

}