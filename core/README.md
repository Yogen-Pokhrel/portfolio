# Portfolio Core Module

The **Portfolio Core Module** provides foundational components and utilities used across all microservices in the Portfolio Builder application. This module includes security configurations, exception handling, API response structure, custom exception classes, and utility functions that can be leveraged by other microservices.

## Overview

This module acts as a shared library for common functionality, ensuring consistency and reusability across all microservices in the system. It is designed to be added as a dependency in the `POM` files of other microservices, enabling them to utilize the following core features:

- **Base Security Configurations**: Pre-configured authentication and authorization settings, including Keycloak integration.
- **Global Exception Handlers**: Standardized exception handling for API responses across all services.
- **API Response Structure**: A consistent structure for API responses, ensuring uniformity in success and error handling.
- **Custom Exception Classes**: Common exception classes for handling errors and improving code readability.
- **Utility Functions**: Common utility methods to simplify tasks such as validation, formatting, and other repetitive actions.

## Features

- **Security Configuration**: Pre-built security settings for Keycloak integration.
- **Exception Handling**: Centralized mechanism to handle exceptions and send structured API responses.
- **Standardized API Responses**: Consistent response format for success and error scenarios.
- **Reusable Utility Functions**: Common methods that can be used throughout all services to avoid code duplication.

## Usage

To use this module in any of the microservices, simply add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.portfolio</groupId>
    <artifactId>portfolio-core</artifactId>
    <version>1.0.0</version>
</dependency>
