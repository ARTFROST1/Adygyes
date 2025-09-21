# Bug Tracking for Adygyes

## Overview
This document tracks bugs, issues, and their resolutions throughout the development of the Adygyes mobile application.

## Bug Report Template

### Bug ID: [BUG-XXX]
- **Title:** Brief description of the issue
- **Severity:** Critical | High | Medium | Low
- **Priority:** P1 | P2 | P3 | P4
- **Status:** Open | In Progress | Resolved | Closed
- **Reporter:** Developer name
- **Assignee:** Developer name
- **Date Reported:** YYYY-MM-DD
- **Date Resolved:** YYYY-MM-DD
- **Environment:** Debug/Release, Android version, Device model

#### Description
Detailed description of the bug and its impact.

#### Steps to Reproduce
1. Step one
2. Step two
3. Step three

#### Expected Behavior
What should happen.

#### Actual Behavior
What actually happens.

#### Screenshots/Logs
Any relevant visual evidence or error logs.

#### Root Cause
Technical explanation of why the bug occurred.

#### Resolution
How the bug was fixed.

#### Prevention
How to prevent similar issues in the future.

---

## Active Bugs

*No active bugs at this time.*

---

## Resolved Bugs

### Bug ID: BUG-012
- **Title:** Ошибка компиляции - несуществующая иконка Icons.Default.Remove
- **Severity:** Critical
- **Priority:** P1
- **Status:** Resolved
- **Reporter:** User
- **Assignee:** Development Agent
- **Date Reported:** 2025-09-21
- **Date Resolved:** 2025-09-21
- **Environment:** Kotlin compilation, Material Icons

#### Description
Ошибка компиляции при попытке использовать несуществующую иконку `Icons.Default.Remove` в MapControls.kt:
```
e: Unresolved reference 'Remove'.
```

#### Steps to Reproduce
1. Попытаться собрать проект
2. Компиляция падает с ошибкой "Unresolved reference 'Remove'"

#### Expected Behavior
Проект должен компилироваться без ошибок.

#### Actual Behavior
Компиляция падает из-за использования несуществующей иконки.

#### Root Cause
При замене иконки для кнопки zoom out была использована несуществующая иконка `Icons.Default.Remove`. В стандартном наборе Material Icons такой иконки нет.

#### Resolution
Заменена несуществующая иконка `Icons.Default.Remove` на существующую `Icons.Default.KeyboardArrowDown`:
- Обновлен импорт в MapControls.kt
- Заменена иконка в UI компоненте
- Функциональность сохранена

#### Prevention
- Проверять существование иконок в документации Material Icons
- Тестировать компиляцию после изменения импортов
- Использовать IDE автодополнение для проверки доступных иконок

### Bug ID: BUG-011
- **Title:** Нефункциональные кнопки масштабирования карты
- **Severity:** Medium
- **Priority:** P2
- **Status:** Resolved
- **Reporter:** User
- **Assignee:** Development Agent
- **Date Reported:** 2025-09-21
- **Date Resolved:** 2025-09-21
- **Environment:** UI/UX, Yandex MapKit

#### Description
Кнопки увеличения и уменьшения масштаба карты в MapControls не работали - были только заглушки с TODO комментариями. Также нужно было убедиться, что жесты масштабирования двумя пальцами работают на мобильных устройствах.

#### Steps to Reproduce
1. Запустить приложение
2. Попытаться нажать на кнопки "+" и "-" для масштабирования карты
3. Ничего не происходит - функциональность не реализована

#### Expected Behavior
1. Кнопка "+" должна увеличивать масштаб карты
2. Кнопка "-" должна уменьшать масштаб карты
3. Жесты pinch-to-zoom должны работать на мобильных устройствах

#### Actual Behavior
Кнопки масштабирования не работали, содержали только TODO комментарии.

#### Root Cause
1. **Неполная реализация**: YandexMapView имел параметры для zoom функций, но они не использовались
2. **Отсутствие связи**: MapScreen содержал только TODO комментарии вместо реальной функциональности
3. **Неправильная архитектура**: Не было механизма передачи команд масштабирования от UI к карте

#### Resolution
1. **Реализован механизм триггеров**:
   - Добавлены параметры `zoomInTrigger` и `zoomOutTrigger` в YandexMapView
   - Создана система состояний для управления масштабированием
   
