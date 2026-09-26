# 🏥 Hospital Management System

A simple **Hospital Management System** developed using **Java, MySQL, HTML, CSS, and JavaScript**.

The system provides a web-based interface for managing patients, doctors, and appointments. A Java HTTP server acts as the backend API and communicates with a MySQL database.

## 📌 Features

- 🏠 Dashboard with hospital statistics
- 👨‍⚕️ Doctor management
- 🧑‍🤝‍🧑 Patient management
- 📅 Appointment management
- 🔗 REST-style API endpoints
- 🗄️ MySQL database integration
- 💻 Web-based user interface

## 🛠️ Technologies Used

- **Frontend:** HTML5, CSS3, JavaScript
- **Backend:** Java, Java HTTP Server, JDBC
- **Database:** MySQL
- **Tools:** VS Code, Git, GitHub

## 📂 Project Structure

```text
Hospital-Management-System/
├── frontend/
│   ├── index.html
│   ├── script.js
│   └── style.css
├── lib/
│   └── mysql-connector-j-9.4.0.jar
├── src/
│   ├── ApiServer.java
│   ├── Appointment.java
│   ├── DatabaseConnection.java
│   ├── Doctor.java
│   ├── Main.java
│   └── Patient.java
├── .gitignore
└── README.md
```

## 🗄️ Database Setup

Make sure MySQL is installed and running.

```sql
CREATE DATABASE hospital;
USE hospital;
```

### Patients Table

```sql
CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(20),
    phone VARCHAR(20),
    disease VARCHAR(255)
);
```

### Doctors Table

```sql
CREATE TABLE doctors (
    doctor_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100),
    phone VARCHAR(20)
);
```

### Appointments Table

```sql
CREATE TABLE appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    reason VARCHAR(255),
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)
);
```

## ⚙️ Database Configuration

The application uses an environment variable for the MySQL password.

```bash
export DB_PASSWORD='YOUR_MYSQL_PASSWORD'
```

Do not commit your actual database password to GitHub.

## 🚀 How to Run

### 1. Clone the Repository

```bash
git clone https://github.com/sathvikaartham/Hospital-Management-System.git
cd Hospital-Management-System
```

### 2. Compile the Java Backend

```bash
javac -cp "lib/mysql-connector-j-9.4.0.jar" -d out src/*.java
```

### 3. Start the Java Backend

```bash
java -cp "out:lib/mysql-connector-j-9.4.0.jar" ApiServer
```

Backend:

```text
http://localhost:8080
```

### 4. Start the Frontend

Open another terminal:

```bash
cd frontend
python3 -m http.server 5500
```

Open:

```text
http://localhost:5500
```

## 🔗 API Endpoints

- `GET /api/patients`
- `GET /api/doctors`
- `GET /api/appointments`

Example:

```text
http://localhost:8080/api/patients
```

## 🔄 Application Architecture

```text
             ┌─────────────────────┐
             │      Frontend       │
             │  HTML/CSS/JavaScript│
             └──────────┬──────────┘
                        │
                        │ HTTP Requests
                        ▼
             ┌─────────────────────┐
             │    Java Backend     │
             │    HTTP API Server  │
             └──────────┬──────────┘
                        │
                        │ JDBC
                        ▼
             ┌─────────────────────┐
             │   MySQL Database    │
             │      Hospital       │
             └─────────────────────┘
```

## 📊 Main Modules

### Dashboard
Provides an overview of patients, doctors, and appointments.

### Patient Management
Manages patient name, age, gender, phone, and disease information.

### Doctor Management
Stores doctor name, specialization, and phone information.

### Appointment Management
Manages patient, doctor, date, time, and appointment reason.

## 🔐 Security

- Database credentials are supplied through environment variables.
- Compiled Java files are excluded using `.gitignore`.
- Sensitive credentials should never be committed to GitHub.

## 🔮 Future Improvements

- User authentication and login
- Role-based access control
- Search and filtering
- Patient medical history
- Doctor availability management
- Appointment notifications
- Cloud database integration
- Online deployment
- Improved mobile responsiveness

## 👩‍💻 Author

**Sathvika Artham**

GitHub: https://github.com/sathvikaartham

## 📄 License

This project is intended for educational and project-development purposes.
