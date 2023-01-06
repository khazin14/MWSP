package com.example.latihanrecycler

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    private lateinit var buttonPindah : Button
    private lateinit var buttonPindahtext : TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var textEmailnya = findViewById(R.id.inputEmail) as EditText
        var textPassword = findViewById(R.id.inputPassword) as EditText

        // inisialisasi
        buttonPindah = findViewById(R.id.btnMove)


        // fungsikan
        buttonPindah.setOnClickListener {
            val email = textEmailnya.text;print(email)
            val password = textPassword.text;print(password)
            val intent = Intent(this,HomeActivity::class.java)
            //intent.putExtra("username",values : ) atau bisa seperti ini
            intent.putExtra("emailku", email.toString())
            intent.putExtra("passwordku", password.toString())
            startActivity(intent)
            Toast.makeText(this,"Login Berhasil", Toast.LENGTH_SHORT).show()
        }

        // inisialisasi
        buttonPindahtext = findViewById(R.id.daftarLogin)


        // fungsikan
        buttonPindahtext.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            //intent.putExtra("username",values : ) atau bisa seperti ini
            startActivity(intent)
        }
    }
}