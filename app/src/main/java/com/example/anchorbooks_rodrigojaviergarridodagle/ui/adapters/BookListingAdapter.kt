package com.example.ensayoantesdelapruebadelsabado.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.anchorbooks_rodrigojaviergarridodagle.databinding.ItemMainListBinding
import com.example.anchorbooks_rodrigojaviergarridodagle.model.entities.BookDetail
import com.example.ensayoantesdelapruebadelsabado.services.ListenersBookListing
import com.squareup.picasso.Picasso

class BookListingAdapter(
    private var lista: MutableList<BookDetail> = mutableListOf(), private val context: Context,
    private var listenerImgViewShort: ListenersBookListing,
    private var listenerImgViewLong: ListenersBookListing,
) : RecyclerView.Adapter<BookListingAdapter.BaseViewHolder<*>>() {

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    inner class MyOwnViewHolder(val binding: ItemMainListBinding) :
        BaseViewHolder<BookDetail>(binding.root) {

        override fun bind(item: BookDetail) = with(binding) {
            Picasso.get().load(item.imageLink)
                .into(imageViewImagelink)

            if (item.author.toString() == "Unknown") {
                textViewAuthor.text = "Autor desconocido"
            } else {
                textViewAuthor.text = item.author.toString()
            }

            textViewTitle.text = item.title.toString()
            textViewLanguage.text = "Idioma: ${item.language.toString()}"
            textViewCountry.text = item.country.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        var binding = ItemMainListBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        val holder = MyOwnViewHolder(binding)

        binding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != NO_POSITION }
                ?: return@setOnClickListener
            YoYo.with(Techniques.Pulse)
                .duration(450)
                .playOn(binding.carviewItem)

            this.listenerImgViewShort.viewTouchedShort(lista[position], position)
        }

        binding.root.setOnLongClickListener {
            val position = holder.adapterPosition.takeIf { it != NO_POSITION }
                ?: return@setOnLongClickListener true
            this.listenerImgViewLong.viewTouchedLong(lista[position], position)

            return@setOnLongClickListener true
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MyOwnViewHolder -> holder.bind(lista[position])
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    fun setProductosEnLaVista(productos: MutableList<BookDetail>?) {
        if (productos != null) {
            this.lista = productos
            notifyDataSetChanged()
        } else {
            Toast.makeText(
                context,
                "la lista viene nula!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun getProductos(position: Int): BookDetail {
        return lista[position]
    }

}