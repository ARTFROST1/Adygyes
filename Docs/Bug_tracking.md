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