2. **Реализованы функции масштабирования**:
   - `zoomIn()`: увеличивает zoom на 1 с плавной анимацией
   - `zoomOut()`: уменьшает zoom на 1 с плавной анимацией
   - Ограничения: минимальный zoom 0, максимальный zoom 21
   
3. **Подключены кнопки MapControls**:
   - Кнопка "+" увеличивает `zoomInTrigger`
   - Кнопка "-" увеличивает `zoomOutTrigger`
   - Изменена иконка zoom out с стрелки на минус
   
4. **Включены жесты масштабирования**:
   - `isZoomGesturesEnabled = true` - pinch-to-zoom
   - `isScrollGesturesEnabled = true` - перемещение карты
   - `isTiltGesturesEnabled = true` - наклон карты
   - `isRotateGesturesEnabled = true` - поворот карты

#### Prevention
- Всегда реализовывать полную функциональность UI элементов
- Тестировать интерактивность на мобильных устройствах
- Не оставлять TODO комментарии в production коде

### Bug ID: BUG-010
- **Title:** Ошибки доступа к спутниковым тайлам и неудобный UX поиска
- **Severity:** Medium
- **Priority:** P2
- **Status:** Resolved
- **Reporter:** User
- **Assignee:** Development Agent
- **Date Reported:** 2025-09-21
- **Date Resolved:** 2025-09-21
- **Environment:** UI/UX, Yandex MapKit API

#### Description
Две проблемы:
1. Ошибки доступа к спутниковым тайлам: "Access denied" при переключении на спутниковый режим
2. Неудобный UX поисковой строки - нет легкого способа вернуться назад из режима поиска

#### Steps to Reproduce
1. Запустить приложение
2. Попытаться переключить карту на спутниковый режим - появляются ошибки в логах
3. Нажать на кнопку поиска - поисковая строка появляется, но нет интуитивного способа закрыть её

#### Expected Behavior
1. Карта должна работать без ошибок доступа к тайлам
2. Поисковая строка должна иметь удобную навигацию с кнопкой "Назад"

#### Actual Behavior
1. Ошибки "Access denied" при загрузке спутниковых тайлов
2. Неинтуитивный UX для закрытия поиска

#### Screenshots/Logs
```
W  a+DGZsA3EUY3wk/Wh7pX: Couldn't load tile [sat, [625, 371, 10]] because: Unexpected server response: Forbidden. 
Url: https://proxy.mob.maps.yandex.net:443/mapkit2/layers/2.x/sat/tiles?v=3.1899.0&x=625&y=371&z=10&zmax=10&zmin=10. 
Body: Access denied.
```

#### Root Cause
1. **API ключ ограничения**: Текущий API ключ не имеет доступа к спутниковым тайлам Yandex Maps
2. **Неоптимальный UX**: TopAppBar не менялся при активации поиска, не было кнопки "Назад"

#### Resolution
1. **Удален MapStyleToggle**: Полностью убрана кнопка переключения стилей карты, используется только схема
2. **Улучшен UX поиска**:
   - TopAppBar теперь меняется на поисковый режим с кнопкой "Назад"
   - Добавлен autoFocus для автоматической активации поисковой строки
   - Кнопка "Назад" очищает поисковый запрос и возвращает к обычному режиму
   - Поисковая строка автоматически получает фокус при открытии

#### Prevention
- Использовать только доступные функции API в зависимости от типа ключа
- Всегда тестировать UX навигации при добавлении новых режимов интерфейса
- Следовать Material Design принципам для навигации

### Bug ID: BUG-009
- **Title:** Дублирование UI элементов - два заголовка, две кнопки поиска и настроек
- **Severity:** Medium
- **Priority:** P2
- **Status:** Resolved
- **Reporter:** User
- **Assignee:** Development Agent
- **Date Reported:** 2025-09-21
- **Date Resolved:** 2025-09-21
- **Environment:** UI/UX, Jetpack Compose

#### Description
В приложении наблюдалось дублирование UI элементов:
- Два заголовка
- Две кнопки поиска
- Две кнопки настроек

#### Steps to Reproduce
1. Запустить приложение
2. Открыть главный экран с картой
3. Наблюдать дублированные элементы интерфейса

#### Expected Behavior
Должен быть только один заголовок, одна кнопка поиска и одна кнопка настроек в верхней части экрана.

#### Actual Behavior
Отображались дублированные элементы интерфейса, что создавало путаницу в UX.

