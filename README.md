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

## Setup

1.  **Clone the repository:**

    ```bash
    git clone git@github.com:Indev-Solutions/workshop01-back-msplay.git
    cd workshop01-back-msplay
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
  