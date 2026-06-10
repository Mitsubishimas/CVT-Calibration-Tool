package com.cvt.admin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.cvt.admin.R
import com.cvt.admin.data.api.RetrofitClient
import com.cvt.admin.data.repository.AuthRepository
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    
    private lateinit var authRepository: AuthRepository
    private lateinit var tvStats: TextView
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        tvStats = view.findViewById(R.id.text_dashboard)
        
        authRepository = AuthRepository(RetrofitClient.instance, requireContext())
        
        loadStats()
        
        return view
    }
    
    private fun loadStats() {
        lifecycleScope.launch {
            try {
                val token = authRepository.getToken()
                if (token != null) {
                    val response = RetrofitClient.instance.getStats("Bearer $token")
                    if (response.isSuccessful && response.body() != null) {
                        val stats = response.body()!!
                        tvStats.text = buildString {
                            appendLine("📊 СТАТИСТИКА")
                            appendLine()
                            appendLine("👥 Пользователи: ${stats.users.total}")
                            appendLine("🆕 Новых сегодня: ${stats.users.newToday}")
                            appendLine("⭐ Активных подписок: ${stats.subscriptions.active}")
                            appendLine("🔑 Сгенерировано ключей: ${stats.keys.generated}")
                            appendLine("✅ Активировано ключей: ${stats.keys.activated}")
                            appendLine("💰 Доход: ${stats.revenue} ₽")
                        }
                    } else {
                        tvStats.text = "Ошибка загрузки статистики\nПроверьте подключение к интернету"
                    }
                }
            } catch (e: Exception) {
                tvStats.text = "Ошибка: ${e.message}\nПроверьте подключение к интернету"
            }
        }
    }
}
