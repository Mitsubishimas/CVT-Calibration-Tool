package com.cvt.admin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.cvt.admin.R
import com.cvt.admin.data.api.RetrofitClient
import com.cvt.admin.data.api.SendMessageRequest
import com.cvt.admin.data.repository.AuthRepository
import kotlinx.coroutines.launch

class ChatFragment : Fragment() {
    
    private lateinit var authRepository: AuthRepository
    private lateinit var spinnerUsers: Spinner
    private lateinit var etMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var tvChat: TextView
    
    private var users = listOf<com.cvt.admin.data.api.ChatUser>()
    private var currentUserId = 0
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        
        spinnerUsers = view.findViewById(R.id.spinner_users)
        etMessage = view.findViewById(R.id.et_message)
        btnSend = view.findViewById(R.id.btn_send)
        tvChat = view.findViewById(R.id.text_chat)
        
        authRepository = AuthRepository(RetrofitClient.instance, requireContext())
        
        loadUsers()
        
        btnSend.setOnClickListener {
            val message = etMessage.text.toString().trim()
            if (message.isNotEmpty() && currentUserId != 0) {
                sendMessage(message)
            }
        }
        
        return view
    }
    
    private fun loadUsers() {
        lifecycleScope.launch {
            try {
                val token = authRepository.getToken()
                if (token != null) {
                    val response = RetrofitClient.instance.getChatUsers("Bearer $token")
                    if (response.isSuccessful && response.body() != null) {
                        users = response.body()!!
                        val userNames = users.map { "${it.name} (ID: ${it.id})" }.toTypedArray()
                        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, userNames)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerUsers.adapter = adapter
                        
                        spinnerUsers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                currentUserId = users[position].id
                                loadMessages(currentUserId)
                            }
                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                        }
                    }
                }
            } catch (e: Exception) {
                tvChat.text = "Ошибка: ${e.message}"
            }
        }
    }
    
    private fun loadMessages(userId: Int) {
        lifecycleScope.launch {
            try {
                val token = authRepository.getToken()
                if (token != null) {
                    val response = RetrofitClient.instance.getMessages("Bearer $token", userId)
                    if (response.isSuccessful && response.body() != null) {
                        val messages = response.body()!!
                        tvChat.text = buildString {
                            appendLine("Чат с пользователем")
                            appendLine()
                            messages.takeLast(10).forEach { msg ->
                                val prefix = if (msg.isFromAdmin) "Админ: " else "${msg.userName}: "
                                appendLine("$prefix${msg.message}")
                                appendLine()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                tvChat.text = "Ошибка: ${e.message}"
            }
        }
    }
    
    private fun sendMessage(message: String) {
        lifecycleScope.launch {
            try {
                val token = authRepository.getToken()
                if (token != null) {
                    val request = SendMessageRequest(currentUserId, message)
                    val response = RetrofitClient.instance.sendMessage("Bearer $token", request)
                    if (response.isSuccessful && response.body()?.success == true) {
                        etMessage.text.clear()
                        Toast.makeText(requireContext(), "Отправлено", Toast.LENGTH_SHORT).show()
                        loadMessages(currentUserId)
                    } else {
                        Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