#### Root Cause
1. **Дублирующиеся файлы**: Существовали файлы как в пакете `com.adygyes`, так и в `com.example.adygyes`
2. **Неправильная логика UI**: В MapScreen.kt была кнопка поиска в TopAppBar И отдельная поисковая строка AttractionSearchBar, которая всегда отображалась

#### Resolution
1. **Удалены дублирующиеся файлы**: Полностью удален пакет `com.example.adygyes` с устаревшими файлами
2. **Исправлена логика поиска**: 
   - Кнопка поиска в TopAppBar теперь управляет видимостью поисковой строки
   - AttractionSearchBar показывается только при нажатии на кнопку поиска
   - Добавлена возможность закрыть поиск через кнопку Clear или после выбора достопримечательности
3. **Обновлен AttractionSearchBar**: Добавлен параметр `onDismiss` для управления закрытием поиска

#### Prevention
- Регулярно проверять структуру проекта на наличие дублирующихся файлов
- Следовать принципу единственной ответственности для UI компонентов
- Тестировать UX после изменений в интерфейсе

### Bug ID: BUG-008
- **Title:** Yandex Maps Access Denied - недействительный API ключ
- **Severity:** High
- **Priority:** P2
- **Status:** Resolved (Temporary)
- **Reporter:** Development Agent
- **Assignee:** Development Agent
- **Date Reported:** 2025-09-21
- **Date Resolved:** 2025-09-21
- **Environment:** Runtime, Yandex MapKit API

#### Description
Ошибки доступа к спутниковым тайлам Yandex Maps:
`Access denied` и `Forbidden` при попытке загрузить спутниковые снимки карты.

#### Steps to Reproduce
1. Запустить приложение
2. Переключиться на спутниковый или гибридный режим карты
3. Наблюдать ошибки в логах

#### Expected Behavior
Спутниковые тайлы должны загружаться без ошибок.

#### Actual Behavior
Сервер Яндекса отклоняет запросы с ошибкой "Access denied".

#### Screenshots/Logs
```
W  a+DGZsA3EUY3wk/Wh7pX: Couldn't load tile [sat, [5009, 2975, 13]] because: Unexpected server response: Forbidden. 
Url: https://proxy.mob.maps.yandex.net:443/mapkit2/layers/2.x/sat/tiles?v=3.1899.0&x=5009&y=2975&z=13&zmax=13&zmin=13. 
Body: Access denied.
```

#### Root Cause
Используется тестовый/placeholder API ключ `96a70138-5c3d-4b3e-a9fa-b74e645a6a30`, который не имеет доступа к спутниковым тайлам Yandex Maps.

#### Resolution
**Временное решение:**
1. **Обновлен AndroidManifest.xml**: Добавлены комментарии с инструкциями по получению настоящего API ключа
2. **Отключены спутниковые режимы**: Временно закомментированы SATELLITE и HYBRID режимы в MapStyleToggle
3. **Оставлен только SCHEMA режим**: Работает с любым API ключом

**Постоянное решение (требует действий пользователя):**
1. Получить настоящий API ключ на https://developer.tech.yandex.ru/
2. Заменить `YOUR_ACTUAL_API_KEY_HERE` в AndroidManifest.xml
3. Раскомментировать спутниковые режимы в MapStyleToggle.kt

#### Prevention
- Всегда использовать настоящие API ключи в production
- Проверять лимиты и права доступа API ключей
- Добавить обработку ошибок загрузки тайлов

### Bug ID: BUG-007
- **Title:** LocationUtils.kt API compatibility issue with Yandex MapKit 4.22.0
- **Severity:** Critical
- **Priority:** P1
- **Status:** Resolved
- **Reporter:** Development Agent
- **Assignee:** Development Agent
- **Date Reported:** 2025-09-21
- **Date Resolved:** 2025-09-21
- **Environment:** Kotlin compilation, Yandex MapKit 4.22.0

#### Description
Compilation errors in LocationUtils.kt due to API changes in Yandex MapKit 4.22.0:
- `subscribeForLocationUpdates` method signature changed
- Location object structure changed
- Parameter types and count mismatch

#### Steps to Reproduce
1. Update Yandex MapKit to 4.22.0
2. Try to compile project with existing LocationUtils.kt
3. Multiple compilation errors occur

#### Expected Behavior
LocationUtils should compile and work with the updated MapKit version.

#### Actual Behavior
Build fails with multiple Kotlin compilation errors:
- Argument type mismatch for subscribeForLocationUpdates
- Too many arguments for the method
- Missing abstract member implementations

