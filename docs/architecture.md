# Architecture

The application is a modular monolith. It keeps one deployable backend while
making boundaries explicit enough to discuss ownership and trade-offs.

## Backend Layers

- `domain`: business concepts, business rules, repository ports, domain exceptions
- `application`: use-case orchestration, commands, responses, outbound ports
- `infrastructure`: persistence adapters, external service adapters, configuration
- `presentation`: HTTP controllers and API-specific concerns

## Dependency Rule

Outer layers may depend on inner layers. Inner layers should not depend on outer
layers.

`presentation -> application -> domain`

`infrastructure -> application/domain`

## Training Focus

This project intentionally starts with placeholder implementations. The goal is
to practise adding behavior, tests, and refactors while keeping the architecture
easy to explain.
