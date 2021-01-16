package com.example.ensayoantesdelapruebadelsabado.services

import com.example.anchorbooks_rodrigojaviergarridodagle.model.entities.BookDetail


interface ListenerBookDetail {
     fun viewTouchedDetailShort(
        book: BookDetail,
        position: Int
    )

    fun viewTouchedDetailLong(
        book: BookDetail,
        id: Int
    )
}