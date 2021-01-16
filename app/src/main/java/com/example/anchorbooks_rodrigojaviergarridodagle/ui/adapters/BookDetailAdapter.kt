package com.example.ensayoantesdelapruebadelsabado.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anchorbooks_rodrigojaviergarridodagle.databinding.ItemDetailListBinding
import com.example.anchorbooks_rodrigojaviergarridodagle.model.entities.BookDetail
import com.example.ensayoantesdelapruebadelsabado.services.ListenerBookDetail

class BookDetailAdapter(
    private var lista: MutableList<BookDetail> = mutableListOf(),
    private val context: Context, private var listener: ListenerBookDetail
) : RecyclerView.Adapter<BookDetailAdapter.BaseViewHolder<*>>() {

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    inner class MyOwnViewHolder__(val binding: ItemDetailListBinding) :
        BookDetailAdapter.BaseViewHolder<BookDetail>(binding.root) {

        override fun bind(item: BookDetail) = with(binding) {
            textViewTitle.text = item.title
            textViewPrice.text = "$ ${item.price.toString()}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        var binding = ItemDetailListBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        val holder = MyOwnViewHolder__(binding)

        binding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                ?: return@setOnClickListener

            this.listener.viewTouchedDetailShort(lista[position], position)

        }

        binding.root.setOnLongClickListener {
            val position = holder.adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                ?: return@setOnLongClickListener true
            this.listener.viewTouchedDetailLong(lista[position], position)

            return@setOnLongClickListener true
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is BookDetailAdapter.MyOwnViewHolder__ -> holder.bind(lista[position])
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    fun setListaDetalleEnLaVista(books: MutableList<BookDetail>) {
        if (books != null) {
            this.lista = books
            notifyDataSetChanged()
        } else {
            Log.d("MessageAdapElse ---->", "layout-car productos esta nula en este metodo")
        }
    }

    fun getDetail(position: Int): BookDetail {
        return lista[position]
    }
}
