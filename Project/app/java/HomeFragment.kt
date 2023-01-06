package com.example.projectmwsp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.projectmwsp.adapterRecyclerHome
import com.example.projectmwsp.dataRecyclerHome
import org.json.JSONException
import org.json.JSONObject


class HomeFragment : Fragment() {
//    private lateinit var textToken : TextView

    //variable untuk recycler view
    private lateinit var recyclerView: RecyclerView

    // variable untuk data userlist
    private lateinit var dataUserList: ArrayList<dataRecyclerHome>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //inisialisasi
//        textToken = view.findViewById(R.id.textTokennya)

        //inisialisasi recycler view
        recyclerView = view.findViewById(R.id.recycleHome)

        //inisialisasi data user
        dataUserList = ArrayList<dataRecyclerHome>()



        //getToken
//        val preferenceShared = activity?.getSharedPreferences("GET_TOKEN", Context.MODE_PRIVATE)
//        val getToken = preferenceShared?.getString("token", "")
//        textToken.setText(getToken)

//        //data dummy example
//        val image = arrayOf(
//            R.drawable.img_1,
//            R.drawable.img_2,
//            R.drawable.img_3,
//            R.drawable.img_1
//        )
//        for (i in image.indices) {
//            dataUserList.add(
//                dataRecyclerHome(
//                    image[i]
//                )
//            )
//            populateData()
//        }


        getDataHome()
        return view

    }
    private fun getDataHome(){
        AndroidNetworking.get("https://grocery-api.tonsu.site/products/katalog")
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
                            for (i in 0 until getJSONArray.length()){
                                val getItem = getJSONArray.getJSONObject(i)
                                dataUserList.add(
                                    dataRecyclerHome(
                                        getItem.getInt("id"),
                                        getItem.getString("image")
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
        val adp = activity?.let { adapterRecyclerHome(it, dataUserList) }
        recyclerView.adapter = adp
    }
}