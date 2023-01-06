package com.example.projectmwsp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var buttonPindah : Button
    private lateinit var buttonPindahtext : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        var textEmailnya = findViewById(R.id.inputEmail) as EditText
//        var textPassword = findViewById(R.id.inputPassword) as EditText

        // inisialisasi
        buttonPindah = findViewById(R.id.btnMove)
        loginEmail = findViewById(R.id.inEmail)
        loginPassword = findViewById(R.id.inPassword)


        // fungsikan
        buttonPindah.setOnClickListener {
            val jsonObject = JSONObject()
            try {
                jsonObject.put("email", loginEmail.text.toString().trim())
                jsonObject.put("password", loginPassword.text.toString().trim())
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            AndroidNetworking.post("https://grocery-api.tonsu.site/auth/login")
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .addHeaders("Content-Type", "application/json")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        // do anything with response
                        try {
                            Log.d("ini respon", response.toString())
                            if (response?.getString("success").equals("true")){
                                val getMessage = response?.getString("message")
                                Toast.makeText(this@LoginActivity, getMessage, Toast.LENGTH_SHORT).show()

                                //getToken
                                val getToken = response?.getString("token")
                                val preferenceShared = getSharedPreferences("GET_TOKEN", Context.MODE_PRIVATE)
                                val editor = preferenceShared.edit()
                                editor.putString("token", getToken)
                                editor.putString("nama", "khazin")
                                editor.commit()
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)

                            }

                        }catch (e : JSONException){
                            Toast.makeText(this@LoginActivity, e.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(error: ANError) {
                        // handle error
                    }
                })
//            if (loginEmail.text.toString().trim().isEmpty()) {
//                loginEmail.error = "Email kosong"
//            } else if (loginPassword.text.toString().trim().isEmpty()){
//                loginPassword.error = "Password Kosong"
//            } else if (loginPassword.getText().toString().length < 8){
//                loginPassword.error = "Username kurang dari 8"
//            }
//            val email = textEmailnya.text;print(email)
//                val password = textPassword.text;print(password)
//                val intent = Intent(this, RegisterActivity::class.java)
//                //intent.putExtra("username",values : ) atau bisa seperti ini
//                intent.putExtra("emailku", email.toString())
//                intent.putExtra("passwordku", password.toString())
//                startActivity(intent)
//                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
            }

            // inisialisasi
            buttonPindahtext = findViewById(R.id.daftarLogin)


            // fungsikan
            buttonPindahtext.setOnClickListener {
                val intent = Intent(this, RegisterActivity::class.java)
                //intent.putExtra("username",values : ) atau bisa seperti ini
                startActivity(intent)
            }
//            buttonPindah.setOnClickListener {
//                val intent = Intent(this, MainActivity::class.java)
//                //intent.putExtra("username",values : ) atau bisa seperti ini
//                startActivity(intent)
//            }
        }
    }

