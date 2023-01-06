package com.example.projectmwsp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class adapterRecyclerPromo(val context : Context, val dataUser : ArrayList<dataRecyclerPromo>) : RecyclerView.Adapter<adapterRecyclerPromo.MyViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.fetch_recycler_promo,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataUser [position]

        val getId = currentItem.id
        val getImage = currentItem.image
        val getProduct = currentItem.product_name
        val getUnit = currentItem.unit
        val getPriceori = currentItem.original_price
        val getMemberprice = currentItem.member_price

        Picasso.get()
            .load(getImage)
            .into(holder.imagePreview)

        holder.imagePreview.setOnClickListener {
            val intent = Intent(context, dataRecyclerPromo::class.java)
            intent.putExtra("image",getImage)
            context.startActivity(intent)
        }



//        holder.imageUser.setImageResource(currentItem.image)
        holder.productName.text = currentItem.product_name
        holder.pcs.text = currentItem.unit
        holder.diskon.text = currentItem.member_price.toString()
        holder.harga.text = currentItem.original_price.toString()


        //untuk button plus
        holder.btnAktifkan.setOnClickListener {
            Toast.makeText(context, "berhasil diaktifkan", Toast.LENGTH_SHORT).show()
            val jsonObject = JSONObject()
            try {
                jsonObject.put("product_id", getId.toString().trim())
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            //get token
            val sharedPreferences = context.getSharedPreferences("GET_TOKEN", Context.MODE_PRIVATE)
            val getToken = sharedPreferences.getString("token", null)
            val editornya = sharedPreferences.edit()
            editornya.putString("token",getToken)
            editornya.commit()

            AndroidNetworking.post("https://grocery-api.tonsu.site/products/activate")
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .addHeaders("token", "Bearer" + " " + getToken)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        // do anything with response
                        try {
                            Log.d("ini respon", response.toString())
                            if (response?.getString("success").equals("true")) {
                                val getMessage = response?.getString("message")
                            }
                        } catch (eror: JSONException) {
                            Toast.makeText(context, eror.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    override fun onError(error: ANError) {
                        // handle error
                    }
                })

        }

    }

    override fun getItemCount(): Int {
        return dataUser.size
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
//        val getImage : ImageView = itemView.findViewById(R.id.photo_user)
        val productName : TextView = itemView.findViewById(R.id.nameProduk)
        val pcs : TextView = itemView.findViewById(R.id.pcs)
        val harga : TextView = itemView.findViewById(R.id.harga)
        val diskon : TextView = itemView.findViewById(R.id.diskon)
        val imagePreview : ImageView = itemView.findViewById(R.id.photo_user)
        val btnAktifkan : Button = itemView.findViewById(R.id.btnAktifkan)
    }

}