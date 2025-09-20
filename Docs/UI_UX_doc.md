# UI/UX Documentation for Adygyes

## Design System Overview

### Design Philosophy
Adygyes follows a **minimalist, modern design approach** that prioritizes:
- **Clarity** - Clear visual hierarchy and intuitive navigation
- **Efficiency** - Quick access to essential features
- **Beauty** - Clean aesthetics inspired by Google/Yandex Maps but with unique identity
- **Accessibility** - Inclusive design for all users

### Visual Identity
- **Primary Focus:** Map-centric interface
- **Style:** Clean, modern, minimalist
- **Inspiration:** Google Maps / Yandex Maps UX patterns
- **Unique Elements:** Custom photo-based markers, Adygea-themed color palette

## Color Palette

### Primary Colors
```kotlin
// Light Theme
val AdygyeaGreen = Color(0xFF2E7D32)      // Primary - Forest green (Adygea nature)
val AdygyeaGreenLight = Color(0xFF4CAF50) // Primary variant
val AdygyeaBlue = Color(0xFF1976D2)       // Secondary - Mountain blue
val AdygyeaGold = Color(0xFFFFB300)       // Accent - Sunset gold

// Neutral Colors
val BackgroundLight = Color(0xFFFAFAFA)   // Light background
val SurfaceLight = Color(0xFFFFFFFF)      // Card surfaces
val OnSurface = Color(0xFF212121)         // Text on surfaces
val OnSurfaceVariant = Color(0xFF757575)  // Secondary text

// Dark Theme
val BackgroundDark = Color(0xFF121212)    // Dark background
val SurfaceDark = Color(0xFF1E1E1E)       // Dark card surfaces
val OnSurfaceDark = Color(0xFFE0E0E0)     // Text on dark surfaces
```

### Semantic Colors
```kotlin
val Success = Color(0xFF4CAF50)
val Warning = Color(0xFFFF9800)
val Error = Color(0xFFF44336)
val Info = Color(0xFF2196F3)
```

## Typography

### Type Scale (Material 3)
```kotlin
val AdygyesTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
)
```

### Font Usage Guidelines
- **Headlines:** Attraction names, screen titles
- **Body Text:** Descriptions, search results
- **Labels:** Buttons, chips, navigation
- **Captions:** Photo credits, metadata

## Component Library

### 1. Map Components

#### Custom Attraction Marker
```kotlin
@Composable
fun AttractionMarker(
    attraction: Attraction,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    // Circular photo-based marker with selection state
    // Size: 48dp normal, 56dp selected
    // Border: 2dp white, 4dp primary when selected
}
```

#### Map Style Toggle
```kotlin
@Composable
fun MapStyleToggle(
    currentStyle: MapStyle,
    onStyleChange: (MapStyle) -> Unit
) {
    // Floating action button in top-right corner
    // Cycles through: Schema -> Satellite -> Hybrid
}
```

#### Map Controls
```kotlin
@Composable
fun MapControls(
    onLocationClick: () -> Unit,
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit
) {
    // Vertical button group on right side
    // My Location, Zoom In, Zoom Out
}
```

### 2. Search Components

#### Search Bar
```kotlin
@Composable
fun AttractionSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    suggestions: List<Attraction> = emptyList()
) {
    // Top-positioned search bar
    // Auto-complete dropdown
    // Clear button when text present
}
```

#### Filter Chips
```kotlin
@Composable
fun CategoryFilterChips(
    categories: List<Category>,
    selectedCategories: Set<Category>,
    onSelectionChange: (Set<Category>) -> Unit
) {
    // Horizontal scrollable row
    // Toggle selection behavior
    // Visual feedback for active filters
}
```

### 3. Content Components

#### Attraction Card (Bottom Sheet)
```kotlin
@Composable
fun AttractionDetailCard(
    attraction: Attraction,
    onRouteClick: () -> Unit,
    onDismiss: () -> Unit
) {
    // Full-screen bottom sheet
    // Photo gallery at top
    // Title, description, actions
    // Route planning button
}
```

#### Photo Gallery
```kotlin
@Composable
fun AttractionPhotoGallery(
    photos: List<String>,
    modifier: Modifier = Modifier
) {
    // Horizontal pager with indicators
    // Zoom capability
    // Full-screen view option
}
```

### 4. Navigation Components

#### Bottom Navigation (Future)
```kotlin
@Composable
fun AdygyesBottomNavigation(
    currentDestination: String,
    onNavigate: (String) -> Unit
) {
    // Map, Search, Favorites, Settings
    // Only if app expands beyond map focus
}
```

## Screen Layouts

### 1. Main Map Screen

#### Layout Structure
```
┌─────────────────────────────────────┐
│ [Search Bar]              [Style]   │ ← Top bar
├─────────────────────────────────────┤
│                                     │
│                                     │
│           MAP VIEW                  │ ← Full screen map
│         (with markers)              │
│                                     │
│                                 [+] │ ← Map controls
│                                 [-] │   (right side)
│                                 [📍] │
└─────────────────────────────────────┘
```

#### Interactive Elements
- **Search Bar:** Always visible at top
- **Map Style Toggle:** Top-right corner
- **Map Controls:** Right side (location, zoom)
- **Markers:** Clickable attraction points
- **Gesture Support:** Pan, zoom, rotate

### 2. Attraction Detail Sheet

