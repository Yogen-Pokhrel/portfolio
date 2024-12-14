# Subdomain Manager Service

The **Subdomain Manager** service is a key component of the portfolio building application. It is responsible for managing and creating subdomains for users upon registration. Built with **NestJS** and integrated with **Kafka**, this service listens for specific events (e.g., user-registration) and performs actions such as creating subdomains and updating DNS records.

## Key Features

- **Subdomain Creation**: Automatically creates a subdomain for each user upon successful registration.
- **Event-Driven Architecture**: Uses Kafka to listen to events (e.g., user-registration) and trigger subdomain creation.
- **Dynamic Subdomain Management**: Supports adding, updating, and removing subdomains based on user activity.
- **DNS Integration**: Automatically updates DNS records (e.g., using Route 53 or another DNS provider) for the created subdomains.
- **Lightweight and Scalable**: Built using NestJS, ensuring a scalable and modular microservice.

## Technologies Used

- **NestJS**: A Node.js framework used to build the service in a modular, maintainable way.
- **Kafka**: Used to handle event-driven communication, allowing the service to react to specific user-related events like registration.
- **DNS Provider**: Integrates with DNS providers to programmatically create and manage subdomains.

## Use Case

1. When a user registers, the **Subdomain Manager** listens for the `user-registration` event in Kafka.
2. It creates a subdomain using the user’s details (e.g., username or custom subdomain).
3. It updates the DNS records for the subdomain, ensuring it's reachable.
4. After successful creation, it emits a `subdomain-creation-success` event or a `subdomain-creation-failure` event.

## Benefits

- **Automatic Subdomain Creation**: Automatically creates a personalized subdomain for each user.
- **Event-Driven**: Efficiently handles actions based on Kafka events, promoting decoupled architecture.
- **Scalability**: Can easily scale to handle many user registrations and subdomain creations.
- **Integration with DNS**: Automatically manages DNS records to ensure each subdomain is accessible.

## Installation

```bash
$ npm install
```

## Running the app

```bash
# development
$ npm run start

# watch mode
$ npm run start:dev

# production mode
$ npm run start:prod
```

## Test

```bash
# unit tests
$ npm run test

# e2e tests
$ npm run test:e2e

# test coverage
$ npm run test:cov
```