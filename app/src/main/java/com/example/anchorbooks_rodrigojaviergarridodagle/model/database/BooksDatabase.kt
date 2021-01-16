package com.rodrigo.javier.eox.hackermen.proyectorepeticionpruebamock.model_.sqlitedatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.anchorbooks_rodrigojaviergarridodagle.model.entities.BookDetail


@Database(entities = [BookDetail::class], version = 2, exportSchema = false)
abstract class BooksDatabase : RoomDatabase() {

    abstract fun getDao(): BookDao

    companion object {

        private var database: BooksDatabase? = null

        fun getDatabase(contex: Context): BooksDatabase {
            if (database == null) {
                synchronized(this) {
                    database = Room.databaseBuilder(
                        contex,
                        BooksDatabase::class.java,
                        "books_database"
                    )
//                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return database!!
        }
    }
}
