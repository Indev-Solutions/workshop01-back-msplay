# Workshop Bet

This project focuses on providing the tools for users to interact with the betting platform, making their plays.

## Technologies Used

* Spring Boot
* Java
* Docker
* Docker Compose
* PostgreSQL

## Prerequisites

* [JDK 17 or higher]
* Docker
* Docker Compose

## Project Structure

	.
	├── deploy               				# Contains resources used during deployment.
	├── src                  				# Source files
	│   ├── main					
	│   │	 ├── java			          		# Standard location for the **main source code**.
	│   │	 ├── resources       				# This directory holds the resource files that the application needs to run
	│   │	 │	  ├── db       		
	│   │	 │	  │	  ├── migrations  		# Flyway migration files
	│   │	 │	  ├── messages.properties	# This file contains the default internationalization (i18n) messages for the application.
	│   ├── test          		
	│   │	 ├── java          				# This directory is dedicated to the **source code for the automated tests****.
	│   │	 ├── resources       				# Load and stress test
	│   │	 │	  ├── db       		
	│   │	 │	  │	  ├── migrations  		# flyway migration files for test purposes.
	├── Dockerfile
	├── docker-compose.ym
	├── pom.xm

	└── README.m

## Setup

1.  **Clone the repository:**

    ```bash
    git clone git@github.com:Indev-Solutions/workshop01-back-msplay.git
    cd workshop01-back-msplay
    git checkout develop
    ```

2.  **Build the Spring Boot application:**

    ```bash
    ./mvnw clean install
    ```

3.  **Run Docker Compose:**

    ```bash
    docker-compose up -d
    ```

    This will start the database in a Docker container.

4.  **Run the Spring Boot application:**

    ```bash
    java -jar target/workshop-play-[version].jar
    ```

    Or, if you prefer to use Maven:

    ```bash
    ./mvnw spring-boot:run
	```

## API Endpoints

* `GET /workshop/plays`: Retrieves the list of latest plays.
* `POST /workshop/plays`: Creates a new play.

## Testing

* To run unit tests:

  ```bash
    ./mvnw test
  ```
  