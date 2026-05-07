# Processing Service

Part of a distributed **Industry 4.0 Industrial Pipeline Simulator**, built with an event-driven microservices architecture.

---

## 🧠 System Overview

This project simulates a real industrial production pipeline, where independent services collaborate to produce goods.

Pipeline Flow:

Raw Material → Processing → Component Production → Product Assembly

Each step operates as an isolated microservice, communicating through Kafka events.

---

## 🎯 Function of this Service

The **Processing Service** is responsible for transforming raw materials into usable industrial materials.

It acts as the second stage of the production pipeline, linking raw material extraction to component manufacturing.

Examples:

- Iron ore → Steel
- Sand → Glass
- Latex → Rubber

---

## ⚙️ Responsibilities

- Consume raw material events from Kafka
- Apply transformation pipelines to raw materials
- Validate transformation rules and compatibility
- Persist processed materials
- Publish processed material events to subsequent services

---

## 🔄 Pipeline Position
[Raw Material Service] → [Processing Service] → [Component Service] → [Assembly Service]

---

## 📡 Event-Driven Communication

### Events Consumed

- `RAW_MATERIAL_EXTRACTED`

### Events Produced

- `MATERIAL_PROCESSED`

---

## 📦 Event Structure

### Entry Event

```
{
"eventId": "uuid",

"eventType": "RAW_MATERIAL_EXTRACTED",

"timestamp": 1710000000,

"sourceService": "raw-material-service",

"targetService": "processing-service",

"payload": {

"name": "Iron Ore",

"quantity": 10

}
}
```

### Exit Event

```
{
"eventId": "uuid",

"eventType": "MATERIAL_PROCESSED",

"timestamp": 1710000000,

"sourceService": "processing-service",

"targetService": "component-service",

"payload": {

"name": "Steel",

"quantity": 8

}
}
```

## ⏱️ Processing Pipeline (Latency Simulation)

Material transformation is performed in time-based steps.

Example: Steel Production
```
[
{ "name": "FOUNDRY", "durationMs": 8000 },

{ "name": "REFINING", "durationMs": 6000 }

]
```

This ensures realistic delays in industrial processing.

POST /processing/start
```
{ "material": "Iron Ore",

"quantity": 10

}
```

**Note:** In normal operation, processing is triggered automatically via Kafka events.

## 🔄 Internal Flow

Receive Kafka event (RAW_MATERIAL_EXTRACTED)
Validate if the material can be processed
Execute transformation pipeline (with delay)
Convert raw material into processed material
Persist the result in the database
Publish MATERIAL_PROCESSED event

## 🗄️ Data Ownership

This service follows the principles of microservices architecture:

## Own database
- No direct access to data from other services
- Communication strictly via Kafka events

## 🧱 Technologies
- Java + Spring Boot
- Apache Kafka
- PostgreSQL
- Docker

## Running the Service
- docker-compose up --build

## 🧠 Key Concepts Demonstrated

- Event-driven processing pipelines
- Data transformation in distributed systems
- Decoupling of services through communication Asynchronous
- Latency simulation in industrial workflows

## Other Services:
- [raw-material-service](https://github.com/Valdemar-Andrade/raw-material-service.git)
- [component-service](https://github.com/Valdemar-Andrade/component-service.git)

## 👤 Developer
- GitHub: [@Valdemar-Andrade]
- LinkedIn: [Valdemar Andrade](https://www.linkedin.com/in/valdemar-andrade-8b0b45189)
