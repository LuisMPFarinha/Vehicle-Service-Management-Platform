# Vehicle Service Management Platform

Training project for a Volkswagen Group Digital Solutions interview.

This repository is intentionally a little more complete than an empty scaffold:
it has build wiring, application boundaries, placeholder APIs, database setup,
Docker, CI, and disabled training tests. The business behavior is left as TODOs
so each feature can be implemented test-first during practice.

## Stack

- Backend: Java 21, Spring Boot, Maven, PostgreSQL, Flyway
- Frontend: React, TypeScript, Vite, React Router, Vitest, React Testing Library
- Testing: JUnit 5, Mockito, Testcontainers
- Infrastructure: Docker Compose, GitHub Actions, AWS adapter placeholders

## Java Version

Java 21 is used as the project target because it is an LTS release and remains a
comfortable enterprise baseline for Spring Boot teams. Java 25 is also an LTS
release and can be used as an upgrade exercise later.

To try Java 25, change:

- `backend/pom.xml`: `<java.version>21</java.version>`
- `.github/workflows/ci.yml`: `java-version: '21'`
- `backend/Dockerfile`: the JDK/JRE base image tags

## Quick Start

Start PostgreSQL:

```bash
docker compose up postgres
```

Run the backend:

```bash
cd backend
mvn spring-boot:run
```

Run the frontend:

```bash
cd frontend
pnpm install
pnpm dev
```

Run backend tests:

```bash
cd backend
mvn test
```

Run frontend tests:

```bash
cd frontend
pnpm test
```

## Training Rules

- Prefer test-first implementation.
- Keep the modular monolith boundaries clear.
- Start with the smallest failing test.
- Explain what layer owns each rule.
- Commit small, understandable changes.
- Refactor deliberately after behavior is covered.

## Pair-Programming Exercises

Each exercise should fit roughly 30-60 minutes.

1. Create a vehicle
   - Add validation, persistence, and `POST /api/vehicles`.
   - Discuss what belongs in DTO validation versus domain rules.

2. Open a service request
   - Start with a failing application-service or domain test.
   - Persist a request linked to an existing vehicle.

3. Prevent duplicate active requests
   - A vehicle cannot have two identical active requests.
   - Discuss domain rule, application check, database constraint, and race conditions.

4. Assign a technician
   - Only active requests may be reassigned.
   - Include invalid-state handling.

5. Complete a request
   - Require an assigned technician.
   - Record `completedAt`.
   - Prevent transition back to `OPEN`.

6. List and filter requests
   - Support filters for status, priority, and vehicle registration.
   - Discuss query design for larger datasets.

7. Build the React request list
   - Cover loading, empty, success, and error states.
   - Keep UI state explicit and easy to reason about.

8. Add a request form
   - Write component tests before implementation.
   - Decide where client-side validation should stop.

9. Refactor deliberately
   - Start from an awkward service.
   - Improve names, responsibilities, and test readability.

10. Add one AWS integration
    - Publish a service-completed event through a port interface.
    - Keep SNS/SQS/S3 adapter code optional and replaceable.

## Interview Prompts To Practise

- Requests now need an estimated completion date.
- Technicians may only have five active assignments.
- The endpoint is slow when there are 100,000 requests.
- We need to notify the owner when work is completed.
- This test is flaky.
- This class has too many responsibilities.
- Can you explain why this logic belongs here?
- Let us swap roles; talk me through the next test.

## Useful Talking Points

- What you understand about the requirement.
- What assumption you are making.
- The smallest failing test you want to write.
- Why logic belongs in a specific layer.
- What trade-off you are accepting.
- What you would improve with more time.
