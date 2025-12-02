# TextChat Application

TextChat is a compact Kotlin Android demo chat app demonstrating modern Android practices: Jetpack Compose + Material 3 UI, MVVM architecture, and Kotlin Coroutines/Flows for async state. Messages are stored in-memory; sending a message displays it aligned to the right and a simulated reply appears after a 5-second delay aligned to the left. The project is test-first with unit tests for core logic.

## Key points
- **Simple, focused demo: send a message (appears on the right) and receive a simulated reply after 5s (appears on the left).
- **UI: Jetpack Compose + Material 3, light/dark support.
- **Architecture: MVVM with Coroutines and Flows.
- **Persistence: in-memory (sufficient for the exercise).
- **Testing: test-first approach with unit tests (JUnit, Robolectric, Mockito).
- **Build: Gradle-based Android project.

## Features

- **Real-time Messaging**: Send and receive messages with a clean and responsive UI.
- **Dynamic Themes**: Supports light and dark themes with Material 3 design.
- **Error Handling**: Graceful error handling for network and UI operations.
- **Unit Testing**: Comprehensive test coverage for ViewModels and UI components.
- **Dynamic Colors**: Adapts to system-wide dynamic color settings on supported devices.

## Tech Stack

- **Programming Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI Framework**: Jetpack Compose
- **Asynchronous Programming**: Kotlin Coroutines and Flows
- **Testing**: JUnit, Robolectric, Mockito

## Project Structure

- `app/src/main/java/za/co/shepherd/textchat/`
    - `ui/`: Contains UI components, themes, and composables.
    - `viewmodel/`: ViewModels for managing UI state and business logic.
    - `data/`: Data models and repository classes for handling data sources.
    - `domain/`: Use cases encapsulating business logic.
    - `util/`: Utility classes and helpers.

1. Clone the repository:
   ```bash
   git clone https://github.com/shepherdsid/TextChat.git

## Open the project in Android Studio (recommended)
* On macOS: open Android Studio, choose File > Open... and select the project's build.gradle or the project root folder.
* Let Android Studio sync Gradle and download any required SDK components.

## Build and run the app
* Start an Android emulator or connect a physical device.

## Testing
- **Unit Tests**
  * Jacoco is configured for code coverage reports.
  * Reports are generated in `app/build/reports/jacoco/jacocoTestReport/html/index.html`.
  * Coverage reports include line and branch coverage.
  * Unit tests are located in app/src/test/java/. These tests cover: ViewModels, UI components, and utility functions.
* Run unit tests and generate JaCoCo coverage report (after running tests):
  * ```bash
    ./gradlew testDebugUnitTest
    ./gradlew clean testDebugUnitTest connectedDebugAndroidTest jacocoTestReport
    ```