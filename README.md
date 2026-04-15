# 🎓 University Event Management System

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1.2-green.svg)](https://www.thymeleaf.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9.0-red.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A comprehensive web-based event management system built with Spring Boot, designed for universities to manage events, bookings, and user interactions efficiently.

## ✨ Features

### 👨‍💼 Admin Features
- **Dashboard Overview**: Monitor events, bookings, and pending payments
- **Event Management**: Create, update, delete, and manage university events
- **Booking Oversight**: View all bookings and manage payment statuses
- **User Management**: Handle student registrations and accounts

### 👨‍🎓 Student Features
- **Event Discovery**: Browse and view available university events
- **Booking System**: Reserve seats for events with payment tracking
- **Personal Dashboard**: View booked events and booking history
- **Rating System**: Rate and review attended events

### 🔧 Technical Features
- **Role-based Authentication**: Secure login system with admin/student roles
- **Responsive Web Interface**: Modern UI built with Thymeleaf templates
- **Database Integration**: MySQL database with JPA/Hibernate ORM
- **RESTful Architecture**: Well-structured MVC pattern implementation

## 🛠️ Tech Stack

- **Backend**: Java 21, Spring Boot 3.4.4
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript
- **Database**: MySQL 8.0
- **Build Tool**: Maven
- **ORM**: Spring Data JPA with Hibernate
- **Security**: Spring Security (session-based authentication)

## 📋 Prerequisites

Before running this application, make sure you have the following installed:

- **Java 21** or higher
- **Maven 3.6+**
- **MySQL Server 8.0+**
- **Git** (for cloning the repository)

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/university-event-management.git
cd university-event-management
```

### 2. Database Setup
1. Install and start MySQL Server
2. Create a database named `uni_event_db`:
```sql
CREATE DATABASE uni_event_db;
```
3. Update database credentials in `src/main/resources/application.properties` if needed:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/uni_event_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```

### 3. Build the Application
```bash
mvn clean compile
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 5. Alternative Run Methods
- **Using Maven Wrapper**: `./mvnw spring-boot:run` (Linux/Mac) or `mvnw.cmd spring-boot:run` (Windows)
- **Build JAR and Run**: `mvn clean package` then `java -jar target/event-management-0.0.1-SNAPSHOT.jar`

## 📖 Usage

### Accessing the Application
1. Open your browser and navigate to `http://localhost:8080`
2. Register as a new user or login with existing credentials

### Default Admin Account
- **Email**: admin@university.edu (create during first setup)
- **Password**: admin123

### Student Registration
- Students can register through the registration page
- After registration, login to access the student dashboard

## 🗄️ Database Schema

The application uses the following main entities:

- **Users**: Admin and Student accounts with role-based access
- **Events**: University events with details, pricing, and capacity
- **Bookings**: Event reservations with payment status tracking
- **Ratings**: Student feedback and ratings for events

## 🔧 Configuration

### Application Properties
Key configuration options in `application.properties`:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/uni_event_db
spring.datasource.username=root
spring.datasource.password=root

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Environment Variables
For production deployment, consider using environment variables:
- `DB_URL`: Database connection URL
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password

## 🧪 Testing

Run the test suite:
```bash
mvn test
```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/university/eventmanagement/
│   │       ├── EventManagementApplication.java
│   │       ├── config/
│   │       │   └── WebConfig.java
│   │       ├── controller/
│   │       │   ├── AdminController.java
│   │       │   ├── AuthController.java
│   │       │   └── StudentController.java
│   │       ├── interceptor/
│   │       │   └── AuthInterceptor.java
│   │       ├── model/
│   │       │   ├── Booking.java
│   │       │   ├── Event.java
│   │       │   ├── Rating.java
│   │       │   └── User.java
│   │       ├── repository/
│   │       │   ├── BookingRepository.java
│   │       │   ├── EventRepository.java
│   │       │   ├── RatingRepository.java
│   │       │   └── UserRepository.java
│   │       └── service/
│   │           ├── BookingService.java
│   │           ├── EventService.java
│   │           ├── RatingService.java
│   │           └── UserService.java
│   └── resources/
│       ├── application.properties
│       ├── static/css/style.css
│       └── templates/
│           ├── index.html
│           ├── auth/
│           ├── admin/
│           └── student/
└── test/
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Development Guidelines
- Follow Java naming conventions
- Write clear, concise commit messages
- Add tests for new features
- Update documentation as needed

## 📝 API Endpoints

### Authentication
- `GET /` - Home page
- `GET /login` - Login page
- `POST /login` - Process login

### Admin Endpoints
- `GET /admin/dashboard` - Admin dashboard
- `GET /admin/events` - Event management
- `GET /admin/bookings` - Booking management

### Student Endpoints
- `GET /student/dashboard` - Student dashboard
- `GET /student/my-bookings` - Booking history

## 🔒 Security

- Session-based authentication
- Role-based access control (Admin/Student)
- CSRF protection enabled
- Input validation and sanitization

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Built with Spring Boot framework
- UI designed with Thymeleaf templates
- Database powered by MySQL
- Icons and styling inspired by modern web design principles



---

**Made with ❤️ for university communities**
