package com.example.anchorbooks_rodrigojaviergarridodagle.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anchorbooks_rodrigojaviergarridodagle.R
import com.example.anchorbooks_rodrigojaviergarridodagle.databinding.FragmentDetailsBinding
import com.example.anchorbooks_rodrigojaviergarridodagle.model.entities.BookDetail
import com.example.ensayoantesdelapruebadelsabado.services.ListenerBookDetail
import com.example.ensayoantesdelapruebadelsabado.ui.adapters.BookDetailAdapter
import com.example.ensayoantesdelapruebadelsabado.viewmodel.BookDetailViewModel
import com.example.ensayoantesdelapruebadelsabado.viewmodel.BookListingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BookSavedFragment : Fragment(), ListenerBookDetail {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: BookDetailAdapter
    private lateinit var model: BookDetailViewModel

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)
        model = ViewModelProvider(this).get(BookDetailViewModel::class.java)
        adapter = BookDetailAdapter(
            mutableListOf(),
            requireContext(), this
        )

        initRecyclerView()

        model.listaDetalles.observe(viewLifecycleOwner, Observer {
            adapter.setListaDetalleEnLaVista(it)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        volverAmainList()
    }

    override fun viewTouchedDetailShort(producto: BookDetail, position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            model.deleteWithViewModel(producto)
        }
    }

    override fun viewTouchedDetailLong(producto: BookDetail, id: Int) {
        TODO("Not yet implemented")
    }

    private fun volverAmainList() {
        binding.btnReturnMainList.setOnClickListener {
            navController.navigate(R.id.next_action)
        }
    }


    private fun initRecyclerView() {
        binding.recyclerDetailPage.hasFixedSize()
        binding.recyclerDetailPage.layoutManager =
            LinearLayoutManager(requireContext())
        binding.recyclerDetailPage.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}