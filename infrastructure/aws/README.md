# AWS Placeholders

This folder is intentionally lightweight. Use it to discuss how the application
would be deployed without turning the interview project into an infrastructure
project.

Possible production shape:

- ECS or EKS for the Spring Boot backend
- S3 and CloudFront for the React frontend
- RDS PostgreSQL for persistence
- SNS or SQS for service-completed notifications
- Secrets Manager or SSM Parameter Store for configuration

Training exercise:

1. Add an application port for service-completed events.
2. Keep the local implementation as a no-op or in-memory publisher.
3. Add an optional AWS adapter behind configuration.
