# Develab Assignment

Assignment from Develab for parking availability

## Table of Contents

- [Project Name](#project-name)
    - [Table of Contents](#table-of-contents)
    - [Features](#features)
    - [Getting Started](#getting-started)
        - [Prerequisites](#prerequisites)
        - [Installation](#installation)
        - [Additional Info](#AdditionalInfo)

## Features

API provided

- /carparks/nearest

## Getting Started

This project use Java 17. So, please install Java 17 1st in your machine. We also utilize docker for development environment.

### Prerequisites

- Java 17
- Maven
- Docker

### Installation

- Run ```docker compose up``` command in the project directory
  - by default, there is no password for database. But you can change it in docker-compose.yml
- Once the infra up, run maven command ```mvn clean install exec:java``` to import the parking info to the database
  - be noted that building the project will refresh the db since this is only for demo purposes
- Run the application
  - Wait one minute (or time define in the application.properties) for worker to upload parking availability into the database

### AdditionalInfo

- We are also using Lombok. So make sure to enable Lombok annotation processor



