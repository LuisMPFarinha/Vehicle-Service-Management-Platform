# ADR 002: PostgreSQL

## Status

Accepted

## Context

The application stores vehicles and maintenance requests with relational
relationships and filterable operational data.

## Decision

Use PostgreSQL as the primary database, with Flyway managing schema changes.

## Consequences

- The data model is familiar and production-relevant.
- Query and indexing discussions are natural interview material.
- Testcontainers can provide realistic integration tests.
