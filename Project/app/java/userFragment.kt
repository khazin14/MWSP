package com.example.projectmwsp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
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
import kotlin.math.max


class userFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataUserMember: ArrayList<dataRecyclerMember>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user2, container, false)

        //inisialisasi recycler view
        recyclerView = view.findViewById(R.id.recyclerMember)

        //inisialisasi data user
        dataUserMember = ArrayList<dataRecyclerMember>()



//        val nama1 = arrayOf(
//            "JACKET",
//            "HOODIE",
//            "SWEATER",
//            "CREWNECK",
//            "JACKET",
//            "HOODIE",
//            "SWEATER",
//            "CREWNECK"
//        )
//        val baju1 = arrayOf(
//            "Rp 110.000",
//            "Rp 130.000",
//            "Rp 200.000",
//            "Rp 180.000",
//            "Rp 110.000",
//            "Rp 130.000",
//            "Rp 200.000",
//            "Rp 180.000"
//        )
//        val total1 = arrayOf(
//            "Total Rp 258.000",
//            "Total Rp 500.000",
//            "Total Rp 350.000",
//            "Total Rp 390.000",
//            "Total Rp 258.000",
//            "Total Rp 500.000",
//            "Total Rp 350.000",
//            "Total Rp 390.000"
//        )
//        val nama2 = arrayOf(
//            "KAOS",
//            "JEANS",
//            "CHINOS",
//            "KOLOR",
//            "KAOS",
//            "JEANS",
//            "CHINOS",
//            "KOLOR"
//        )
//        val baju2 = arrayOf(
//            "Rp 130.000",
//            "Rp 30.000",
//            "Rp 100.000",
//            "Rp 80.000",
//            "Rp 130.000",
//            "Rp 30.000",
//            "Rp 100.000",
//            "Rp 80.000"
//        )
//        val total2 = arrayOf(
//            "Total Rp 158.000",
//            "Total Rp 200.000",
//            "Total Rp 360.000",
//            "Total Rp 300.000",
//            "Total Rp 158.000",
//            "Total Rp 200.000",
//            "Total Rp 360.000",
//            "Total Rp 300.000"
//        )
//
//
//        for (i in nama1.indices) {
//            dataUserMember.add(
//                dataRecyclerMember(
//                    nama1[i],
//                    baju1[i],
//                    total1[i],
//                    nama2[i],
//                    baju2[i],
//                    total2[i]
//                )
//
//            )
//            populateData()
//        }

        getDataMember()
        return view
    }

    private fun getDataMember() {

//        get token
        val sharedPreferences = activity?.getSharedPreferences("GET_TOKEN", Context.MODE_PRIVATE)
        val getToken = sharedPreferences?.getString("token", null)
        val editornya = sharedPreferences?.edit()
        if (editornya != null) {
            editornya.putString("token", getToken)
        }
        if (editornya != null) {
            editornya.commit()
        }
//        editornya.putString("token",getToken)
//        editornya.commit()
        AndroidNetworking.get("https://grocery-api.tonsu.site/products/cart")
//            .addJSONObjectBody(jsonObject) // posting json
            .setTag("test")
            .addHeaders("token", "Bearer" + " " + getToken)
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
                                dataUserMember.add(
                                    dataRecyclerMember(
                                        getItem.getInt("product_id"),
                                        getItem.getString("image"),
                                        getItem.getString("product_name"),
                                        getItem.getInt("member_price"),
                                        getItem.getInt("original_price"),
                                        getItem.getString("discount_price"),

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
    private fun populateData() {
        val linearManager = LinearLayoutManager(activity)
        linearManager.reverseLayout = true
        linearManager.stackFromEnd = true
        recyclerView.layoutManager = linearManager
        val adp = activity?.let{ adapterRecylerMember(it,dataUserMember) }
        recyclerView.adapter = adp
    }
}
