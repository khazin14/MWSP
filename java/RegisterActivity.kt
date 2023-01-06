package com.example.latihanrecycler

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    private lateinit var buttonPindah : Button
    private lateinit var buttonPindahtext : TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var textNamanya = findViewById(R.id.inputNamareg) as EditText
        var textEmailnya = findViewById(R.id.inputEmailreg) as EditText
        var textPasswordnya = findViewById(R.id.inputPasswordreg) as EditText


        // inisialisasi
        buttonPindah = findViewById(R.id.btnMove)


        // fungsikan
        buttonPindah.setOnClickListener {
            val nama = textNamanya.text;print(nama)
            val email = textEmailnya.text;print(email)
            val password = textPasswordnya.text;print(password)
            val intent = Intent(this,HomeActivity::class.java)
            //intent.putExtra("username",values :) atau bisa seperti ini
            intent.putExtra("namaku", nama.toString())
            intent.putExtra("emailku", email.toString())
            intent.putExtra("passwordku", password.toString())
            startActivity(intent)
            Toast.makeText(this,"Berhasil Register", Toast.LENGTH_SHORT).show()
        }

        // inisialisasi
        buttonPindahtext = findViewById(R.id.loginReg)


        // fungsikan
        buttonPindahtext.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            //intent.putExtra("username",values : ) atau bisa seperti ini
            startActivity(intent)
        }
    }
}