package com.example.anchorbooks_rodrigojaviergarridodagle.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "books_table")
data class BookDetail(

    @SerializedName("id") @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @SerializedName("author") val author: String = "",
    @SerializedName("country") val country: String = "",
    @SerializedName("imageLink") val imageLink: String = "",
    @SerializedName("language") val language: String = "",
    @SerializedName("link") val link: String = "",
    @SerializedName("pages") val pages: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("year") val year: Int = 0,
    @SerializedName("price") val price: Int = 0,
    @SerializedName("lastPrice") val lastPrice: Int = 0,
    @SerializedName("delivery") val delivery: Boolean = false
)