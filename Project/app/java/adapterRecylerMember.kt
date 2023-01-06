package com.example.projectmwsp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

class adapterRecylerMember(val context : Context, val dataPos : ArrayList<dataRecyclerMember>) : RecyclerView.Adapter<adapterRecylerMember.MyViewHolder>(){



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(context).inflate(R.layout.fetch_recycler_member,parent,false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val currentItem = dataPos [position]

//            val getId = currentItem.id
            val getImage = currentItem.image
            val getId = currentItem.id

//            Picasso.get()
//                .load(getImage)
//                .into(holder.memberImage)
//
//            holder.memberImage.setOnClickListener {
//                val intent = Intent(context, dataRecyclerPromo::class.java)
//                intent.putExtra("image",getImage)
//                context.startActivity(intent)
//            }

            holder.nama_pdk1nya.text = currentItem.product_name
            holder.hargaBaju1nya.text = currentItem.member_price.toString()
            holder.harga_total1nya.text = currentItem.original_price.toString()
            holder.harga_diskon.text = currentItem.original_price.toString()
//            holder.nama_pdk2nya.text = currentItem.nama2
//            holder.hargaBaju2nya.text = currentItem.baju2
//            holder.harga_total2nya.text = currentItem.total2

            //untuk button plus
            holder.btnPlus.setOnClickListener {
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
            return dataPos.size
        }
        class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            val memberImage : TextView = itemView.findViewById(R.id.memberImg)
            val nama_pdk1nya : TextView = itemView.findViewById(R.id.nama_pdk1)
            val hargaBaju1nya : TextView = itemView.findViewById(R.id.hargaBaju1)
            val harga_total1nya : TextView = itemView.findViewById(R.id.harga_total1)
            val harga_diskon : TextView = itemView.findViewById(R.id.hargaItem)
            val btnPlus : Button = itemView.findViewById(R.id.plusMember)
//            val hargaBaju2nya : TextView = itemView.findViewById(R.id.hargaBaju2)
//            val harga_total2nya : TextView = itemView.findViewById(R.id.harga_total2)
        }

}
