package com.example.listingapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.Coil
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import kotlinx.android.synthetic.main.fragment_blank.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "imageURL"
private const val ARG_PARAM2 = "name"
private const val ARG_PARAM3 = "capital"
private const val ARG_PARAM4 = "region"
private const val ARG_PARAM5 = "area"


class DetailsCountry : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: String? = null
    private var param5: String? = null
    private var mContext:Context? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
            param4 = it.getString(ARG_PARAM4)
            param5 = it.getString(ARG_PARAM5)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageLoader = ImageLoader.Builder(mContext!!).componentRegistry {
            add(SvgDecoder(mContext!!))
        }.build()
        Coil.setImageLoader(imageLoader)
        imgurl.load(param1)
        name.text = this.getString(R.string.CountryName,param2)
        capital.text = this.getString(R.string.capital,param3)
        population.text = this.getString(R.string.region,param4)
        numericCode.text = this.getString(R.string.area,param4)
    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String,param3: String,
        param4: String,param5: String) =
            DetailsCountry().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                    putString(ARG_PARAM4, param4)
                    putString(ARG_PARAM5, param5)

                }
            }
    }
}