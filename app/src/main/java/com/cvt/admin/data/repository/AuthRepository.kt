package com.cvt.admin.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.cvt.admin.data.api.ApiService
import com.cvt.admin.data.api.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(private val apiService: ApiService, private val context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences("cvt_prefs", Context.MODE_PRIVATE)
    
    suspend fun login(password: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.login(LoginRequest(password))
            if (response.isSuccessful && response.body() != null) {
                val token = response.body()!!.token
                saveToken(token)
                Result.success(token)
            } else {
                Result.failure(Exception("Неверный пароль"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun saveToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
    }
    
    fun getToken(): String? = prefs.getString("auth_token", null)
    
    fun logout() {
        prefs.edit().remove("auth_token").apply()
    }
    
    fun isLoggedIn(): Boolean = getToken() != null
}
