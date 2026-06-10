package com.cvt.admin.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cvt.admin.MainActivity
import com.cvt.admin.R
import com.cvt.admin.data.api.RetrofitClient
import com.cvt.admin.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var authRepository: AuthRepository
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBar)
        
        authRepository = AuthRepository(RetrofitClient.instance, this)
        
        // Если уже авторизован, переходим в MainActivity
        if (authRepository.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        
        btnLogin.setOnClickListener {
            val password = etPassword.text.toString()
            if (password.isEmpty()) {
                etPassword.error = "Введите пароль"
                return@setOnClickListener
            }
            
            performLogin(password)
        }
    }
    
    private fun performLogin(password: String) {
        progressBar.visibility = ProgressBar.VISIBLE
        btnLogin.isEnabled = false
        
        lifecycleScope.launch {
            val result = authRepository.login(password)
            progressBar.visibility = ProgressBar.GONE
            btnLogin.isEnabled = true
            
            result.onSuccess {
                Toast.makeText(this@LoginActivity, "Вход выполнен успешно", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }.onFailure {
                Toast.makeText(this@LoginActivity, "Ошибка: ${it.message}", Toast.LENGTH_LONG).show()
                etPassword.text?.clear()
            }
        }
    }
}
