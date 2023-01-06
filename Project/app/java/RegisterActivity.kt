package com.example.projectmwsp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class RegisterActivity : AppCompatActivity() {
    private lateinit var inputNama: EditText
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var buttonPindahlogin : TextView
    private lateinit var checkbox : CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //inisialiasi
        inputNama = findViewById(R.id.edNama)
        inputEmail = findViewById(R.id.edEmail)
        inputPassword = findViewById(R.id.edPassword)
        buttonRegister = findViewById(R.id.btnRegister)
        buttonPindahlogin = findViewById(R.id.loginReg)
        checkbox = findViewById(R.id.checkBox)

        //kondisi
        // if, else, if, else
        buttonRegister.setOnClickListener {
            val cek = checkbox.isChecked
            val jsonObject = JSONObject()
            try {
                jsonObject.put("name", inputNama.text.toString().trim() )
                jsonObject.put("email", inputEmail.text.toString().trim())
                jsonObject.put("password", inputPassword.text.toString().trim())
                jsonObject.put("terms", if (cek) 1 else 0)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            AndroidNetworking.post("https://grocery-api.tonsu.site/auth/register")
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
                                Toast.makeText(this@RegisterActivity, getMessage, Toast.LENGTH_SHORT).show()
                            }

                        }catch (e : JSONException){
                            Toast.makeText(this@RegisterActivity, e.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(error: ANError) {
                        // handle error
                    }
                })
//            if (inputNama.text.toString().trim().isEmpty()) {
//                inputNama.error = "Nama kosong"
//            } else if (inputEmail.text.toString().trim().isEmpty()){
//                inputEmail.error = "Email Kosong"
//            } else if (inputPassword.text.toString().trim().isEmpty()){
//                inputPassword.error = "Password Kosong"
//            } else if (inputPassword.getText().toString().length < 8){
//                inputPassword.error = "Username kurang dari 8"
//            } else {
//                Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
//            }
        }

        //fungsikan
        buttonPindahlogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            //intent.putExtra("username",values : ) atau bisa seperti ini
            startActivity(intent)
        }
    }
}