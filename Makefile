.PHONY: postgres backend frontend backend-test frontend-test

postgres:
	docker compose up postgres

backend:
	cd backend && mvn spring-boot:run

frontend:
	cd frontend && pnpm dev

backend-test:
	cd backend && mvn test

frontend-test:
	cd frontend && pnpm test
