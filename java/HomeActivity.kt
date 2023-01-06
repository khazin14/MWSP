package com.example.latihanrecycler

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {
    private lateinit var buttonPindahbanner: TextView

    //variable untuk recycler view
    private lateinit var recyclerView: RecyclerView

    // variable untuk data userlist
    private lateinit var dataUserList: ArrayList<dataRecyclerHome>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //inisialisasi recycler view
        recyclerView = findViewById(R.id.recycleHome)

        //inisialisasi data user
        dataUserList = ArrayList<dataRecyclerHome>()

        // inisialisasi
        buttonPindahbanner = findViewById(R.id.moveHome)

        //data dummy example
        val image = arrayOf(
            R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3
        )
        for (i in image.indices) {
            dataUserList.add(
                dataRecyclerHome(
                    image[i]
                )
            )
            populateData()


            // fungsikan
            buttonPindahbanner.setOnClickListener {
                val intent = Intent(this, RecyclerActivity::class.java)
                //intent.putExtra("username",values : ) atau bisa seperti ini
                startActivity(intent)
            }
        }


    }

    private fun populateData() {
        val linearManager = LinearLayoutManager(this)
        linearManager.reverseLayout = true
        linearManager.stackFromEnd = true
        recyclerView.layoutManager = linearManager
        val adp = adapterRecyclerHome(this, dataUserList)
        recyclerView.adapter = adp
    }
}