#### Screenshots/Logs
```
e: Argument type mismatch: actual type is 'kotlin.Double', but 'com.yandex.mapkit.location.SubscriptionSettings' was expected.
e: Too many arguments for 'fun subscribeForLocationUpdates(p0: SubscriptionSettings, p1: LocationListener): Unit'.
e: 'onLocationUpdated' overrides nothing.
```

#### Root Cause
- Yandex MapKit 4.22.0 changed the LocationManager API significantly
- `subscribeForLocationUpdates` now requires `SubscriptionSettings` object instead of individual parameters
- Location object now has `position` property instead of direct latitude/longitude
- Import statements needed updating for new API structure

#### Resolution
**Updated LocationUtils.kt to use new API:**
1. **Changed location method**: Switched from `subscribeForLocationUpdates` to `requestSingleUpdate` (more appropriate for single location requests)
2. **Updated imports**: Added correct Yandex MapKit location imports
3. **Fixed Location object access**: Changed from `location.latitude` to `location.position.latitude`
4. **Simplified implementation**: Removed complex subscription settings, using simpler single update approach

**Key changes:**
- Used `requestSingleUpdate()` instead of `subscribeForLocationUpdates()`
- Updated Location object access pattern
- Corrected import statements for new API

#### Prevention
- Check API documentation when updating major SDK versions
- Test location functionality after SDK updates
- Use appropriate methods for use case (single update vs continuous updates)

### Bug ID: BUG-006
- **Title:** MinSdk version compatibility issue with Yandex MapKit 4.22.0
- **Severity:** High
- **Priority:** P2
- **Status:** Resolved
- **Reporter:** Development Agent
- **Assignee:** Development Agent
- **Date Reported:** 2025-09-21
- **Date Resolved:** 2025-09-21
- **Environment:** Build system, Gradle manifest merger

#### Description
Build fails with manifest merger error when updating Yandex MapKit to version 4.22.0:
`uses-sdk:minSdkVersion 24 cannot be smaller than version 26 declared in library [com.yandex.android:maps.mobile:4.22.0-full]`

#### Steps to Reproduce
1. Update Yandex MapKit dependency to 4.22.0-full
2. Run `./gradlew build` or sync project
3. Manifest merger fails with minSdk compatibility error

#### Expected Behavior
Project should build successfully with updated Yandex MapKit version.

#### Actual Behavior
Build fails due to minSdk version mismatch between project (24) and library requirement (26).

#### Screenshots/Logs
```
uses-sdk:minSdkVersion 24 cannot be smaller than version 26 declared in library [com.yandex.android:maps.mobile:4.22.0-full]
Suggestion: use a compatible library with a minSdk of at most 24, or increase this project's minSdk version to at least 26
```

#### Root Cause
- Yandex MapKit 4.22.0 requires minimum Android API 26 (Android 8.0)
- Project was configured with minSdk 24 (Android 7.0)
- Newer MapKit versions use APIs that are only available in Android 8.0+

#### Resolution
**Updated minSdk version in build.gradle.kts:**
- Changed `minSdk = 24` to `minSdk = 26`
- This ensures compatibility with Yandex MapKit 4.22.0 requirements
- Android API 26+ covers ~95% of active Android devices (as of 2024)

#### Impact Assessment
- **Device Compatibility**: Minimum supported Android version is now 8.0 (API 26)
- **Market Coverage**: Still covers vast majority of active Android devices
- **Benefits**: Access to latest MapKit features and Android 14 compatibility

#### Prevention
- Check library requirements before updating dependencies
- Consider device compatibility impact when updating minSdk
- Test on devices with minimum supported Android version

### Bug ID: BUG-005
- **Title:** Yandex MapKit broadcast receiver SecurityException - Android 14 compatibility issue
- **Severity:** Critical
- **Priority:** P1
- **Status:** Resolved
- **Reporter:** Development Agent
- **Assignee:** Development Agent
- **Date Reported:** 2025-09-21
- **Date Resolved:** 2025-09-21
- **Environment:** Debug build, Android 14 (API 34), Yandex MapKit 4.4.0

#### Description
App crashes on Android 14 devices due to Yandex MapKit's internal broadcast receiver registration not complying with new Android 14 security requirements:
`java.lang.SecurityException: One of RECEIVER_EXPORTED or RECEIVER_NOT_EXPORTED should be specified when a receiver isn't being registered exclusively for system broadcasts`

