package com.example.listingapp

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import com.example.listingapp.model.CountryDetails
import kotlinx.android.synthetic.main.countrylistitem.view.*

class CountryListAdapter(private val context:Context,private val coutryList:List<CountryDetails>):RecyclerView.Adapter<CountryListAdapter.ChildHolder>() {
    class ChildHolder(view:View):RecyclerView.ViewHolder(view){
        val thumbnail = view.thumbnail as ImageView
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

        val imageLoader = ImageLoader.Builder(context).componentRegistry {
            add(SvgDecoder(context))
        }.build()
        Coil.setImageLoader(imageLoader)
        holder.thumbnail.load(response.flag)
    }
}