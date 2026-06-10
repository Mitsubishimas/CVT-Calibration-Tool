package com.cvt.admin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cvt.admin.R

class SettingsFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        
        val textView: TextView = view.findViewById(R.id.text_settings)
        textView.text = "Настройки приложения\n\n- Смена пароля\n- Настройки уведомлений\n- О приложении"
        
        return view
    }
}
