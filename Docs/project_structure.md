# Project Structure for Adygyes

## Root Directory Structure

```
Adygyes/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/adygyes/
│   │   │   │   ├── data/
│   │   │   │   │   ├── database/
│   │   │   │   │   │   ├── entities/
│   │   │   │   │   │   ├── dao/
│   │   │   │   │   │   └── AppDatabase.kt
│   │   │   │   │   ├── repository/
│   │   │   │   │   ├── network/
│   │   │   │   │   └── models/
│   │   │   │   ├── domain/
│   │   │   │   │   ├── models/
│   │   │   │   │   ├── repository/
│   │   │   │   │   └── usecases/
│   │   │   │   ├── presentation/
│   │   │   │   │   ├── ui/
│   │   │   │   │   │   ├── components/
│   │   │   │   │   │   ├── screens/
│   │   │   │   │   │   │   ├── map/
│   │   │   │   │   │   │   ├── attraction/
│   │   │   │   │   │   │   ├── search/
│   │   │   │   │   │   │   └── settings/
│   │   │   │   │   │   └── theme/
│   │   │   │   │   ├── viewmodels/
│   │   │   │   │   └── navigation/
│   │   │   │   ├── di/
│   │   │   │   └── utils/
│   │   │   ├── res/
│   │   │   │   ├── drawable/
│   │   │   │   ├── mipmap/
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── strings-ru.xml
│   │   │   │   │   ├── strings-en.xml
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── themes.xml
│   │   │   │   │   └── dimens.xml
│   │   │   │   └── raw/
│   │   │   │       └── attractions_data.json
│   │   │   └── AndroidManifest.xml
│   │   ├── test/
│   │   └── androidTest/
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── Docs/
│   ├── Implementation.md
│   ├── project_structure.md
│   ├── UI_UX_doc.md
│   └── Bug_tracking.md
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Detailed Module Structure

### Data Layer (`app/src/main/java/com/adygyes/data/`)

#### Database Module (`database/`)
- **`entities/`** - Room database entities
  - `AttractionEntity.kt` - Main attraction data model
  - `CategoryEntity.kt` - Attraction categories
  - `PhotoEntity.kt` - Attraction photos
  - `ReviewEntity.kt` - Future reviews data

- **`dao/`** - Data Access Objects
  - `AttractionDao.kt` - Attraction CRUD operations
  - `CategoryDao.kt` - Category operations
  - `PhotoDao.kt` - Photo operations

- **`AppDatabase.kt`** - Room database configuration

#### Repository Module (`repository/`)
- `AttractionRepositoryImpl.kt` - Implementation of attraction repository
- `MapRepositoryImpl.kt` - Map-related data operations
- `SettingsRepositoryImpl.kt` - User preferences

#### Network Module (`network/`)
- `YandexApiService.kt` - Future API integrations
- `NetworkModule.kt` - Network configuration
- `ApiModels.kt` - Network response models

#### Models (`models/`)
- Data transfer objects and response models

### Domain Layer (`app/src/main/java/com/adygyes/domain/`)

#### Models (`models/`)
- `Attraction.kt` - Core attraction domain model
- `Category.kt` - Attraction category model
- `Location.kt` - Geographic location model
- `Route.kt` - Navigation route model

#### Repository Interfaces (`repository/`)
- `AttractionRepository.kt` - Attraction data contract
- `MapRepository.kt` - Map operations contract
- `SettingsRepository.kt` - Settings contract

#### Use Cases (`usecases/`)
- `GetAttractionsUseCase.kt` - Fetch attractions
- `SearchAttractionsUseCase.kt` - Search functionality
- `FilterAttractionsUseCase.kt` - Category filtering
- `GetRouteUseCase.kt` - Route planning

### Presentation Layer (`app/src/main/java/com/adygyes/presentation/`)

#### UI Components (`ui/components/`)
- `AttractionCard.kt` - Attraction detail cards
- `AttractionMarker.kt` - Custom map markers
- `SearchBar.kt` - Search input component
- `FilterChips.kt` - Category filter chips
- `MapControls.kt` - Map control buttons
- `LoadingIndicator.kt` - Loading states
- `ErrorMessage.kt` - Error handling UI

#### Screens (`ui/screens/`)

**Map Screen (`map/`)**
- `MapScreen.kt` - Main map interface
- `MapViewModel.kt` - Map state management
- `MapState.kt` - UI state definitions

**Attraction Screen (`attraction/`)**
- `AttractionDetailScreen.kt` - Full attraction details
- `AttractionViewModel.kt` - Attraction state management
- `AttractionState.kt` - UI state definitions

**Search Screen (`search/`)**
- `SearchScreen.kt` - Search interface
- `SearchViewModel.kt` - Search state management
- `SearchState.kt` - Search UI state

**Settings Screen (`settings/`)**
- `SettingsScreen.kt` - App settings
- `SettingsViewModel.kt` - Settings management
- `SettingsState.kt` - Settings UI state

#### Theme (`ui/theme/`)
- `Color.kt` - App color palette
- `Theme.kt` - Material 3 theme configuration
- `Typography.kt` - Text styles
- `Shapes.kt` - Component shapes

#### ViewModels (`viewmodels/`)
- Base ViewModels and shared state management

#### Navigation (`navigation/`)
- `NavGraph.kt` - App navigation structure
- `Destinations.kt` - Navigation destinations
- `NavigationArgs.kt` - Navigation arguments

### Dependency Injection (`app/src/main/java/com/adygyes/di/`)
- `DatabaseModule.kt` - Database dependencies
- `RepositoryModule.kt` - Repository bindings
- `NetworkModule.kt` - Network dependencies
- `UseCaseModule.kt` - Use case dependencies

### Utilities (`app/src/main/java/com/adygyes/utils/`)
- `Constants.kt` - App constants
- `Extensions.kt` - Kotlin extensions
- `LocationUtils.kt` - Location helper functions
- `ImageUtils.kt` - Image processing utilities
- `NetworkUtils.kt` - Network helper functions

## Resource Organization

### Drawable Resources (`app/src/main/res/drawable/`)
- `ic_marker_nature.xml` - Nature attraction marker
- `ic_marker_cultural.xml` - Cultural attraction marker
- `ic_marker_historical.xml` - Historical attraction marker
- `ic_search.xml` - Search icon
- `ic_filter.xml` - Filter icon
- `ic_route.xml` - Route planning icon
- `ic_location.xml` - Location icon

### String Resources (`app/src/main/res/values/`)
- **`strings.xml`** - Default (Russian) strings
- **`strings-en.xml`** - English translations
- **`strings-ru.xml`** - Explicit Russian strings
- **Future: `strings-ady.xml`** - Adyghe translations

### Raw Resources (`app/src/main/res/raw/`)
- `attractions_data.json` - Initial attraction dataset
- `categories.json` - Attraction categories

## Configuration Files

### Build Configuration
- **`app/build.gradle.kts`** - App module build configuration
  - Dependencies management
  - Build variants (debug/release)
  - Compose configuration
  - Room compiler options

- **`build.gradle.kts`** - Project-level build configuration
- **`settings.gradle.kts`** - Project settings

### ProGuard Configuration
- **`app/proguard-rules.pro`** - Code obfuscation rules
  - Yandex MapKit keep rules
  - Room database keep rules
  - Retrofit keep rules

### Manifest Configuration
- **`AndroidManifest.xml`** - App permissions and components
  - Internet permission for maps
  - Location permissions
  - Yandex MapKit API key configuration

## Testing Structure

### Unit Tests (`app/src/test/`)
```
test/
├── java/com/adygyes/
│   ├── data/
│   │   ├── repository/
│   │   └── database/
│   ├── domain/
│   │   └── usecases/
│   └── presentation/
│       └── viewmodels/
```

### Integration Tests (`app/src/androidTest/`)
```
androidTest/
├── java/com/adygyes/
│   ├── database/
│   ├── ui/
│   └── integration/
```

## Asset Management

### Images and Icons
- Vector drawables for scalability
- Multiple density support (mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)
- Dark theme variants where needed

### Data Assets
- Initial attraction data in JSON format
- Offline map tiles (future implementation)
- Category icons and images

## Build Variants and Flavors

### Debug Configuration
- Debug API keys for Yandex MapKit
- Logging enabled
- Debug database name

### Release Configuration
- Production API keys
- Logging disabled
- ProGuard enabled
- Signed APK configuration

## Documentation Structure

### Code Documentation
- KDoc comments for public APIs
- README files for complex modules
- Architecture decision records (ADRs)

### User Documentation
- In-app help screens
- User guide (future)
- API documentation (future)

## Scalability Considerations

### Multi-Region Support
- Region-specific data modules
- Configurable map bounds
- Localized content structure

### Feature Modules (Future)
- Dynamic feature delivery
- Modular architecture
- Plugin-based extensions

This structure follows Android best practices and supports the clean architecture pattern, ensuring maintainability, testability, and scalability for the Adygyes application.
