package com.example.ensayoantesdelapruebadelsabado.model.apidata

import com.example.anchorbooks_rodrigojaviergarridodagle.model.entities.BookDetail
import retrofit2.Call
import retrofit2.Callback


class RetrofitManager {
    private var listenerRetrofit: ApiRetrofit

    init {
        listenerRetrofit = RetrofitClient.getRetrofitClient().create(ApiRetrofit::class.java)
    }

    fun getBookListWithRetrofit(callback: Callback<MutableList<BookDetail>>) {
        val call: Call<MutableList<BookDetail>> = listenerRetrofit.getAllBooks()

        call.enqueue(callback)
    }

    fun getBookDetailWithRetrofit(
        callback: Callback<BookDetail>,
        id
        : Int
    ) {
        val call: Call<BookDetail> = listenerRetrofit.getBookDetail(id)
        call.enqueue(callback)
    }


}