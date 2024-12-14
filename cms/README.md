# Portfolio CMS Service

Welcome to the **Portfolio CMS** service! This microservice is responsible for managing and organizing user content on the portfolio website. It enables users to create, update, and customize their portfolio sections, such as project listings, themes, and other user-provided content.

## Overview

The **Portfolio CMS** service allows users to manage their portfolios by providing functionality to:

- **Create and edit portfolio content**: Manage portfolio sections such as projects, achievements, and other personal details.
- **Custom theme management**: Users can configure and customize the look and feel of their portfolio.
- **File management**: Handle uploaded assets such as images, documents, and project files.
- **Content structure**: Organize the content with categories and tags to make it easily navigable.
- **Authorization and access control**: Integrated with **Keycloak** for authentication and authorization, ensuring users can only access and modify their own content. Content on the front-end are filtered by the subdomain.

This service is part of a larger **Portfolio Builder** application designed to help professionals build and showcase their portfolios online.

## Key Features

- **Portfolio Sections**: Users can create sections like "Projects", "Achievements", "Skills", and more.
- **Theme Configuration**: Allows users to change the appearance of their portfolio by selecting and customizing themes.
- **Project Management**: Users can upload and update details about their projects, including descriptions, images, and links.
- **Media Upload**: Manage uploaded files, such as resumes, project images, and other assets.
- **Access Control**: Fine-grained permissions for users and administrators to manage content and roles.
- **REST API**: Exposes a RESTful API for interacting with portfolio content (create, read, update, delete).
- **Integration with Keycloak**: Secure user authentication and role-based authorization.

## API Endpoints

The **Portfolio CMS** service exposes the following key endpoints:

- `GET /content/sections`: Retrieve a list of all available sections (e.g., projects, skills).
- `POST /content/sections`: Create a new content section for the user’s portfolio.
- `GET /content/{sectionId}`: Get content for a specific section (e.g., project details).
- `PUT /content/{sectionId}`: Update content for a specific section.
- `DELETE /content/{sectionId}`: Delete a content section.
- `POST /content/upload`: Upload files (images, documents, etc.) related to content.
- `GET /content/themes`: Get the list of available themes for the user’s portfolio.
- `PUT /content/themes`: Update the selected theme for the user's portfolio.

## Technology Stack

- **Backend**: Java, Spring Boot
- **Database**: PostgreSQL (for storing content data)
- **File Storage**: AWS S3 (or another cloud file storage provider)
- **Authentication**: Keycloak (OAuth2, OpenID Connect)
- **APIs**: RESTful API design using JSON
- **Caching**: Redis (for caching frequently accessed data)
- **Deployment**: Docker, Kubernetes for containerized deployment

## Requirements

- **Java 17+** (for development and building the service)
- **PostgreSQL** (for local development and testing)
- **Docker** (for containerization)
- **Keycloak** (for authentication, if you wish to integrate with Keycloak locally)
- **AWS CLI** (if using AWS S3 for file storage)
