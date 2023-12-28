# DevTrack Auth/Resource Server

Welcome to the repository for the DevTrack Auth/Resource Server, a central piece of the DevTrack web application that ensures secure user authentication and authorization while serving as a resource server. It's designed to demonstrate a robust and secure implementation of user session management using JWT tokens and provides optimized access to resources such as tasks and images.

Features
User Authentication: Secure login functionality that validates user credentials and initiates their session.
JWT Token Management: Generates and distributes access and refresh tokens securely via HTTP-only cookies to maintain session integrity.
Resource Server: Facilitates access to protected resources, ensuring that users can interact with their tasks and images in a secure environment.
Authorization Server: Acts as a gatekeeper to validate JWT tokens and authorize access to various endpoints based on token validity.
Seamless Session Persistence: Employs refresh tokens to keep users logged in across sessions, minimizing the need for frequent logins.
Image Optimization: Provides a dual approach to image handling by offering performance-optimized thumbnails for browsing and the ability to download full-sized images as needed.
Main Technologies
Spring Boot 3: A modern, feature-rich framework that simplifies the development of stand-alone, production-grade Spring-based applications.
Spring Security: A powerful, highly customizable authentication and access-control framework to secure Spring-based applications.
Java 20: The latest version of Java, ensuring the use of up-to-date language features and performance enhancements.
JWT (JSON Web Tokens): A compact, URL-safe means of representing claims to be transferred between two parties, perfect for handling the authentication tokens.
PostgreSQL: A robust, SQL-compliant and feature-rich database system utilized for storing and managing user data and application resources securely.
API Overview
The server exposes RESTful endpoints responsible for handling authentication and resource serving. Here's a brief overview of some of the core functionalities:

Authentication routes for handling user login processes and token management.
Resource endpoints to provide access to user-specific resources such as tasks and image data.
Endpoints for optimized image retrieval and full-size image downloads.
Custom Error Handling
The application includes custom exceptions and error response handling to provide informative feedback to API consumers, aiding in the quick resolution of issues encountered during interaction with the server.

Security Measures
The Auth/Resource Server employs industry-standard security practices, including:

Secure transmission of JWT tokens within HTTP-only, Secure cookies to mitigate XSS and CSRF attacks.
Comprehensive use of Spring Security for authentication and authorization, ensuring robust access control.
Deployed Application
The application is deployed and available for demonstration purposes. You can interact with the live version at the following URL: Deployed Application Link

Contact
For any inquiries or suggestions regarding the DevTrack Auth/Resource Server, please reach out through [Your Contact Information].

Acknowledgments

Acts as an authorization sever and a resource server. Made with Spring Boot and Spring Security.

The DevTrack Auth Resource Server is a component of the DevTrack web application that handles user authentication, authorization, and acts as a resource server. It is responsible for generating JWT tokens, validating user credentials, and providing secure access to protected resources.

## Features
- User authentication: Authenticate users through secure login using credentials.
- JWT token generation: Generate JSON Web Tokens (JWT) upon successful authentication.
- Authorization server: Verify the validity of JWT tokens and grant access to protected resources.
- Resource server: Provide endpoints and APIs to access and interact with data from the database.
- Secure communication: Employ industry-standard security measures to protect sensitive user information and ensure secure data transmission.

## Technologies Used
- Spring Boot: Java-based framework for building server-side applications.
- Spring Security: Robust security framework for managing authentication and authorization.
- JWT: JSON-based open standard for creating access tokens.
- PostgreSQL: Utilize a suitable database system for storing user-related data securely.

## Important Note

**Please read this before accessing the website!**

**Server Startup Delay:** 

Please note that the resource-auth server for this website is hosted on a free instance and will temporalily shut down when its not used. As a result, there might be a delay of approximately 3 minutes when accessing the website for the first time, however after that everything will work as intended. The free hosting instance may spin down after a period of inactivity, causing this initial delay. Subsequent accesses to the website will be instant once the server is up and running. I apologize for any inconvenience caused by this delay and appreciate your patience. 

## Live Demo
‼️Read the important note section before accessing.‼️

You can access a live demo of the DevTrack web application here: [DevTrack web app link](https://devtrack.dedyn.io).

If you dont want to sign up use these credentials:
- Username: user
- Password: pass123!

Loggin in with these demo credentials gives you access to a prepopulated kanban board with predefined tasks and images, allowing you to explore the functionality of DevTrack without having to create an account or add tasks manually. To view or edit the details of a task just double click on the task.
