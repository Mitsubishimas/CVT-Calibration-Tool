package com.cvt.admin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cvt.admin.R

class ChatFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        
        val textView: TextView = view.findViewById(R.id.text_chat)
        textView.text = "Чат поддержки\n\nВыберите пользователя для начала чата"
        
        return view
    }
}
