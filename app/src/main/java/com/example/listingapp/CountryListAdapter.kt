package com.example.listingapp

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import com.example.listingapp.model.CountryDetails
import kotlinx.android.synthetic.main.countrylistitem.view.*

class CountryListAdapter(private val context:Context,
                        private val onItemClick:ItemClick):RecyclerView.Adapter<CountryListAdapter.ChildHolder>() {
     val coutryList:MutableList<CountryDetails> = mutableListOf()
    class ChildHolder(view:View):RecyclerView.ViewHolder(view){
        val thumbnail = view.thumbnail as AppCompatImageView
        val title = view.title  as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildHolder {
        return ChildHolder(LayoutInflater.from(context).inflate(R.layout.countrylistitem,parent,false))
    }

    override fun getItemCount(): Int {
        return coutryList.size
    }

    override fun onBindViewHolder(holder: ChildHolder, position: Int) {
        val response = coutryList[position]
        holder.title.text = response.name



//            holder.thumbnail.load("https://restcountries.eu/data/aut.svg")

        val imageLoader = ImageLoader.Builder(context).componentRegistry {
            add(SvgDecoder(context))
        }.build()
        Coil.setImageLoader(imageLoader)
        if(!response.flag!!.contains("aut")){
            holder.thumbnail.load(response.flag)
        }
        holder.thumbnail.setOnClickListener { onItemClick.onItemClick(position) }
    }
    interface ItemClick{
        fun onItemClick(position: Int)
    }
}