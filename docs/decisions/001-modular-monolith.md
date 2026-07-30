# ADR 001: Modular Monolith

## Status

Accepted

## Context

The training project needs enough architecture to discuss boundaries, testing,
and change management without introducing distributed-systems complexity.

## Decision

Build the backend as a modular monolith with explicit domain, application,
infrastructure, and presentation layers.

## Consequences

- The project remains easy to run locally.
- Design conversations can focus on responsibility and testability.
- Service extraction remains possible later if a real scaling or ownership need
  appears.
