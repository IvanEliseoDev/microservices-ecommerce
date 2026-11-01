# Microservices Architecture for E-Commerce Platform

## Overview

This repository contains an asynchronous and synchronous microservices-based e-commerce platform built with Spring Boot (Java 21) and containerized using Docker. The primary goal of this project is to model and implement a scalable, decoupled architecture, focusing on service isolation, database-per-service pattern (MongoDB and PostgreSQL), and event-driven communication.

---

## Technical Stack

* **Core Framework & Runtime:** Java 21 (JDK 21) / Spring Boot 3+
* **Database Infrastructure:** MongoDB (NoSQL) & PostgreSQL (Relational)
* **Message Broker:** RabbitMQ
* **Service Discovery:** Spring Cloud Netflix Eureka (`discovery-server`)
* **Containerization:** Docker & Docker Compose

---

## Architecture & Project Structure

The project is structured as a mono-repository containing isolated services:

```text
microservices-ecommerce/
│
├── discovery-server/      # Eureka Service Registry for dynamic instance discovery
├── product-service/        # Handles catalog management and MongoDB interactions
├── order-service/          # Processes transaction workflows and order placement (PostgreSQL)
├── inventory-service/      # Manages stock checking and allocation
└── notification-service/   # Asynchronous notification processor via RabbitMQ
```
---
## Communication Patterns
Synchronous Communication (REST)
Direct HTTP/REST endpoints are utilized when an immediate response is required. For instance, the order-service executes synchronous checks against the inventory-service during order processing to verify stock availability before confirming a transaction.

Asynchronous Communication (Event-Driven)
RabbitMQ is integrated to decouple non-blocking domain events. Upon successfully placing an order, order-service publishes an event payload to an exchange. The notification-service consumes this message to process secondary tasks (e.g., sending confirmation emails) without increasing latency on the client request thread.

---
## Prerequisites
* Java Development Kit (JDK) 21 

* Apache Maven 3.8+

* Docker Desktop & Docker Compose CLI

---
## Setup & Execution
1. Environment Configuration
Copy the sample environment file in each service directory that requires runtime variables (e.g., product-service):


cp product-service/.env.example product-service/.env
2. Infrastructure Deployment
To start all infrastructure dependencies (databases, message brokers) alongside the microservices:

docker-compose up -d --build

3. Service Verification
Check running containers:

docker ps

---
## Project Status
This repository serves as a technical showcase and hands-on implementation of microservice design patterns, event-driven integration, and cloud-native practices in Java/Spring Boot.