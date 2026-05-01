📚 Library Management System

Created by Rishab Velip

📖 Overview

The Library Management System (LMS) is a desktop application built using Java Swing for the user interface and MySQL as the backend database. It digitizes and simplifies daily library operations, replacing manual record-keeping with an efficient and reliable system.

The application features a modern dark-themed UI and supports the complete lifecycle of library operations — from managing books and members to issuing and returning books.

It follows a layered architecture, ensuring clear separation between:

UI (Swing)
Business Logic (DAO)
Database (MySQL)

This makes the system scalable, maintainable, and easy to extend.

🎯 Objectives
Develop a fully functional desktop LMS
Maintain digital records of books
Manage library members
Automate issuing and returning of books
Provide real-time statistics
Build a clean UI using Java Swing
Use MySQL for persistent storage
📌 Features
🌙 Dark navy modern UI theme
📊 Real-time dashboard statistics
📚 Full CRUD operations for books and members
🔍 Live search (title, author, ISBN, name, email)
🔄 Issue & return tracking with status indicators
🎯 Dropdown-based issue system
🎨 Color-coded statuses:
Yellow → ISSUED
Green → RETURNED
🔐 Transaction-safe database operations
⚡ Singleton database connection handling
✅ Input validation across all forms
🧩 Project Modules
📘 Book Management
Add, edit, delete books
Search by title, author, ISBN
Track available copies
👤 Member Management
Register new members
Update/delete member records
Search by name/email
🔄 Issue & Return System
Issue books to members
Auto record issue date
Handle returns with updates
View full issue history
📊 Dashboard
Total books
Registered members
Issued books count
🛠️ Tech Stack
Frontend: Java Swing
Backend: MySQL
Language: Java
Architecture: DAO Pattern
