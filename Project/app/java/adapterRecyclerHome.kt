package com.example.projectmwsp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class adapterRecyclerHome(val context : Context, val dataRecyclerHome: ArrayList<dataRecyclerHome>) : RecyclerView.Adapter<adapterRecyclerHome.MyViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.fetch_recycler_home,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataRecyclerHome [position]
        // Get ID dan Image
        val getId = currentItem.id
        val getImage = currentItem.images

        Picasso.get()
            .load(getImage)
            .into(holder.imagePreview)

        holder.imagePreview.setOnClickListener {
            val intent =Intent(context, dataRecyclerHome::class.java)
            intent.putExtra("image",getImage)
            context.startActivity(intent)
        }
        holder.button.setOnClickListener {
            // Remove the item from the data source
            dataRecyclerHome.removeAt(position)
            // Notify the adapter that the item has been removed
            notifyItemRemoved(position)

            // Notify the adapter of the change in the data set
            notifyItemRangeChanged(position, dataRecyclerHome.size)

        }



    }

    override fun getItemCount(): Int {
        return dataRecyclerHome.size
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val imagePreview : ImageView = itemView.findViewById(R.id.imgBanner)
        val button: Button =itemView.findViewById(R.id.hapusHome)

    }

}
