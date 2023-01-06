package com.example.latihanrecycler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class RecyclerActivity : AppCompatActivity() {
    private lateinit var buttonPindah: TextView

    //variable untuk recycler view
    private lateinit var recyclerView: RecyclerView

    // variable untuk data userlist
    private lateinit var dataUserList: ArrayList<dataRecylerUser>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)



        //inisialisasi recycler view
        recyclerView = findViewById(R.id.recycleUser)

        //inisialisasi data user
        dataUserList = ArrayList<dataRecylerUser>()

        //data dummy example
        val image = arrayOf(
            R.drawable.img_prod1,
            R.drawable.img_prod2,
            R.drawable.img_prod3,
            R.drawable.img_prod4,
            R.drawable.img_prod1,
            R.drawable.img_prod2,
            R.drawable.img_prod3,
            R.drawable.img_prod4

        )
        val nama = arrayOf(
            "CREWNECK MANHATTAN",
            "CREWNECK NEW YORK",
            "JACKET PARKA",
            "HOODIE",
            "CREWNECK MANHATTAN",
            "CREWNECK NEW YORK",
            "JACKET PARKA",
            "HOODIE"
        )
        val picis = arrayOf(
            "pieces",
            "pieces",
            "pieces",
            "pieces",
            "pieces",
            "pieces",
            "pieces",
            "pieces"

        )
        val harga = arrayOf(
            "Rp 110.000",
            "Rp 130.000",
            "Rp 200.000",
            "Rp 180.000",
            "Rp 110.000",
            "Rp 130.000",
            "Rp 200.000",
            "Rp 180.000"

        )
        val diskon = arrayOf(
            "Rp 150.000",
            "Rp 170.000",
            "Rp 250.000",
            "Rp 123.000",
            "Rp 150.000",
            "Rp 170.000",
            "Rp 250.000",
            "Rp 123.000"
        )

        for (i in image.indices) {
            dataUserList.add(
                dataRecylerUser(
                    image[i],
                    nama[i],
                    picis[i],
                    harga[i],
                    diskon[i]
                )
            )
            populateData()
        }
        buttonPindah = findViewById(R.id.daftaruser)

        buttonPindah.setOnClickListener(){
            val intent = Intent(this, RecyclerActivityPos ::class.java)
            startActivity(intent)
        }


    }
    private fun populateData(){
        val linearManager = LinearLayoutManager(this)
        linearManager.reverseLayout = true
        linearManager.stackFromEnd = true
        recyclerView.layoutManager = linearManager
        val adp = adapterRecyclerUser(this, dataUserList)
        recyclerView.adapter = adp



    }

}