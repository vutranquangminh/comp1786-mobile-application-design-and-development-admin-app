# LotusYogaApp - Yoga Studio Management System

## 👨‍💻 Author Information

**Author:** Vũ Trần Quang Minh  
**Greenwich UK Student ID:** 001325733  
**FPT Greenwich Student ID:** GCS220006  
**Class:** COS1103  
**Subject:** COMP1786 - Mobile Application Design and Development  
**Contact:** minhvtqgcs220006@fpt.edu.vn  
**GitHub:** [vutranquangminh](https://github.com/vutranquangminh)

---

## 📱 Overview

LotusYogaApp is a comprehensive Android application designed for yoga studio management. It provides an intuitive interface for managing teachers, courses, customers, and transactions with seamless cloud synchronization capabilities.

## ✨ Features

### 🧘‍♀️ Core Management Features
- **Teacher Management**: Add, edit, delete, and view yoga teachers
- **Course Management**: Create and manage yoga courses with detailed information
- **Customer Management**: Track customer information and course enrollments
- **Transaction Tracking**: Monitor financial transactions and payments

### 🔄 Data Synchronization
- **Firebase Integration**: Real-time cloud synchronization with Firebase Firestore
- **Offline Support**: Local SQLite database for offline functionality
- **Bidirectional Sync**: Automatic synchronization between local and cloud databases

### 🎨 User Interface
- **Modern Material Design**: Clean and intuitive user interface
- **RecyclerView Lists**: Efficient display of teachers and courses
- **Responsive Layout**: Optimized for various screen sizes
- **Edge-to-Edge Design**: Modern Android UI patterns

## 🏗️ Architecture

### Technology Stack
- **Language**: Java
- **Minimum SDK**: Android API 35
- **Target SDK**: Android API 35
- **Database**: Room (SQLite) with Firebase Firestore
- **UI Framework**: AndroidX with Material Design components

### Project Structure
```
app/
├── src/main/java/com/vutranquangminh/lotusyogaapp/
│   ├── MainActivity.java                    # Main entry point
│   ├── backend/
│   │   ├── adapters/                       # RecyclerView adapters
│   │   ├── courses/                        # Course management activities
│   │   ├── teachers/                       # Teacher management activities
│   │   └── fragments/                      # View activities
│   └── infrastructure/
│       ├── AppDatabase.java                # Room database configuration
│       ├── dao/                           # Data Access Objects
│       ├── firebase/                      # Firebase synchronization
│       └── models/                        # Entity and relationship models
└── res/
    ├── layout/                            # UI layouts
    └── values/                            # Resources and strings
```

### Database Schema

#### Core Entities
- **Teacher**: Manages yoga instructors with experience and start date
- **Course**: Yoga classes with details like duration, capacity, and pricing
- **Customer**: Student information with contact details
- **UserTransaction**: Financial transaction records
- **CourseCustomerCrossRef**: Many-to-many relationship between courses and customers

#### Relationships
- **Teacher → Course**: One-to-many (one teacher can teach multiple courses)
- **Course ↔ Customer**: Many-to-many (customers can enroll in multiple courses)

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API 35
- Google Services configuration for Firebase

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd comp1786-mobile-application-design-and-development-admin-app
   ```

2. **Configure Firebase**
   - Create a Firebase project at [Firebase Console](https://console.firebase.google.com/)
   - Download `google-services.json` and place it in the `app/` directory
   - Enable Firestore Database in your Firebase project

3. **Build and Run**
   ```bash
   ./gradlew build
   ./gradlew installDebug
   ```

### Firebase Setup
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project
3. Add Android app with package name: `com.vutranquangminh.lotusyogaapp`
4. Download `google-services.json` and place in `app/` directory
5. Enable Firestore Database
6. Set up security rules for Firestore

## 📱 Usage

### Main Dashboard
The app starts with a main dashboard offering two primary options:
- **Manage Teachers**: Access teacher management features
- **Manage Courses**: Access course management features

### Teacher Management
- **View Teachers**: See all registered yoga teachers
- **Add Teacher**: Create new teacher profiles with name, experience, and start date
- **Edit Teacher**: Modify existing teacher information
- **Delete Teacher**: Remove teachers from the system

### Course Management
- **View Courses**: Browse all available yoga courses
- **Add Course**: Create new courses with details like:
  - Course name and description
  - Date and time
  - Duration (1-3 hours)
  - Capacity (10-30 students)
  - Price and category (Beginner/Intermediate/Advanced/VIP)
  - Assigned teacher
- **Edit Course**: Modify course details
- **Delete Course**: Remove courses from the system

## 🔧 Configuration

### Database Configuration
The app uses Room database with the following configuration:
- **Database Name**: `yoga_app_database`
- **Version**: 2 (with migration support)
- **Entities**: Customer, Course, Teacher, UserTransaction, CourseCustomerCrossRef

### Firebase Configuration
- **Collections**: `customers`, `courses`, `teachers`, `transactions`, `course_customer_crossrefs`
- **Sync Strategy**: Bidirectional synchronization with conflict resolution

## 🛠️ Development

### Key Dependencies
```gradle
// Room Database
implementation("androidx.room:room-runtime:2.7.2")
annotationProcessor("androidx.room:room-compiler:2.7.2")

// Firebase
implementation(platform("com.google.firebase:firebase-bom:34.0.0"))
implementation("com.google.firebase:firebase-firestore")

// AndroidX Components
implementation(libs.appcompat)
implementation(libs.material)
implementation(libs.activity)
implementation(libs.constraintlayout)
```

### Build Configuration
- **Compile SDK**: 35
- **Minimum SDK**: 35
- **Target SDK**: 35
- **Java Version**: 11

## 🔄 Data Synchronization

### Sync Features
- **Automatic Sync**: Data syncs on app startup and activity resume
- **Bidirectional**: Local ↔ Cloud synchronization
- **Conflict Resolution**: Cloud data takes precedence during conflicts
- **Offline Support**: Full functionality without internet connection

### Sync Process
1. **Local to Cloud**: Uploads local changes to Firebase
2. **Cloud to Local**: Downloads cloud changes to local database
3. **Data Consistency**: Maintains data integrity across devices

## 📊 Data Models

### Teacher Entity
```java
@Entity
public class Teacher {
    @PrimaryKey(autoGenerate = true)
    public int Id;
    public String Name;
    public String Experience;
    public String DateStartedTeaching;
}
```

### Course Entity
```java
@Entity
public class Course {
    @PrimaryKey(autoGenerate = true)
    public int Id;
    public String Name;
    public String Description;
    public String DateTime;
    public Integer Duration;
    public Integer Capacity;
    public String Price;
    public String Category;
    public int TeacherId;
}
```

## 🧪 Testing

### Unit Tests
- Located in `app/src/test/java/`
- Tests for database operations and business logic

### Instrumented Tests
- Located in `app/src/androidTest/java/`
- UI testing with Espresso

## 📝 License

This project is developed for educational purposes as part of the COMP1786 Mobile Application Design and Development course.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📞 Support

For technical support or questions about the application, please contact the development team.

---

**Note**: This application is designed for yoga studio management and includes comprehensive data management features with cloud synchronization capabilities.

---