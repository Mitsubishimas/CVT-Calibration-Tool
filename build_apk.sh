#!/bin/bash

echo "🚀 Сборка APK"

# Устанавливаем Java 11
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Проверяем Java
echo "Java version:"
java -version

# Даем права
chmod +x gradlew

# Очищаем
echo "🧹 Очистка..."
./gradlew clean

# Собираем
echo "📦 Сборка Debug APK..."
./gradlew assembleDebug

if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    echo ""
    echo "✅✅✅ УСПЕХ! ✅✅✅"
    cp app/build/outputs/apk/debug/app-debug.apk ./CVT-Admin-App.apk
    echo ""
    echo "📱 APK готов: ./CVT-Admin-App.apk"
    ls -lh ./CVT-Admin-App.apk
    echo ""
    echo "Скачайте файл через интерфейс Codespaces"
else
    echo ""
    echo "❌ Ошибка сборки"
    echo "Лог ошибок:"
    ./gradlew assembleDebug --stacktrace 2>&1 | tail -30
    exit 1
fi
