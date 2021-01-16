package com.example.ensayoantesdelapruebadelsabado.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.anchorbooks_rodrigojaviergarridodagle.model.entities.BookDetail
import com.example.anchorbooks_rodrigojaviergarridodagle.ui.activities.MainActivity
import com.example.ensayoantesdelapruebadelsabado.model.apidata.RetrofitManager
import com.rodrigo.javier.eox.hackermen.proyectorepeticionpruebamock.model_.sqlitedatabase.BookDao
import com.rodrigo.javier.eox.hackermen.proyectorepeticionpruebamock.model_.sqlitedatabase.BooksDatabase
import com.rodrigo.javier.eox.hackermen.proyectorepeticionpruebamock.model_.sqlitedatabase.DatabaseManager
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BookListingViewModel(application: Application) : AndroidViewModel(application) {
    var list: MutableLiveData<MutableList<BookDetail>> =
        MutableLiveData<MutableList<BookDetail>>(mutableListOf())
    var detail: MutableLiveData<BookDetail> = MutableLiveData<BookDetail>()

    var dao: BookDao


    init {
        var instance = MainActivity()

        if (list.value.isNullOrEmpty()) {
            getListaProductosUsingViewModelToRetorfitManager()
        }
        val database = BooksDatabase.getDatabase(getApplication())
        dao = database.getDao()

    }

    private fun getListaProductosUsingViewModelToRetorfitManager() {
        RetrofitManager().getBookListWithRetrofit(object :
            Callback<MutableList<BookDetail>> {
            override fun onResponse(
                call: Call<MutableList<BookDetail>>,
                response: Response<MutableList<BookDetail>>
            ) {
                list!!.value = response.body()
                viewModelScope.launch { list }

            }

            override fun onFailure(call: Call<MutableList<BookDetail>>, t: Throwable) {
                Log.d(
                    "--FAILURE LIST->",
                    "No hay internet---------->"
                )

                Toast.makeText(
                    getApplication(),
                    "NO hay internet!",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun getBookDetailUsingViewModelToRetrofitManager(id: Int) {
        RetrofitManager().getBookDetailWithRetrofit(object : Callback<BookDetail> {
            override fun onResponse(call: Call<BookDetail>, response: Response<BookDetail>) {
                detail!!.value = response!!.body()!!
                viewModelScope.launch { detail }
            }

            override fun onFailure(call: Call<BookDetail>, t: Throwable) {
                Log.d(
                    "--FAILURE DETAIL",
                    "---------------------------->"
                )
            }

        }, id)
    }

    suspend fun insertInDatabaseUsingViewModel(bookDetail: BookDetail) {
        DatabaseManager(dao).insertDetailInDDBB(bookDetail)
    }

}
