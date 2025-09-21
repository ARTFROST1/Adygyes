# Настройка Yandex MapKit API

Для работы приложения Adygyes необходимо получить API ключ от Yandex MapKit.

## 🔑 Получение API ключа

### Шаг 1: Регистрация в Yandex Developer Console

1. Перейдите на [Yandex Developer Console](https://developer.tech.yandex.ru/)
2. Войдите в свой аккаунт Yandex или зарегистрируйтесь
3. Примите условия использования

### Шаг 2: Создание приложения

1. Нажмите **"Создать приложение"**
2. Заполните форму:
   - **Название**: `Adygyes Mobile App`
   - **Описание**: `Интерактивная карта достопримечательностей Адыгеи`
   - **Платформа**: `Android`
   - **Пакет приложения**: `com.adygyes`

### Шаг 3: Получение API ключа

1. После создания приложения перейдите в его настройки
2. В разделе **"MapKit Mobile SDK"** нажмите **"Получить ключ"**
3. Скопируйте полученный API ключ

### Шаг 4: Настройка в проекте

1. Откройте файл `app/src/main/AndroidManifest.xml`
2. Найдите строку:
```xml
<meta-data
    android:name="com.yandex.mapkit.ApiKey"
    android:value="YOUR_YANDEX_MAPKIT_API_KEY_HERE" />
```
3. Замените `YOUR_YANDEX_MAPKIT_API_KEY_HERE` на ваш реальный API ключ

### Шаг 5: Настройка ограничений (Рекомендуется)

Для безопасности рекомендуется настроить ограничения для API ключа:

1. В настройках приложения перейдите в раздел **"Ограничения"**
2. Добавьте ограничение по **SHA-1 отпечатку**:
   - Для debug версии используйте debug keystore
   - Для release версии используйте ваш production keystore

#### Получение SHA-1 отпечатка для debug:

```bash
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```

#### Получение SHA-1 отпечатка для release:

```bash
keytool -list -v -keystore path/to/your/keystore.jks -alias your_alias
```

## 🔒 Безопасность API ключа

### ⚠️ Важные правила безопасности:

1. **НЕ** коммитьте API ключ в публичный репозиторий
2. Используйте переменные окружения или gradle.properties для хранения ключа
3. Настройте ограничения для API ключа в Yandex Console
4. Регулярно ротируйте API ключи

### Рекомендуемый способ хранения:

1. Создайте файл `local.properties` в корне проекта (он уже в .gitignore):
```properties
yandex.mapkit.api.key=ВАШ_РЕАЛЬНЫЙ_API_КЛЮЧ
```

2. Обновите `build.gradle.kts` в модуле app:
```kotlin
android {
    defaultConfig {
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(FileInputStream(localPropertiesFile))
        }
        
        manifestPlaceholders["yandexMapKitApiKey"] = 
            localProperties.getProperty("yandex.mapkit.api.key", "YOUR_YANDEX_MAPKIT_API_KEY_HERE")
    }
}
```

3. Обновите AndroidManifest.xml:
```xml
<meta-data
    android:name="com.yandex.mapkit.ApiKey"
    android:value="${yandexMapKitApiKey}" />
```

## 📊 Лимиты и квоты

### Бесплатный план:
- **25,000** запросов в день
- **1,000** запросов в час
- Подходит для разработки и тестирования

### Коммерческий план:
- Увеличенные лимиты
- Техническая поддержка
- SLA гарантии

## 🔧 Тестирование настройки

После настройки API ключа:

1. Запустите приложение
2. Проверьте, что карта загружается корректно
3. Убедитесь, что нет ошибок в логах связанных с API ключом

### Возможные ошибки:

- **"Invalid API key"** - проверьте правильность ключа
- **"API key restrictions"** - проверьте настройки ограничений
- **"Quota exceeded"** - превышен лимит запросов

## 📚 Дополнительные ресурсы

- [Документация Yandex MapKit](https://yandex.com/maps-api/docs/mapkit/)
- [Android SDK документация](https://yandex.com/maps-api/docs/mapkit/android/)
- [Примеры кода](https://github.com/yandex/mapkit-android-demo)
- [FAQ по MapKit](https://yandex.com/maps-api/docs/mapkit/faq/)

## 🆘 Поддержка

Если у вас возникли проблемы с настройкой API ключа:

1. Проверьте [FAQ](https://yandex.com/maps-api/docs/mapkit/faq/)
2. Обратитесь в [техническую поддержку Yandex](https://yandex.com/support/maps-api/)
3. Создайте issue в репозитории проекта

---

**Примечание**: API ключ необходим для работы всех функций карты в приложении Adygyes.