#### Layout Structure
```
┌─────────────────────────────────────┐
│              [Handle]               │ ← Drag handle
├─────────────────────────────────────┤
│                                     │
│         PHOTO GALLERY               │ ← Hero image area
│                                     │
├─────────────────────────────────────┤
│ Attraction Name              [❤️]   │ ← Title & favorite
├─────────────────────────────────────┤
│ Category • Distance                 │ ← Metadata
├─────────────────────────────────────┤
│                                     │
│ Description text here...            │ ← Content area
│                                     │
├─────────────────────────────────────┤
│        [Get Directions]             │ ← Primary action
└─────────────────────────────────────┘
```

### 3. Search Results Screen

#### Layout Structure
```
┌─────────────────────────────────────┐
│ [Search Input]                 [×]  │ ← Search header
├─────────────────────────────────────┤
│ [Nature] [Culture] [History]        │ ← Filter chips
├─────────────────────────────────────┤
│ ┌─────┐ Attraction Name             │
│ │ IMG │ Short description...        │ ← Result items
│ └─────┘ Category • 2.5 km          │
├─────────────────────────────────────┤
│ ┌─────┐ Another Attraction          │
│ │ IMG │ Description text...         │
│ └─────┘ Category • 5.1 km          │
└─────────────────────────────────────┘
```

## User Experience Flows

### 1. Primary User Journey: Discover Attraction

```
Map Screen → Tap Marker → View Details → Get Directions
     ↑                        ↓
  Search ←────────────── Share/Favorite
```

**Steps:**
1. User opens app to full-screen map
2. Sees custom photo markers for attractions
3. Taps marker to view basic info popup
4. Taps popup to open full detail sheet
5. Views photos and reads description
6. Taps "Get Directions" for navigation

### 2. Search Flow

```
Map Screen → Tap Search → Type Query → Select Result → View Details
     ↑                                      ↓
  Filter by Category ←─────────────── Refine Search
```

**Steps:**
1. User taps search bar at top
2. Types attraction name or keyword
3. Sees auto-complete suggestions
4. Selects result or applies category filter
5. Views filtered results list
6. Taps result to view details

### 3. Navigation Flow

```
Attraction Details → Get Directions → Yandex Maps → Return to App
                                          ↓
                                    Route Completed
```

## Responsive Design

### Screen Size Adaptations

#### Phone Portrait (Primary)
- Full-screen map with overlay UI
- Bottom sheet for attraction details
- Compact search bar and controls

#### Phone Landscape
- Map takes full width
- Side panel for attraction details (if space allows)
- Horizontal control layout

#### Tablet Support (Future)
- Split-screen layout: map + details panel
- Enhanced search with sidebar
- Multi-column attraction grid

## Accessibility Standards

### Visual Accessibility
- **Contrast Ratios:** WCAG AA compliant (4.5:1 minimum)
- **Text Scaling:** Support dynamic type up to 200%
- **Color Independence:** No color-only information conveyance

### Motor Accessibility
- **Touch Targets:** Minimum 48dp for all interactive elements
- **Gesture Alternatives:** Button alternatives for complex gestures
- **Voice Control:** Support for voice navigation commands

### Cognitive Accessibility
- **Clear Labels:** Descriptive button and link text
- **Consistent Navigation:** Predictable interaction patterns
- **Error Prevention:** Clear validation and confirmation

### Implementation
```kotlin
@Composable
fun AccessibleButton(
    onClick: () -> Unit,
    contentDescription: String,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .semantics {
                this.contentDescription = contentDescription
                role = Role.Button
            }
            .minimumInteractiveComponentSize()
    ) {
        content()
    }
}
```

## Animation Guidelines

### Micro-Interactions
- **Marker Selection:** Scale + elevation animation (200ms)
- **Sheet Expansion:** Smooth slide-up with bounce (300ms)
- **Search Suggestions:** Fade in/out (150ms)
- **Button Press:** Subtle scale down (100ms)

### Transitions
- **Screen Changes:** Shared element transitions for photos
- **Map Updates:** Smooth camera movements
- **Loading States:** Skeleton screens and progress indicators

### Performance Considerations
- **60 FPS Target:** All animations maintain smooth framerate
- **Reduced Motion:** Respect system accessibility settings
- **Battery Optimization:** Minimize continuous animations

## Dark Theme Support

### Color Adaptations
- **Backgrounds:** Deep grays instead of pure black
- **Surfaces:** Elevated surfaces with subtle borders
- **Text:** High contrast white/light gray
- **Markers:** Enhanced visibility with glow effects

### Component Variations
- **Search Bar:** Dark surface with light text
- **Cards:** Dark surface with accent borders
- **Buttons:** Adjusted colors for dark backgrounds

## Localization Considerations

### Text Expansion
- **Russian:** Baseline language (longest text)
- **English:** ~20% shorter than Russian
- **Adyghe (Future):** Unknown expansion factor

### RTL Support (Future)
- **Layout Mirroring:** Automatic layout direction
- **Icon Adjustments:** Directional icons flipped
- **Text Alignment:** Proper text direction support

### Cultural Adaptations
- **Date Formats:** Localized date/time display
- **Distance Units:** Metric system (km/m)
- **Currency:** Russian Ruble (future monetization)

## Performance Guidelines

### Image Optimization
- **Marker Photos:** 48dp circular crops, WebP format
- **Gallery Images:** Progressive loading, multiple sizes
- **Caching:** Aggressive caching for offline support

### Layout Performance
- **Lazy Loading:** Virtualized lists for search results
- **Composition:** Minimize recomposition with stable keys
- **Memory:** Efficient bitmap handling and recycling

This UI/UX documentation ensures a consistent, accessible, and beautiful user experience that aligns with the Adygyes app's goals of being a modern, minimalist tourism application for the Republic of Adygea.
