package com.example.ensayoantesdelapruebadelsabado.model.apidata

import com.example.anchorbooks_rodrigojaviergarridodagle.model.entities.BookDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiRetrofit {
    @GET("books")
    fun getAllBooks(): Call<MutableList<BookDetail>>

    @GET("bookDetail/{id}")
    fun getBookDetail(
        @Path("id")
        id: Int
    ): Call<BookDetail>

}