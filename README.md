## Notes and Implementation Details
**Architecture Pattern**
For this project, I used MVVM architecture. I opted to use the Clean Architecture pattern, which helps to separate concerns, making the code more maintainable and testable. Clean Architecture divides the application into different layers:

**Presentation Layer – Contains the ViewModel and UI logic.**
Domain Layer – Holds business logic (use cases).
Data Layer – Responsible for data fetching and persistence (repositories and APIs).
The separation of concerns ensures that each layer only depends on the one directly below it, leading to a more modular structure and easier testing.

**Libraries and Tools**
* **Hilt**: For dependency injection. This simplifies the creation and management of object dependencies across the application.
* **Kotlin** Coroutines: Used to handle asynchronous operations smoothly, providing a better alternative to callback-based approaches.
* **Retrofit**: For network communication with APIs. It simplifies API calls and response handling.
* **Mockk**: A mocking framework used in unit testing to mock dependencies and simulate various behaviors during tests.
* **Kotlinx Serialization**: Used to handle serialization and deserialization of JSON objects.
* **JUnit**: For unit testing the ViewModel and repository layers, ensuring business logic and UI states are correct.
* **Truth**: Used for assertions in your unit tests. It is a library for fluent assertions, allowing you to write clear and readable test expectations.
* **Lottie**: This is used for integrating Lottie animations in Jetpack Compose UI. This helps render rich animations in your UI with Lottie files (e.g., JSON-based animations).

**Testing Strategy and Coverage**
* Unit tests were written for repositories and ViewModels using JUnit and Mockk.
* CountriesViewModel 100% Coverage
* Exchange Rate 50% Coverage
* ExchangeRateRepositoryImpl 100% Coverage

**Known Issues, Limitations, or Areas for Improvement**
**Error Handling:**
Currently, the repository's error handling is simplistic. To improve the user experience, more detailed error handling (e.g., handling specific API error codes) can be added.

**Caching:**
The app does not implement caching for API responses. In real-world scenarios, caching is beneficial for improving app performance and reducing API calls, especially for countries

**Network Optimization:**
I added a loading animation while getting data from the API and also checking the network connection. I think we can do it better using cashing.

**Testing Strategy:**
I implemented Repo testing, but we can do VM, UseCases, and UI also. 

**Scalability:**
We can add Pagination, the data models may need to be optimized for performance (e.g., pagination, lazy loading, etc.).
We can also make a module-based approach for Countries and exchange rates.

<img src="screenshot/CountriesList.png" width="500" alt=""/>
<img src="screenshot/ExchangeRateScreen.png" width="500" alt=""/>
<img src="screenshot/NoInternetConnection.png" width="500" alt=""/>

```kotlin
FetchDataFromAssets(context, onCountryClick)
```

```kotlin
FetchDataFromAPI(viewModel, context, onCountryClick)
```
