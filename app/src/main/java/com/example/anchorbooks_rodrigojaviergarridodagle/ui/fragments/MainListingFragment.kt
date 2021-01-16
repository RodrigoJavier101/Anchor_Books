package com.example.anchorbooks_rodrigojaviergarridodagle.ui.fragments

import android.app.Dialog
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.anchorbooks_rodrigojaviergarridodagle.R
import com.example.anchorbooks_rodrigojaviergarridodagle.databinding.DialogDetailBinding
import com.example.anchorbooks_rodrigojaviergarridodagle.databinding.FragmentMainListBinding
import com.example.anchorbooks_rodrigojaviergarridodagle.model.entities.BookDetail
import com.example.ensayoantesdelapruebadelsabado.services.ListenersBookListing
import com.example.ensayoantesdelapruebadelsabado.ui.adapters.BookListingAdapter
import com.example.ensayoantesdelapruebadelsabado.viewmodel.BookListingViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainListingFragment : Fragment(), ListenersBookListing {

    private var _binding: FragmentMainListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: BookListingAdapter
    private lateinit var model: BookListingViewModel

    private var soundPool: SoundPool? = null
    private var sonido: Int = -1

    private var bindingDialog: DialogDetailBinding? = null


    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainListBinding.inflate(layoutInflater)

        model = ViewModelProvider(this).get(BookListingViewModel::class.java)
        adapter = BookListingAdapter(
            mutableListOf(),
            requireContext(), this, this
        )

        initRecyclerView()

        model.list.observe(viewLifecycleOwner, Observer {
            adapter.setProductosEnLaVista(it)
        })

        setSoundEfect()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.btnGuardados.setOnClickListener {
            navController.navigate(R.id.next_action)
        }
    }

    override fun viewTouchedShort(producto: BookDetail, position: Int) {
        soundPool!!.play(sonido, 1f, 1f, 0, 0, 1f)

        displayDetailDialog(producto)
    }

    override fun viewTouchedLong(producto: BookDetail, id: Int) {
        soundPool!!.play(sonido, 1f, 1f, 0, 0, 1f)
        CoroutineScope(Dispatchers.IO).launch {
            saveInDDBB(producto)
        }
    }

    private fun setSoundEfect() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            var audioAttributes =
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
            soundPool =
                SoundPool.Builder().setMaxStreams(6).setAudioAttributes(audioAttributes).build()
        } else {
            soundPool = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
        }

        sonido = soundPool!!.load(context, R.raw.sn, 1)
    }


    private fun initRecyclerView() {
        binding.recyclerMainPage.hasFixedSize()
        binding.recyclerMainPage.layoutManager =
            LinearLayoutManager(requireContext())
        binding.recyclerMainPage.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        soundPool!!.release()
        soundPool = null
    }

    private fun displayDetailDialog(bookDetail: BookDetail) {
        bindingDialog = DialogDetailBinding.inflate(layoutInflater)
        val dialog = Dialog(bindingDialog!!.root.context)
        dialog.requestWindowFeature(
            Window.FEATURE_NO_TITLE
        )
        dialog.setCancelable(true)
        dialog.setContentView(
            bindingDialog!!.root
        )


        model.getBookDetailUsingViewModelToRetrofitManager(bookDetail.id)
        model.detail!!.observe(
            this,
            Observer {
                var det: BookDetail = model.detail!!.value!!
                Picasso.get().load(det.imageLink).into(bindingDialog!!.imageBookDetail)
                bindingDialog!!.textViewAuthor.text = det.author.toString()
                bindingDialog!!.textViewTitle.text = det.title.toString()
                bindingDialog!!.textViewPages.text = det.pages.toString()
                bindingDialog!!.textViewCountry.text = det.country.toString()
                bindingDialog!!.textViewLanguaje.text = det.language.toString()
                bindingDialog!!.textViewYear.text = det.year.toString()
                bindingDialog!!.textViewPrice.text = det.price.toString()
                bindingDialog!!.textViewLastPrice.text = det.lastPrice.toString()
                bindingDialog!!.textViewLink.text = det.link.toString()

                if (det.delivery == true) {
                    bindingDialog!!.textViewDelivery.text = "Delivery habilitado"
                } else {
                    bindingDialog!!.textViewDelivery.text = "Delivery no disponible"
                }
            })

        bindingDialog!!.btnGoBackDetail.setOnClickListener {
            dialog.dismiss()
        }

        bindingDialog!!.btnGoToSendMail.setOnClickListener {
            YoYo.with(Techniques.Shake)
                .duration(400)
                .playOn(bindingDialog!!.linearLayoutCompat)
            goToMail(bookDetail)
        }

        dialog.show()
    }

    private fun goToMail(bookDetail: BookDetail) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, bookDetail.toString())

        startActivity(intent)

    }

    private suspend fun saveInDDBB(book: BookDetail) {
        model.insertInDatabaseUsingViewModel(book)
    }
}