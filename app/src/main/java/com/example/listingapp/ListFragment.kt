package com.example.listingapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.listingapp.model.ApixuWeatherResponse
import com.example.listingapp.model.CountryDetails
import com.example.listingapp.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment(), CountryListAdapter.ItemClick {
    private lateinit var listViewModel: ListViewModel
    private var mContext :Context?=null
    private lateinit var countryListAdapter: CountryListAdapter
    private val TAG:String ="ListFragment"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        recyclerView.apply {
            layoutManager = GridLayoutManager(mContext, 2)
            countryListAdapter = CountryListAdapter(mContext!!,  this@ListFragment)
            adapter = countryListAdapter
        }

        allCountryDetails()
        getWeatherReport("CHENNAI")
        symbol_searchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if(it.length>2){
                        listViewModel.searchDetails(it.toString()).observe(this@ListFragment,
                            Observer {country ->
                                Log.d(TAG, "onTextChanged: "+country.size)
                                if(country != null ) {

                                    updateAdapter(country)
                                }
                            })
                        getWeatherReport(it.toString())
                    }else{
                        allCountryDetails()

                    }
                }


            }
        })

    }

    private fun updateAdapter(it: List<CountryDetails>) {
        if (it.isNotEmpty()) {
            progressBar.visibility = View.GONE
            nodataa.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            countryListAdapter.run {
                coutryList.clear()
                coutryList.addAll(it)
                notifyDataSetChanged()
            }
        } else {
            recyclerView.visibility = View.GONE
            progressBar.visibility = View.GONE
            nodataa.visibility = View.VISIBLE
        }

    }

    override fun onItemClick(position: Int) {
        (activity as MainActivity).addChildFragment(countryListAdapter,position)
    }
    private fun allCountryDetails(){
        listViewModel.getCountryDetails().observe(this, Observer {
            Log.d(TAG, "countryList: " + it.size)
            updateAdapter(it)
        })
    }
    private fun getWeatherReport(searchCity:String){
        listViewModel.getWeatherDetails(searchCity).observe(this, Observer {
            (activity as MainActivity).updateweatherCard(it)
        })
    }
}