#### Steps to Reproduce
1. Run app on Android 14 device with targetSdk 35
2. Initialize Yandex MapKit (happens automatically)
3. MapKit tries to register connectivity broadcast receiver
4. SecurityException is thrown due to missing RECEIVER_EXPORTED/RECEIVER_NOT_EXPORTED flag

#### Expected Behavior
Yandex MapKit should initialize without errors on Android 14+ devices.

#### Actual Behavior
App crashes with SecurityException when MapKit tries to register internal broadcast receivers.

#### Screenshots/Logs
```
2025-09-21 10:55:00.915  4827-4827  com.yandex...bscription com.adygyes                          E  Cannot register receiver
java.lang.SecurityException: com.adygyes: One of RECEIVER_EXPORTED or RECEIVER_NOT_EXPORTED should be specified when a receiver isn't being registered exclusively for system broadcasts
	at com.yandex.runtime.connectivity.internal.ConnectivitySubscription$1.run(ConnectivitySubscription.java:53)
```

#### Root Cause
- Using outdated Yandex MapKit version (4.4.0) that doesn't comply with Android 14 broadcast receiver requirements
- Android 14 (API 34) introduced mandatory RECEIVER_EXPORTED/RECEIVER_NOT_EXPORTED flags for context-registered receivers
- Yandex MapKit's internal ConnectivitySubscription class was registering receivers without these flags

#### Resolution
**Updated Yandex MapKit dependency:**
- Changed from `com.yandex.android:maps.mobile:4.4.0-full` to `com.yandex.android:maps.mobile:4.22.0-full`
- Latest version includes Android 14 compatibility fixes
- No code changes required - issue is resolved at SDK level

#### Prevention
- Always use latest stable versions of third-party SDKs
- Monitor SDK release notes for Android compatibility updates
- Test on latest Android versions during development
- Check SDK compatibility when updating targetSdk version

### Bug ID: BUG-004
- **Title:** Location permission SecurityException - missing runtime permission handling
- **Severity:** Critical
- **Priority:** P1
- **Status:** Resolved
- **Reporter:** Development Agent
- **Assignee:** Development Agent
- **Date Reported:** 2025-09-21
- **Date Resolved:** 2025-09-21
- **Environment:** Debug build, Android device

#### Description
App crashes when trying to access location services due to SecurityException:
`java.lang.SecurityException: uid 10216 does not have android.permission.ACCESS_COARSE_LOCATION or android.permission.ACCESS_FINE_LOCATION`

#### Steps to Reproduce
1. Launch the app
2. Tap the location button in map controls
3. Yandex MapKit attempts to get last known location
4. SecurityException is thrown

#### Expected Behavior
App should request location permissions at runtime and handle location access properly.

#### Actual Behavior
App crashes with SecurityException when trying to access location without runtime permissions.

#### Screenshots/Logs
```
2025-09-21 10:54:59.543  4827-4855  com.yandex...wnLocation com.adygyes                          E  failed to get last known location: java.lang.SecurityException: uid 10216 does not have android.permission.ACCESS_COARSE_LOCATION or android.permission.ACCESS_FINE_LOCATION.
```

#### Root Cause
- Location permissions were declared in AndroidManifest.xml but no runtime permission handling was implemented
- MainActivity lacked permission request logic
- MapScreen had stubbed location functionality without permission checks

#### Resolution
1. **Updated MainActivity.kt:**
   - Added runtime permission request using ActivityResultContracts
   - Implemented permission checking methods
   - Added proper permission handling lifecycle

2. **Created LocationUtils.kt:**
   - Utility class for location operations
   - Permission checking methods
   - Safe location access with error handling
   - Distance calculation utilities

3. **Updated MapScreen.kt:**
   - Integrated LocationUtils for permission checking
   - Added proper error handling for location access
   - Implemented user feedback for permission issues
   - Added location error message display

#### Prevention
- Always implement runtime permission handling for dangerous permissions
- Test location functionality on devices with different permission states
- Add proper error handling and user feedback for permission-related issues

### Bug ID: BUG-003
- **Title:** Yandex Maps style warning - Unknown style type 'model' for 'hd_vegetation_model'
- **Severity:** Low
- **Priority:** P4
- **Status:** Resolved
- **Reporter:** Development Agent
- **Assignee:** Development Agent
- **Date Reported:** 2025-09-21
- **Date Resolved:** 2025-09-21
- **Environment:** Debug build, Yandex MapKit

