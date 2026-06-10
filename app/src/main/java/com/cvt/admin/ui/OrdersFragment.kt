package com.cvt.admin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.cvt.admin.R
import com.cvt.admin.data.api.RetrofitClient
import com.cvt.admin.data.repository.AuthRepository
import kotlinx.coroutines.launch

class OrdersFragment : Fragment() {
    
    private lateinit var authRepository: AuthRepository
    private lateinit var tvOrders: TextView
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_orders, container, false)
        tvOrders = view.findViewById(R.id.text_orders)
        
        authRepository = AuthRepository(RetrofitClient.instance, requireContext())
        
        loadOrders()
        
        return view
    }
    
    private fun loadOrders() {
        lifecycleScope.launch {
            try {
                val token = authRepository.getToken()
                if (token != null) {
                    val response = RetrofitClient.instance.getOrders("Bearer $token", "calibration")
                    if (response.isSuccessful && response.body() != null) {
                        val orders = response.body()!!
                        if (orders.isNotEmpty()) {
                            tvOrders.text = buildString {
                                appendLine("📋 ПОСЛЕДНИЕ ЗАКАЗЫ")
                                appendLine()
                                orders.take(5).forEach { order ->
                                    appendLine("${order.orderNumber} | ${order.amount}₽ | ${order.status}")
                                }
                            }
                        } else {
                            tvOrders.text = "Нет активных заказов"
                        }
                    } else {
                        tvOrders.text = "Ошибка загрузки заказов"
                    }
                }
            } catch (e: Exception) {
                tvOrders.text = "Ошибка: ${e.message}"
            }
        }
    }
}
