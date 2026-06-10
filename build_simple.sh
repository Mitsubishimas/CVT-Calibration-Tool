#!/bin/bash

echo "🚀 Начинаем сборку APK..."

# Даем права
chmod +x gradlew

# Очищаем
echo "🧹 Очистка..."
./gradlew clean

# Собираем Debug APK
echo "📦 Сборка..."
./gradlew assembleDebug --stacktrace

if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    echo ""
    echo "✅ СБОРКА УСПЕШНА!"
    cp app/build/outputs/apk/debug/app-debug.apk ./CVT-Admin-App.apk
    echo "📱 APK: ./CVT-Admin-App.apk"
    ls -lh ./CVT-Admin-App.apk
else
    echo "❌ Ошибка сборки"
    exit 1
fi