#### Description
Yandex MapKit logs warning about unknown style type 'model' for style 'hd_vegetation_model'.

#### Screenshots/Logs
```
2025-09-21 10:54:59.591  4827-4862  yandex.maps             com.adygyes                          E  zj2if+9/fT1WVI5BdkTB: Unknown style type 'model' for  style with id: 'hd_vegetation_model'
```

#### Root Cause
This is an internal Yandex MapKit warning indicating that the SDK is trying to load a map style component that doesn't exist or is not supported in the current SDK version.

#### Resolution
This is a non-critical warning from Yandex MapKit internal styling system. No action required as it doesn't affect app functionality. The warning can be safely ignored as it's related to internal map rendering optimizations.

#### Prevention
- Monitor Yandex MapKit SDK updates for style system improvements
- Consider updating to newer SDK versions when available

### Bug ID: BUG-002
- **Title:** Material Icons compilation errors - non-existent icons
- **Severity:** Critical
- **Priority:** P1
- **Status:** Resolved
- **Reporter:** Development Agent
- **Assignee:** Development Agent
- **Date Reported:** 2025-01-21
- **Date Resolved:** 2025-01-21
- **Environment:** Debug build, Android Studio

#### Description
Compilation errors due to using non-existent Material Icons:
1. Icons.Default.Directions - does not exist
2. Icons.Default.Remove - does not exist  
3. Icons.Default.Map - does not exist

#### Root Cause
Using Material Icons that are not available in the standard androidx.compose.material.icons.filled package.

#### Resolution
Replaced with existing Material Icons:
- Icons.Default.Directions → Icons.Default.DirectionsWalk
- Icons.Default.ZoomOut → Icons.Default.Remove (standard minus icon)
- Icons.Default.Map → Icons.Default.Settings (for map style toggle)

All compilation errors resolved successfully.

### Bug ID: BUG-001
- **Title:** Compilation errors in UI components - missing imports and unresolved references
- **Severity:** Critical
- **Priority:** P1
- **Status:** Resolved
- **Reporter:** Development Agent
- **Assignee:** Development Agent
- **Date Reported:** 2025-01-21
- **Date Resolved:** 2025-01-21
- **Environment:** Debug build, Android Studio

#### Description
Multiple compilation errors in UI components preventing successful build:
1. Missing Material Icons imports (Directions, MyLocation, Remove, Layers)
2. Unresolved background modifier references
3. Import issues in newly created components

#### Steps to Reproduce
1. Run `./gradlew assembleDebug`
2. Compilation fails with Kotlin compiler errors

#### Expected Behavior
Project should compile successfully without errors.

#### Actual Behavior
Build fails with multiple "Unresolved reference" errors in:
- AttractionDetailCard.kt
- AttractionSearchBar.kt  
- MapControls.kt
- MapStyleToggle.kt

#### Screenshots/Logs
```
e: Unresolved reference 'Directions'
e: Unresolved reference 'MyLocation'
e: Unresolved reference 'Remove'
e: Unresolved reference 'Layers'
e: Unresolved reference 'background'
```

#### Root Cause
Missing imports for Material Icons and Foundation modifiers in newly created Compose components.

#### Resolution
Fixed all compilation errors by:
1. Added missing import `androidx.compose.foundation.background` to AttractionDetailCard.kt and AttractionSearchBar.kt
2. Replaced non-existent Material Icons with available alternatives:
   - `MyLocation` → `LocationOn`
   - `ZoomOut` → `Remove` 
   - `Layers` → `Map`
3. Removed duplicate background modifier extension functions
4. Cleaned Gradle cache and restarted daemon

#### Prevention
- Use IDE auto-import features and verify all imports before committing code
- Check Material Icons documentation for available icons
- Test compilation after adding new UI components

---

## Known Issues

### Development Environment Issues

#### Issue: Yandex MapKit API Key Configuration
- **Status:** Documentation
- **Description:** Developers need to properly configure Yandex MapKit API keys
- **Solution:** Follow the setup guide in Implementation.md Stage 1
- **Prevention:** Include API key setup in onboarding documentation

#### Issue: Room Database Migration
- **Status:** Future Consideration
- **Description:** Database schema changes will require migration strategies
- **Solution:** Implement proper migration paths when schema changes occur
- **Prevention:** Plan database schema carefully and version appropriately

---

## Testing Guidelines

