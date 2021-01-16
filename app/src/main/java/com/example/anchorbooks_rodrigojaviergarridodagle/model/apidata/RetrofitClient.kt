package com.example.ensayoantesdelapruebadelsabado.model.apidata

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        private val BASE_URL: String = "https://my-json-server.typicode.com/Himuravidal/anchorBooks/"
        private var retrofit: Retrofit? = null

        fun getRetrofitClient(): Retrofit {
            if (retrofit == null) {
                synchronized(this) {
                    retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                }
            }
            return retrofit!!
        }
    }
}