package com.example.projectmwsp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONException
import org.json.JSONObject


class ExploreFragment : Fragment() {

//    private lateinit var buttonPindah: TextView

    //variable untuk recycler view
    private lateinit var recyclerView: RecyclerView

    // variable untuk data userlist
    private lateinit var dataUserList: ArrayList<dataRecyclerPromo>

    //var button
//    private lateinit var buttonPindah : Button


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_explore, container, false)
        //inisialisasi recycler view
        recyclerView = view.findViewById(R.id.recyclerPromo)

        //inisialisasi data user
        dataUserList = ArrayList<dataRecyclerPromo>()




        //data dummy example
//        val image = arrayOf(
//            R.drawable.img_prod1,
//            R.drawable.img_prod2,
//            R.drawable.img_prod3,
//            R.drawable.img_prod4,
//            R.drawable.img_prod1,
//            R.drawable.img_prod2,
//            R.drawable.img_prod3,
//            R.drawable.img_prod4
//
//        )
//        val nama = arrayOf(
//            "CREWNECK MANHATTAN",
//            "CREWNECK NEW YORK",
//            "JACKET PARKA",
//            "HOODIE",
//            "CREWNECK MANHATTAN",
//            "CREWNECK NEW YORK",
//            "JACKET PARKA",
//            "HOODIE"
//        )
//        val picis = arrayOf(
//            "pieces",
//            "pieces",
//            "pieces",
//            "pieces",
//            "pieces",
//            "pieces",
//            "pieces",
//            "pieces"
//
//        )
//        val harga = arrayOf(
//            "Rp 110.000",
//            "Rp 130.000",
//            "Rp 200.000",
//            "Rp 180.000",
//            "Rp 110.000",
//            "Rp 130.000",
//            "Rp 200.000",
//            "Rp 180.000"
//
//        )
//        val diskon = arrayOf(
//            "Rp 150.000",
//            "Rp 170.000",
//            "Rp 250.000",
//            "Rp 123.000",
//            "Rp 150.000",
//            "Rp 170.000",
//            "Rp 250.000",
//            "Rp 123.000"
//        )
//
//        for (i in image.indices) {
//            dataUserList.add(
//                dataRecyclerPromo(
//                    image[i],
//                    nama[i],
//                    picis[i],
//                    harga[i],
//                    diskon[i]
//                )
//            )
//            populateData()
//        }

        getDataPromo()
        return view
    }

    private fun getDataPromo() {
        AndroidNetworking.get("https://grocery-api.tonsu.site/products/promo")
//              .addJSONObjectBody(jsonObject) // posting json
            .setTag("test")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    // do anything with response
                    try {
                        Log.d("ini respon", response.toString())
                        if (response?.getString("success").equals("true")) {
                            val getJSONArray = response.getJSONArray("data")
                            for (i in 0 until getJSONArray.length()) {
                                val getItem = getJSONArray.getJSONObject(i)
                                dataUserList.add(
                                    dataRecyclerPromo(
                                        getItem.getString("image"),
                                        getItem.getString("product_name"),
                                        getItem.getString("unit"),
                                        getItem.getInt("original_price"),
                                        getItem.getInt("member_price"),
                                        getItem.getInt("id")
                                    )
                                )
                            }
                            //panggil function populateddata() disini
                            populateData()
                        }

                    } catch (error: JSONException) {
                        Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onError(error: ANError) {
                    // handle error
                }
            })

    }
    private fun populateData(){
        val linearManager = LinearLayoutManager(activity)
        linearManager.reverseLayout = true
        linearManager.stackFromEnd = true
        recyclerView.layoutManager = linearManager
        val adp = activity?.let{ adapterRecyclerPromo(it, dataUserList) }
        recyclerView.adapter = adp
    }

}