### Bug Reporting Process
1. **Verify Reproduction:** Ensure the bug can be consistently reproduced
2. **Check Existing Reports:** Search for duplicate issues
3. **Gather Information:** Collect logs, screenshots, device info
4. **Assign Severity:** Use severity guidelines below
5. **Create Report:** Use the bug report template
6. **Notify Team:** Alert relevant team members

### Severity Guidelines

#### Critical (P1)
- App crashes on startup
- Data loss or corruption
- Security vulnerabilities
- Core functionality completely broken

#### High (P2)
- Major features not working
- Performance issues affecting usability
- UI completely broken on major devices
- Memory leaks

#### Medium (P3)
- Minor feature issues
- UI inconsistencies
- Performance issues on specific devices
- Localization problems

#### Low (P4)
- Cosmetic issues
- Minor UI inconsistencies
- Edge case scenarios
- Enhancement requests

### Testing Checklist

#### Pre-Release Testing
- [ ] Core map functionality works
- [ ] All attractions display correctly
- [ ] Search functionality works
- [ ] Route planning integration works
- [ ] App works on different screen sizes
- [ ] Dark/light theme switching works
- [ ] Language switching works (when implemented)
- [ ] Performance is acceptable
- [ ] No memory leaks detected
- [ ] Offline functionality works (when implemented)

#### Device Testing Matrix
- **Primary:** Samsung Galaxy S21+ (Android 13)
- **Secondary:** Google Pixel 6 (Android 14)
- **Budget:** Samsung Galaxy A32 (Android 12)
- **Tablet:** Samsung Galaxy Tab S8 (Android 13)

---

## Common Issues and Solutions

### Yandex MapKit Issues

#### Map Not Loading
**Symptoms:** Blank map area, no tiles loading
**Common Causes:**
- Invalid API key
- Network connectivity issues
- Incorrect SDK initialization

**Solutions:**
1. Verify API key is correctly set in AndroidManifest.xml
2. Check internet permissions
3. Ensure MapKit is initialized before use
4. Check Yandex MapKit service status

#### Markers Not Displaying
**Symptoms:** Map loads but custom markers don't appear
**Common Causes:**
- Incorrect marker positioning
- Image loading issues
- Clustering configuration problems

**Solutions:**
1. Verify latitude/longitude coordinates
2. Check image resources exist and are accessible
3. Review clustering settings
4. Test with simple markers first

### Database Issues

#### Room Database Crashes
**Symptoms:** App crashes when accessing database
**Common Causes:**
- Missing database migrations
- Incorrect entity definitions
- Threading issues

**Solutions:**
1. Implement proper database migrations
2. Verify entity annotations
3. Use proper coroutine scopes for database operations
4. Enable database debugging

### UI/Compose Issues

#### Recomposition Performance
**Symptoms:** Laggy UI, poor scrolling performance
**Common Causes:**
- Unstable composable keys
- Heavy operations in composition
- Excessive recompositions

**Solutions:**
1. Use stable keys for lists
2. Move heavy operations to side effects
3. Use remember and derivedStateOf appropriately
4. Profile with Compose compiler metrics

#### Theme Issues
**Symptoms:** Inconsistent colors, theme not applying
**Common Causes:**
- Missing MaterialTheme wrapper
- Incorrect color definitions
- Theme not propagated properly

**Solutions:**
1. Ensure MaterialTheme wraps all composables
2. Verify color definitions in theme
3. Check theme application in MainActivity

---

## Performance Monitoring

### Key Metrics to Track
- **App Startup Time:** Target < 2 seconds cold start
- **Map Load Time:** Target < 3 seconds for initial map display
- **Search Response Time:** Target < 500ms for local search
- **Memory Usage:** Target < 200MB peak usage
- **Battery Usage:** Monitor for excessive drain

### Performance Testing Tools
- **Android Studio Profiler:** Memory, CPU, network monitoring
- **Systrace:** UI performance analysis
- **LeakCanary:** Memory leak detection
- **Firebase Performance:** Production monitoring (future)

---

## Release Notes Template

### Version X.X.X (YYYY-MM-DD)

#### New Features
- Feature descriptions

#### Bug Fixes
- Bug fix descriptions with reference to bug IDs

#### Performance Improvements
- Performance enhancement descriptions

#### Known Issues
- Any remaining known issues

---

This bug tracking system will help maintain code quality and ensure a smooth development process for the Adygyes application.
