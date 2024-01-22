# TypeTask-Pro's Official API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

#### This project has been created to be [TypeTask Pro's](https://github.com/MartinGHaas/TypeTask-Pro) official API!

It's been developed with the intention to learn more about Spring Boot Web using  
the latest features.

This project was built in **Java, Java Spring, PostgreSQL and JWT for authentication.** 

## Docs Summary
 - [Installation Guide](#installation-guide)
 - [Starting the Application](#starting-the-application)
 - [Endpoints](#endpoints)
 - [Roles](#authentication-roles)
 - [Database](#database)
 - [Contributing](#contributing)

### Installation Guide
 1. Clone this repo with:
```
git clone git@github.com:MartinGHaas/TypeTaskPro-Backend.git
```
 2. Install Maven dependencies
 3. Install [PostgreSQL](https://www.postgresql.org/download/)
 4. Change `application.yml` to your own Settings

### Starting the Application
 1. Start the Application
 2. Access the API locally at *[localhost:8080](http://localhost:8080)*
 3. If everything runs correctly. You should've been redirected here!

### Endpoints

*Provided endpoints until now:*

```
POST /auth/register - Register a new user into the App

POST /aut/login - Login as a user into the App
```

### Authentication Roles

*Roles until now:*
```
USER - Default user role (low level of hierarchy)
ADMIN - Admin role in the application (high level of hierarchy)
```

### Database

This project uses [PostgreSQL](https://www.postgresql.org) as database!  
Before running this application, make sure to change `application.yml` on `.\src\main\resources\application.yml`  
to configure your own Postgres data **(username & password & host & port)**.

### Contributing

#### You are free to contribute to this project!  
  
If you find any problems or want to suggest a new feature you  
can open a Pull Request with a short explanation of your changes.
