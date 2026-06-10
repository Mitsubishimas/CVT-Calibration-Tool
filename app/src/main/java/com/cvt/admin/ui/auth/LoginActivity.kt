package com.cvt.admin.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cvt.admin.MainActivity
import com.cvt.admin.R

class LoginActivity : AppCompatActivity() {
    
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        
        btnLogin.setOnClickListener {
            if (etPassword.text.toString() == "CVT_Admin_2024") {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Неверный пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
