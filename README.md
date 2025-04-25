# 🌍  Travel Management System

<div align="center">
  
  ![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
  ![License](https://img.shields.io/badge/license-MIT-green.svg)
  ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0+-brightgreen.svg)
  ![JWT](https://img.shields.io/badge/JWT-Authentication-orange.svg)
  
</div>

## 📖 Overview

The Tourist Guide and Travel Management System is a comprehensive platform bridging the gap between travelers and service providers. This full-stack application streamlines trip planning with intuitive booking capabilities, destination insights, and personalized travel experiences.

## ✨ Key Features

<table>
  <tr>
    <td>
      <b>🔐 Multi-role System</b><br>
      Admin and Tourist access levels with role-specific capabilities
    </td>
    <td>
      <b>🏨 Accommodation Booking</b><br>
      Seamless integration with hotels, resorts, and homestays
    </td>
  </tr>
  <tr>
    <td>
      <b>🗺️ Destination Management</b><br>
      Rich information on locations, attractions, and local customs
    </td>
    <td>
      <b>🚗 Transportation Services</b><br>
      Local transport options, car rentals, and route planning
    </td>
  </tr>
  <tr>
    <td>
      <b>🎭 Tour & Activity Scheduling</b><br>
      Real-time availability and booking management
    </td>
    <td>
      <b>⭐ Review & Rating System</b><br>
      User feedback for continuous service improvement
    </td>
  </tr>
  <tr>
    <td>
      <b>🔒 Secure Authentication</b><br>
      JWT-based protection of user data and transactions
    </td>
    <td>
      <b>🗺️ Interactive Maps</b><br>
      Visual location services and route visualization
    </td>
  </tr>
</table>

## 🛠️ Technology Stack

### Backend
- **☕ Java Spring Boot** - Robust application framework
- **🔑 JWT Authentication** - Secure token-based authentication
- **🛡️ Spring Security** - Comprehensive security features
- **🗄️ Spring Data JPA** - Simplified data access layer
- **🔄 RESTful API** - Standardized communication interface

### Frontend
- **📝 JavaScript** - Dynamic client-side functionality
- **🏗️ HTML5** - Modern markup language
- **🎨 CSS3** - Advanced styling capabilities
- **📱 Bootstrap 5** - Responsive design framework
- **🔐 JWT Integration** - Secure client-side authentication

## 📂 Project Structure

```
tourist-guide-system/
├── src/
│   ├── main/
│   │   ├── java/com/touristguide/
│   │   │   ├── config/         # JWT config, security settings
│   │   │   ├── controller/     # REST endpoints
│   │   │   ├── dto/            # Data transfer objects
│   │   │   ├── advicer/        # exception handler
│   │   │   ├── entity/         # Entity classes
│   │   │   ├── repository/     # Data access layer
│   │   │   ├── service/        # Business logic
│   │   │   └── util/           # Helper classes
│   │   └── resources/
│   │       ├── static/         # Frontend assets
│   │       │   ├── css/        # Stylesheets
│   │       │   ├── js/         # JavaScript files
│   │       │   └── images/     # Image assets
│   │       └── templates/      # HTML templates
│   └── test/                   # Unit and integration tests
├── pom.xml                     # Maven dependencies
└── README.md                   # This documentation
```

## 🔐 Security Implementation

Our application implements a robust JWT-based authentication flow:

1. Users provide credentials through a secure login endpoint
2. Server validates credentials and generates a signed JWT token
3. Token is stored client-side and included in request headers
4. Server validates token signature and permissions for each protected resource
5. Time-based token expiration enhances security with configurable lifespan

## 👥 Role-Based Access Control

| Role | Capabilities |
|------|-------------|
| **Admin** | System configuration, user management, data analytics, service oversight |
| **Tourist** | Destination browsing, service booking, itinerary creation, review submission |

## 🔌 API Endpoints

The system exposes RESTful endpoints organized by domain:

- `/api/v1/auth/*` - Authentication and user management
- `/api/V1/destinations/*` - Destination information and search
- `/api/v1/accommodations/*` - Accommodation information and search
- `/api/v1/tours/*` - Tour information and search
- `/api/v1/transport/*` - Transport information and search
- `/api/v1/reviews/*` - Customer feedback and ratings
- `/api/v1/booking/*` - booking
- `/api/v1/paymnet/*` - paymnet

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL/PostgreSQL database

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/manushamadubhashini/Tour-Guide-and-Travel-Management-System.git
   cd Tour-Guide-and-Travel-Management-System
   ```

2. **Configure application properties:**
   Create `application.properties` with your database and JWT settings

3. **Build the project:**
   ```bash
   mvn clean install
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application at:**
   ```
   http://localhost:8080
   ```

## 🧪 Testing

Execute the test suite with:
```bash
mvn test
```

## 📦 Deployment

Deploy as a standalone JAR on any Java-compatible server:
```bash
java -jar target/tourist-guide-system-1.0.0.jar
```

## 🔮 Future Enhancements

- 📱 Mobile application development
- 🌐 Multi-language support
- 🤖 AI-powered recommendation engine
- 🔄 Integration with third-party booking platforms
- 📊 Advanced analytics and reporting

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch: `git checkout -b feature/amazing-feature`
3. Commit changes: `git commit -m 'Add amazing feature'`

## Tourist Dashboard

 ![Image](https://github.com/user-attachments/assets/e19d9dcf-14ec-4d7b-8b07-08117c70778b)
 

![Image](https://github.com/user-attachments/assets/fad10c3c-5843-4dbc-af29-b2f82d21409a)


![Image](https://github.com/user-attachments/assets/f90d8264-4f02-463a-bb3a-d6a819308139)


![Image](https://github.com/user-attachments/assets/8df84ae7-035d-4b38-99fd-d09d9c6a09fb)


![Image](https://github.com/user-attachments/assets/db59295e-a6b1-4146-873a-8495bf5b75ac)


![Image](https://github.com/user-attachments/assets/b282ad94-c0f5-4d9f-80ca-6bc506c7a842)


![Image](https://github.com/user-attachments/assets/b1905dc8-f384-4c4a-a0bd-d2a141e8d20a)


![Image](https://github.com/user-attachments/assets/996bd8d3-f3a8-475b-a30c-6f25031dc074)


![Image](https://github.com/user-attachments/assets/003f9701-aa13-4d6d-98e9-f678b7ff67bf)


![Image](https://github.com/user-attachments/assets/c9ebb89d-10fa-443f-bcaa-723ff249cbb1)


![Image](https://github.com/user-attachments/assets/9910c7b7-ce3d-4305-861c-bf86b44fb2de)


![Image](https://github.com/user-attachments/assets/07df806e-c250-4fa9-a6a5-1fd5f75197dc)


![Image](https://github.com/user-attachments/assets/9f141867-db18-4282-91ae-8851fb085436)


![Image](https://github.com/user-attachments/assets/ef46a00c-d6ec-4e7b-9ccb-b6f21a0d4a45)


![Image](https://github.com/user-attachments/assets/9ffb6d1f-58d9-42e3-9bd9-1081a2598a17)


![Image](https://github.com/user-attachments/assets/525bfd22-a589-4947-85b2-5e21639ee42c)
5. Push to branch: `git push origin feature/amazing-feature`
6. Open a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.
