# 📒 Notes App

A simple and efficient Notes app built using modern Android development practices, allowing users to create, view, update, and delete notes. The app leverages Room Database for local storage and Jetpack Compose for a seamless and intuitive user experience.

## 🚀 Features

- **Create Notes:** Add new notes with ease.  
- **View Notes:** Display saved notes in a scrollable list using LazyColumn.  
- **Update Notes:** Long press on a note to update its content.  
- **Delete Notes:** Double-tap on a note to delete it, with a confirmation dialog.  
- **Responsive UI:** Built with Jetpack Compose for a smooth, modern interface.  

## 🛠️ Tech Stack

- **Kotlin** — Primary programming language.  
- **Jetpack Compose** — Modern Android UI toolkit.  
- **Room Database** — Local persistence for storing notes.  
- **Dagger Hilt** — Dependency injection for scalable architecture.  
- **State Management** — Using State and MutableState to handle UI changes.  

## 🏗️ Architecture

- **MVVM (Model-View-ViewModel)** — Pattern for clear separation of concerns.  
- **Repository Pattern** — Manages data sources.  

## ⚡ How It Works

- Notes are stored locally using Room.  
- State management ensures live updates to the UI.  
- Gesture detection differentiates between taps, long presses, and double taps.  

## 🛠️ Setup & Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/yourusername/NotesApp.git
