package com.cvt.admin.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cvt.admin.MainActivity
import com.cvt.admin.R
import com.cvt.admin.data.repository.AuthRepository

class SettingsFragment : Fragment() {
    
    private lateinit var authRepository: AuthRepository
    private lateinit var etServerUrl: EditText
    private lateinit var btnLogout: Button
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        
        etServerUrl = view.findViewById(R.id.et_server_url)
        btnLogout = view.findViewById(R.id.btn_logout)
        
        authRepository = AuthRepository(null!!, requireContext())
        
        // Загружаем сохраненный URL сервера
        val prefs = requireContext().getSharedPreferences("cvt_prefs", Context.MODE_PRIVATE)
        etServerUrl.setText(prefs.getString("server_url", "https://mastermitsu.ru/api/"))
        
        btnLogout.setOnClickListener {
            authRepository.logout()
            Toast.makeText(requireContext(), "Вы вышли из системы", Toast.LENGTH_SHORT).show()
            
            // Возвращаемся на экран логина
            val intent = Intent(requireContext(), com.cvt.admin.ui.auth.LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        
        return view
    }
}
