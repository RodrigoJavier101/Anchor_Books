package com.rodrigo.javier.eox.hackermen.proyectorepeticionpruebamock.model_.sqlitedatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.anchorbooks_rodrigojaviergarridodagle.model.entities.BookDetail

@Dao
interface BookDao {

    @Query("SELECT * FROM books_table")
    fun getProductsList(): LiveData<MutableList<BookDetail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetail(book: BookDetail)

    @Delete
    suspend fun deleteItem(book: BookDetail)
}
