Welcome to the repository for the DevTrack Auth/Resource Server, a central piece of the DevTrack web application that ensures secure user authentication and authorization while serving as a resource server. It's designed to demonstrate a robust and secure implementation of user session management using JWT tokens and provides optimized access to resources.

## Features
- User Authentication: Secure login functionality that validates user credentials and initiates their session.
- JWT Token Management: Generates and distributes access and refresh tokens securely via HTTP-only cookies to maintain session integrity.
- Resource Server: Facilitates access to protected resources, ensuring that users can interact with their resources in a secure environment.
- Authorization Server: Acts as a gatekeeper to validate JWT tokens and authorize access to various endpoints based on token validity.
- Seamless Session Persistence: Employs refresh tokens to keep users logged in across sessions, minimizing the need for frequent logins.
- Image Optimization: Provides a dual approach to image handling by offering performance-optimized thumbnails for browsing and the ability to download full-sized images as needed.
  
## Main Technologies
- Spring Boot 3: A modern, feature-rich framework that simplifies the development of stand-alone, production-grade Spring-based applications.
- Spring Security: A powerful, highly customizable authentication and access-control framework to secure Spring-based applications.
- PostgreSQL: A robust, SQL-compliant and feature-rich database system utilized for storing and managing user data and application resources securely.
- JWT (JSON Web Tokens): A compact, URL-safe means of representing claims to be transferred between two parties, perfect for handling the authentication tokens.

## API Overview
The server exposes RESTful endpoints responsible for handling authentication and resource serving. Here's a brief overview of some of the core functionalities:
- Authentication routes for handling user login processes and token management.
- Resource endpoints to provide access to user-specific resources such as tasks and image data.
- Endpoints for optimized image retrieval and full-size image downloads.

## Custom Error Handling
The application includes custom exceptions and error response handling to provide informative feedback to API consumers, aiding in the quick resolution of issues encountered during interaction with the server.


## Important Note

**Please read this before accessing the website!**

**Server Startup Delay:** 

Please note that the resource-auth server for this website is hosted on a free instance and will temporalily shut down when its not used. As a result, there might be a delay of approximately 3 minutes when accessing the website for the first time, however after that everything will work as intended. The free hosting instance may spin down after a period of inactivity, causing this initial delay. Subsequent accesses to the website will be instant once the server is up and running. I apologize for any inconvenience caused by this delay and appreciate your patience. 

## Deployed Application
‼️Read the important note section before accessing.‼️

The application is deployed and available for demonstration purposes. You can interact with the live version at the following URL: [DevTrack web app link](https://devtrack.dedyn.io).

If you dont want to sign up use these credentials:
- Username: user
- Password: pass123!

Loggin in with these credentials gives you access to a prepopulated kanban board with predefined tasks and images, allowing you to explore the functionality of DevTrack without having to create an account or add tasks manually. To view or edit the details of a task just double click on the task.

## Screenshots

![image](https://github.com/PanosEko/devtrack-frontend/assets/93736094/3bfc6d6a-f8f0-42de-986f-da31e4fc5e15)

![image](https://github.com/PanosEko/devtrack-frontend/assets/93736094/50ccdb14-7ddc-4d08-8174-06f993845aa4)

![image](https://github.com/PanosEko/devtrack-frontend/assets/93736094/5c9378d5-98f6-472e-aed6-971a7fe6e855)

![image](https://github.com/PanosEko/devtrack-frontend/assets/93736094/3efc0575-c523-4c8f-86ac-3b2fdef54d88)

![image](https://github.com/PanosEko/devtrack-frontend/assets/93736094/45ee7627-9bee-46d7-9611-ee43a862f2bd)
