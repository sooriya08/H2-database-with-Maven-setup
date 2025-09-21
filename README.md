# H2 JDBC Demo

A simple Java project demonstrating H2 Database integration using Maven.  
Includes JDBC setup, table creation, data insertion, and querying in an in-memory or file-based H2 database.

## Features
- In-memory H2 database (`jdbc:h2:mem:testdb`) or file-based database (`jdbc:h2:~/testdb`)
- Table creation and auto-increment primary keys
- Data insertion and simple SQL queries
- H2 Web Console support for interactive querying

## Prerequisites
- Java 17 or higher
- Maven 3.x
- Optional: Web browser to access H2 console

## Project Structure
h2-jdbc-demo/
├── src/main/java/com/example/Main.java
├── pom.xml
└── README.md


## Setup & Run

1. **Clone the repository**
```bash
git clone https://github.com/<your-username>/h2-jdbc-demo.git
cd h2-jdbc-demo

2. Build the project with Maven

mvn package

3. Run the main class

mvn exec:java -Dexec.mainClass="com.example.Main"

4.Access H2 Web Console (if enabled in code)

http://localhost:8082
User: sa
Password: (leave blank)

Notes :

In-memory DB (mem:testdb) is cleared when the JVM stops.

For persistent data, switch to file-based DB (jdbc:h2:~/testdb).

Make sure port 8082 is free if using the web console.
