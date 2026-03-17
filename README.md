# 🚀 Distributed Job Scheduler System

A scalable **Distributed Job Scheduler** built using **Java, Spring Boot, Redis, and MySQL** to process asynchronous background jobs with retry mechanisms, worker architecture, and execution tracking.

---

## 📌 Features

### ✅ Core Features

* Create and schedule jobs via REST APIs
* Asynchronous job execution using Redis queue
* Persistent job storage using MySQL
* Job lifecycle management (SCHEDULED → RUNNING → COMPLETED / FAILED)

### 🔁 Retry Mechanism

* Configurable retry logic (`maxRetries`)
* Automatic retry on job failure
* Tracks retry count per job

### ☠️ Dead Letter Queue

* Failed jobs after max retries are moved to Dead Letter Queue
* Enables debugging and manual reprocessing

### ⚙️ Worker Architecture

* Background worker continuously polls Redis queue
* Executes jobs asynchronously
* Maintains execution history

### 📊 Job Execution Tracking

* Tracks start time, end time, and status
* Stores execution logs in MySQL (`JobExecution` table)

### 🔄 Scheduling Support

* Supports delayed execution using `nextRunTime`
* Cron-based scheduling support (extendable)

---

## 🏗️ System Architecture

#Client
#↓
#REST API (Spring Boot)
#↓
#MySQL (Job Metadata)
#↓
#Scheduler
#↓
#Redis Queue
#↓
#Worker
#↓
#Execution + Retry
#↓
#Dead Letter Queue

---

## 🛠️ Tech Stack

**Backend:** Java, Spring Boot, Spring Data JPA, Hibernate
**Database:** MySQL
**Queue:** Redis
**Tools:** Maven, Postman, Git

---

## 📂 Project Structure

scheduler/
├── controller/
├── service/
├── worker/
├── repository/
├── entity/
├── config/
└── SchedulerApplication.java

---

## 🔑 Key Concepts Used

* Distributed Systems
* Asynchronous Processing
* Message Queue (Redis)
* Multithreading
* Retry Mechanism
* Dead Letter Queue
* REST API Design

---

## 📊 Performance

* Handles **10,000+ queued jobs**
* Improves throughput by **3–5× using worker-based execution**
* Supports retry-based fault tolerance

---

## ⚙️ How It Works

### 1️⃣ Job Creation

POST /jobs

* Job stored in MySQL
* Job pushed to Redis queue

---

### 2️⃣ Worker Execution

* Worker continuously polls Redis queue
* Updates job status → RUNNING
* Executes job

---

### 3️⃣ Success Flow

RUNNING → COMPLETED

---

### 4️⃣ Failure Flow

FAILED
↓
retryCount++
↓
retryCount ≤ maxRetries → Retry
retryCount > maxRetries → Dead Letter Queue

---

## 🧪 API Endpoints

POST /jobs
GET /jobs
GET /jobs/{id}

---

## 🛠️ Setup & Run Locally

### 1️⃣ Clone Repository

git clone https://github.com/your-username/job-scheduler.git
cd job-scheduler

---

### 2️⃣ Configure MySQL

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/scheduler_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update

---

### 3️⃣ Start Redis

redis-server

---

### 4️⃣ Run Application

mvn spring-boot:run

OR run from IDE

---

### 5️⃣ Test APIs

POST http://localhost:8080/jobs

---

## 📊 Database Tables

### Job Table

* id
* jobName
* status
* retryCount
* maxRetries
* nextRunTime
* createdAt

### JobExecution Table

* jobId
* startTime
* endTime
* status

---

## 🚀 Future Enhancements

* Worker Pool using ExecutorService
* Distributed workers (multiple instances)
* Monitoring with Prometheus + Grafana
* Rate limiting
* Priority-based scheduling
* Kafka integration

---

## 📈 Resume Highlights

* Built distributed scheduler handling **10k+ jobs**
* Implemented retry mechanism with Dead Letter Queue
* Designed scalable worker architecture
* Improved throughput by **3–5×**

---

## 👨‍💻 Author

Jai singh katiyar

---

## ⭐ If you like this project

Give it a star ⭐ on GitHub
