package com.example.ensayoantesdelapruebadelsabado.services

import com.example.anchorbooks_rodrigojaviergarridodagle.model.entities.BookDetail

interface ListenersBookListing {
    fun viewTouchedShort(
        producto: BookDetail,
        position: Int
    )

    fun viewTouchedLong(
        producto: BookDetail,
        id: Int
    )
}
