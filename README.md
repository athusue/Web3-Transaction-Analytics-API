# Web3 Transaction Analytics API

Simple backend service built with Java and Spring Boot for processing and analyzing blockchain transaction data.

This project provides REST API endpoints for:

- transaction filtering
- pagination
- transaction lookup by hash
- wallet activity analytics
- top sender statistics
- cached transaction loading

The goal of this project was to practice backend architecture, API development, data processing, and service layer design.

---

# Features

## Transactions API

Supports:

- pagination
- minimum amount filtering
- maximum amount filtering

Example:

```http
GET /transactions?minAmount=100&maxAmount=1000&page=0&size=10
Transaction Lookup

Find a transaction by hash.

Example:

GET /transactions/0x123...
Stats API

Returns:

total transaction volume
largest transaction
top sender

Example:

GET /stats

Supports filters:

GET /stats?minAmount=100
Top Senders

Returns the most active senders by transferred volume.

Example:

GET /top-senders
Cache Reload

Reloads transactions from the JSON file.

Example:

GET /cache/clear
Tech Stack
Java
Spring Boot
Jackson
Maven
  ```
  ```
Project Structure
src/main/java/com/example/web3backend
├── model
│   ├── Transaction.java
│   └── Stats.java
│
├── service
│   └── TransactionService.java
│
├── TransactionController.java
└── Web3backendApplication.java
How It Works
  ```

Transactions are loaded from:

src/main/resources/data.json

The service caches parsed transactions in memory to avoid unnecessary file reads on every request.

Filtering and pagination are handled in the service layer.

Run Locally
Clone repository
git clone <repo-url>
Start project
mvn spring-boot:run

Server starts on:
  ```
http://localhost:8080
API Endpoints
Method	Endpoint	Description
GET	/transactions	Get paginated transactions
GET	/transactions/{hash}	Find transaction by hash
GET	/stats	Get transaction statistics
GET	/top-senders	Get top senders
GET	/cache/clear	Clear cached data
  ```
Example Response
  ```
{
  "tx_hash": "0xabc123",
  "from": "0xwallet1",
  "to": "0xwallet2",
  "amount": 250.5
}
  ```
Notes

This project focuses on backend logic and API design rather than database integration.

The current implementation uses JSON as a local data source for simplicity and learning purposes.
