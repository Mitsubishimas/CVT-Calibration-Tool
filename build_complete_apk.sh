#!/bin/bash

echo "🚀 Начинаем полную сборку APK..."
echo "================================="

# Проверяем Java
if ! command -v java &> /dev/null; then
    echo "📦 Устанавливаем Java..."
    sudo apt-get update
    sudo apt-get install -y openjdk-17-jdk
fi

# Даем права на выполнение
chmod +x gradlew

# Очищаем
echo "🧹 Очистка предыдущих сборок..."
./gradlew clean

# Проверяем наличие google-services.json
if [ ! -f "app/google-services.json" ]; then
    echo "❌ Ошибка: файл google-services.json не найден в папке app/"
    echo "Создаю файл из ваших данных..."
    
    cat > app/google-services.json << 'JSONEOF'
{
  "project_info": {
    "project_number": "136902505194",
    "project_id": "cvt-admin-panel",
    "storage_bucket": "cvt-admin-panel.firebasestorage.app"
  },
  "client": [
    {
      "client_info": {
        "mobilesdk_app_id": "1:136902505194:android:658a7654e03cc595601d93",
        "android_client_info": {
          "package_name": "com.cvt.admin"
        }
      },
      "oauth_client": [],
      "api_key": [
        {
          "current_key": "AIzaSyB-R3mhPALr6OdsKJXK-77rR1IV5SKElLA"
        }
      ],
      "services": {
        "appinvite_service": {
          "other_platform_oauth_client": []
        }
      }
    }
  ],
  "configuration_version": "1"
}
JSONEOF
    
    echo "✅ Файл создан"
fi

# Собираем Debug APK
echo "📦 Сборка Debug APK..."
./gradlew assembleDebug

if [ $? -eq 0 ]; then
    echo ""
    echo "✅✅✅ СБОРКА УСПЕШНО ЗАВЕРШЕНА! ✅✅✅"
    echo ""
    echo "📱 APK файл: app/build/outputs/apk/debug/app-debug.apk"
    
    # Копируем в корень
    cp app/build/outputs/apk/debug/app-debug.apk ./CVT-Admin-Panel-v1.0.apk
    
    echo ""
    echo "📁 Файл скопирован: ./CVT-Admin-Panel-v1.0.apk"
    echo "📊 Размер файла:"
    ls -lh ./CVT-Admin-Panel-v1.0.apk
    echo ""
    echo "🎉 Готово! Вы можете скачать APK через интерфейс Codespaces"
    echo "================================="
else
    echo ""
    echo "❌ Ошибка сборки. Проверьте логи выше."
    exit 1
fi
