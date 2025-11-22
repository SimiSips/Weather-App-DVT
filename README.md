# Weather App DVT

A modern Android weather application built with Jetpack Compose, demonstrating Clean Architecture principles and MVVM pattern. This app provides real-time weather data, forecasts, and location search functionality using the OpenWeatherMap One Call API 3.0.

## Architecture

This application follows **Clean Architecture** principles with three distinct layers:

### Data Layer
- **Models**: Data classes for API responses and domain entities
- **Remote**: Retrofit API interface and network configuration
- **Repository Implementation**: Concrete implementation of repository interfaces with error handling

### Domain Layer
- **Repository Interface**: Abstract contracts defining data operations
- **Use Cases**: Business logic (implicitly handled through repository)

### Presentation Layer
- **ViewModels**: State management using StateFlow and business logic coordination
- **UI Components**: Jetpack Compose screens and reusable components
- **State Classes**: UI state representations

### MVVM Pattern
The app implements the Model-View-ViewModel pattern:
- **Model**: Data layer (repository, API, models)
- **View**: Composable UI components
- **ViewModel**: Manages UI state and coordinates data flow using Kotlin Flows

## Project Structure

```
app/src/main/java/com/simphiweradebe/weatherappdvt/
├── data/
│   ├── models/              # Data models for API responses
│   │   ├── OneCallResponse.kt
│   │   ├── GeoLocation.kt
│   │   └── ...
│   ├── remote/              # Network layer
│   │   └── WeatherApi.kt
│   └── repository/          # Repository implementations
│       └── WeatherRepositoryImpl.kt
├── di/                      # Dependency injection modules
│   ├── AppModule.kt
│   └── NetworkModule.kt
├── domain/
│   └── repository/          # Repository interfaces
│       └── WeatherRepository.kt
├── presentation/
│   └── screens/
│       ├── weather/         # Main weather screen
│       │   ├── WeatherScreen.kt
│       │   ├── WeatherViewModel.kt
│       │   ├── WeatherState.kt
│       │   └── components/
│       └── search/          # Location search
│           ├── SearchDialog.kt
│           ├── SearchViewModel.kt
│           └── SearchState.kt
├── utils/                   # Utility classes
│   ├── Constants.kt
│   ├── Resource.kt
│   └── WeatherIconMapper.kt
└── WeatherApplication.kt    # Application class
```

## Third-Party Dependencies

### Core Dependencies
- **Hilt (2.51.1)**: Dependency injection framework for Android
  - Provides compile-time verified dependency injection
  - Manages object lifecycle and scoping
  - Used for: ViewModels, Repository, API, and Retrofit instances

- **Retrofit (2.11.0)**: Type-safe HTTP client
  - Handles API requests and responses
  - Integrates with Kotlin Coroutines
  - Used for: OpenWeatherMap API communication

- **OkHttp (4.12.0)**: HTTP client
  - Provides logging interceptor for debugging
  - Connection pooling and request/response intercepting
  - Used for: Network request logging and monitoring

- **Kotlin Serialization (1.6.3)**: JSON serialization/deserialization
  - Type-safe JSON parsing
  - Kotlin-first alternative to Gson/Moshi
  - Used for: API response parsing

### Jetpack Components
- **ViewModel**: UI state management and lifecycle awareness
- **Navigation Compose**: Screen navigation (if expanded)
- **Hilt Navigation Compose**: ViewModel integration with Compose

### Coroutines & Flow
- **Kotlin Coroutines**: Asynchronous programming
  - Handles background operations
  - Used for: API calls and data streaming

- **Kotlin Flow**: Reactive data streams
  - StateFlow for state management
  - Used for: Repository to ViewModel data flow

## Features

- Real-time weather data for any location
- Current weather conditions with detailed metrics
- Hourly weather forecast (up to 48 hours)
- Daily weather forecast (up to 8 days)
- Weather alerts and warnings
- Location search with autocomplete
- Custom weather icons for different conditions
- Gradient UI design with modern Material 3 components
- Error handling with retry functionality
- Loading states and empty states

## Build Instructions

### Prerequisites
- Android Studio Hedgehog or later
- JDK 17 or later
- Android SDK 34
- Minimum SDK 26 (Android 8.0)

### Setup

1. Clone the repository:
```bash
git clone https://github.com/SimiSips/Weather-App-DVT.git
cd WeatherAppDVT
```

2. Open the project in Android Studio

3. Configure API Key:
   - Open `app/src/main/java/com/simphiweradebe/weatherappdvt/utils/Constants.kt`
   - Replace the API_KEY value with your OpenWeatherMap API key
   - Note: Requires OpenWeatherMap One Call API 3.0 subscription

4. Sync Gradle:
   - Click "Sync Project with Gradle Files" in Android Studio
   - Or run: `./gradlew build`

5. Run the app:
   - Select a device/emulator
   - Click the "Run" button or press Shift+F10

### Build Variants
```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test

# Clean build
./gradlew clean build
```

## API Configuration

This app uses the **OpenWeatherMap One Call API 3.0**, which requires a paid subscription:

1. Sign up at [OpenWeatherMap](https://openweathermap.org/)
2. Subscribe to "One Call by Call" plan (pay-as-you-go)
3. Generate an API key (takes 10-30 minutes to activate)
4. Update the API_KEY in `Constants.kt`

### API Endpoints Used
- **One Call API 3.0**: Weather data (current, hourly, daily, alerts)
  - `https://api.openweathermap.org/data/3.0/onecall`

- **Geocoding API**: Location search and coordinate conversion
  - `https://api.openweathermap.org/geo/1.0/direct`

## Technical Notes

### Design Patterns
- **Repository Pattern**: Abstracts data sources from the rest of the app
- **Singleton Pattern**: Used for network and repository instances via Hilt
- **Observer Pattern**: Implemented through Kotlin StateFlow for reactive UI updates

### State Management
- Uses `StateFlow` for unidirectional data flow
- Each screen has its own state class encapsulating UI state
- ViewModels expose immutable state to UI components

### Error Handling
- Network errors are wrapped in `Resource` sealed class
- Three states: Loading, Success, Error
- Repository layer handles exceptions and converts to user-friendly messages
- Retry functionality for failed requests

### Custom Components
- Weather icon mapper for condition-based icons
- Custom gradient backgrounds
- Reusable weather cards and forecast items
- All UI built with Jetpack Compose (no XML layouts)

### Code Quality
- Kotlin DSL for Gradle configuration
- Version catalog for dependency management (`libs.versions.toml`)
- Previews for all composable components
- Proper error handling at all layers
- KAPT error type correction enabled

### Network Security
- All API endpoints use HTTPS
- Internet permission declared in AndroidManifest
- Network security policy compliant

## Screenshots

The app features a modern gradient design with:
- Custom header with menu and notification icons
- Large centered weather icon display
- Prominent temperature display (96sp)
- Wind and humidity metrics section
- Horizontal scrolling hourly forecast
- Dark card design for weather details
- Location search dialog with results list

## License

This project was created as part of a technical assessment for DVT.
Created by Simphiwe Radebe.
