package com.cvt.admin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cvt.admin.R

class OrdersFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_orders, container, false)
        
        val textView: TextView = view.findViewById(R.id.text_orders)
        textView.text = "Управление заказами\n\n- Калибровка CVT8\n- Активация программ\n- Покупка ПО"
        
        return view
    }
}
