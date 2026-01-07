# MoodTracker

MoodTracker is a full-stack mood tracking application built with
**Spring Boot (Kotlin)** and **Vue 3**.

It allows users to log their daily mood, stress level, sleep hours,
habits, and meals, and later visualize patterns over time.

This repository uses a **monorepo** setup containing both the backend API
and the frontend client.

---

## Tech Stack

### Backend
- Spring Boot
- Kotlin
- Spring Security
- JWT Authentication
- JPA / Hibernate

### Frontend
- Vue 3
- Vite
- Pinia
- Vue Router
- Axios

---

## Project Structure

```

mood-tracker
├── api        # Spring Boot (Kotlin) backend
└── client     # Vue 3 frontend

```

---

## Backend Structure

```

com.ralphmarondev.api
├── config
├── user
└── mood

````

Authentication logic lives inside the `user` feature.

---

## Features

### User & Authentication
- User registration
- Login with JWT
- Password hashing
- Protected endpoints

### Mood Tracking
- Daily mood logging
- Stress level tracking
- Hours of sleep
- Reason and notes
- Daily habit checks
- Daily food checks

### Planned
- Mood statistics and trends
- Calendar or timeline view
- Reminders
- Data export
- Dark mode

---

## Getting Started

### Backend

```bash
cd api
./gradlew bootRun
````

Backend runs at:

```
http://localhost:3333
```

---

### Frontend

```bash
cd client
npm install
npm run dev
```

Frontend runs at:

```
http://localhost:5173
```

---
