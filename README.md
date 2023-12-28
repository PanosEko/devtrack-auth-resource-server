# DevTrack Auth/Resource Server

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
