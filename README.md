# Product List App

This is an Android application that displays a list of products fetched from an API using Retrofit. The app allows users to add products to their favorites and remove them using Room Database. It follows the MVVM architecture and utilizes Coroutines, Flow, and MutableStateFlow for data management.

## Features
- ✅ Fetch products from API using Retrofit
- ✅ Display a list of products
- ✅ Add products to favorites
- ✅ Remove products from favorites
- ✅ Store favorite products using Room Database
- ✅ MVVM architecture with Coroutines and Flow

## Technologies Used
- **Kotlin**
- **MVVM Architecture**
- **Retrofit** for API calls
- **Room Database** for local storage
- **Coroutines & Flow** for asynchronous operations
- **MutableStateFlow** for state management
- **Jetpack Components**

## 🚀 Setup and Installation
```sh
# Clone the repository
git clone https://github.com/your-username/repository-name.git

# Open the project in Android Studio
# Sync Gradle files
# Run the application on an emulator or physical device
```

## 🌐 API Source
The products are fetched from [DummyJSON](https://dummyjson.com/docs/products)

## ⚙️ How It Works
1. The app fetches product data from an API using Retrofit.
2. The data is exposed through a repository and ViewModel using Coroutines and Flow.
3. Users can mark products as favorites, which are stored in Room Database.
4. Favorite products persist even after the app is closed.


## 🎓 Credits
This project is developed as part of the **Day 2 Code Lab** for the Coroutines course at ITI.

