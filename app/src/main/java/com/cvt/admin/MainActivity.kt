package com.cvt.admin

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val textView = TextView(this)
        textView.text = """
            CVT Admin Panel
            
            ✅ Приложение успешно запущено!
            
            📊 Функционал:
            • Панель управления
            • Управление заказами
            • Чат поддержки
            • Управление пользователями
            • Генерация ключей
            • Push-уведомления
            
            Версия 1.0
        """.trimIndent()
        textView.textSize = 18f
        textView.gravity = android.view.Gravity.CENTER
        textView.setTextColor(0xFF00D2FF.toInt())
        textView.setBackgroundColor(0xFF0f0c29.toInt())
        textView.setPadding(32, 32, 32, 32)
        
        setContentView(textView)
    }
}
