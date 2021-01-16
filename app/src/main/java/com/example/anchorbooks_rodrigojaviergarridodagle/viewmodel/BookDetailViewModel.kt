package com.example.ensayoantesdelapruebadelsabado.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.anchorbooks_rodrigojaviergarridodagle.model.entities.BookDetail
import com.example.ensayoantesdelapruebadelsabado.services.ListenerBookDetail
import com.rodrigo.javier.eox.hackermen.proyectorepeticionpruebamock.model_.sqlitedatabase.BookDao
import com.rodrigo.javier.eox.hackermen.proyectorepeticionpruebamock.model_.sqlitedatabase.BooksDatabase
import com.rodrigo.javier.eox.hackermen.proyectorepeticionpruebamock.model_.sqlitedatabase.DatabaseManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookDetailViewModel(application: Application) : AndroidViewModel(application) {
    var listaDetalles: LiveData<MutableList<BookDetail>> =
        MutableLiveData<MutableList<BookDetail>>(mutableListOf())
    var dao: BookDao
    val databaseManager: DatabaseManager

    init {
        dao = BooksDatabase.getDatabase(getApplication()).getDao()

        databaseManager = DatabaseManager(dao)
        listaDetalles = databaseManager.allProducts
    }

    suspend fun deleteWithViewModel(book: BookDetail) {
        dao.deleteItem(book)
    }
}
