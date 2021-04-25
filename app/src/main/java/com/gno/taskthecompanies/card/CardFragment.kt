package com.gno.taskthecompanies.card

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gno.taskthecompanies.R
import com.gno.taskthecompanies.databinding.CardFragmentBinding
import com.gno.taskthecompanies.retrofit.Api
import com.squareup.picasso.Picasso

class CardFragment : Fragment() {

    private val TAG = "CardFragment"
    private lateinit var cardViewModel: CardViewModel
    private lateinit var cardFragmentBinding: CardFragmentBinding

    private fun TextView.setTextWithTitle(title: String, text: String) {
        this.text = "$title: $text"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //dataBinding
        cardFragmentBinding = CardFragmentBinding.inflate(
            inflater, container, false
        )

        return cardFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)

        val id = arguments?.getString("id") ?: ""
        cardViewModel.getCompanyData(id)
        cardViewModel.companyDataLiveData.observe(viewLifecycleOwner, { companyData ->

            Picasso.get().load(Api.BASE_URL + companyData.img)
                .error(R.drawable.ic_blank_company_picture_100)
                .into(cardFragmentBinding.cardFragmentImage)

            cardFragmentBinding.cardFragmentName.setTextWithTitle("Наименование", companyData.name)
            cardFragmentBinding.cardFragmentDescription.setTextWithTitle(
                "Описание",
                companyData.description
            )

            if (companyData.www != "") {
                cardFragmentBinding.cardFragmentWeb.visibility = View.VISIBLE
                cardFragmentBinding.cardFragmentWeb.setTextWithTitle(
                    "Электронный адрес",
                    companyData.www
                )

                cardFragmentBinding.cardFragmentWeb.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(companyData.www))
                    startActivity(intent)
                }
            } else cardFragmentBinding.cardFragmentWeb.visibility = View.GONE

            if (companyData.phone != "") {
                cardFragmentBinding.cardFragmentPhone.visibility = View.VISIBLE
                cardFragmentBinding.cardFragmentPhone.setTextWithTitle("Телефон", companyData.phone)
                cardFragmentBinding.cardFragmentPhone.setOnClickListener {
                    val phone = companyData.phone
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:$phone"))
                    startActivity(intent)
                }

            } else cardFragmentBinding.cardFragmentPhone.visibility = View.GONE


            if (companyData.lat != 0.0 && companyData.lon != 0.0) {
                cardFragmentBinding.cardFragmentCoordinates.visibility = View.VISIBLE
                cardFragmentBinding.cardFragmentCoordinates.setOnClickListener {
                    val lat = companyData.lat
                    val lon = companyData.lon
                    val gmmIntentUri = Uri.parse("google.streetview:cbll=$lat,$lon")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    try {
                        startActivity(mapIntent)
                    } catch (e: Exception) {
                        Log.e(TAG, e.message, e)
                    }
                }
            } else cardFragmentBinding.cardFragmentCoordinates.visibility = View.GONE
        })
    }
}