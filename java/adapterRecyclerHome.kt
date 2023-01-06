package com.example.latihanrecycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapterRecyclerHome(val context : Context, val dataUser : ArrayList<dataRecyclerHome>) : RecyclerView.Adapter<adapterRecyclerHome.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterRecyclerHome.MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.fetch_recycler_home,parent,false)
        return adapterRecyclerHome.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: adapterRecyclerHome.MyViewHolder, position: Int) {
        val currentItem = dataUser [position]


        holder.imageBanner.setImageResource(currentItem.image)


    }
    override fun getItemCount(): Int {
        return dataUser.size
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val imageBanner : ImageView = itemView.findViewById(R.id.imgBanner)

    }

}
