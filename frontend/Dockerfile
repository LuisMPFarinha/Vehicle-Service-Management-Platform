FROM node:22-alpine
WORKDIR /app
RUN corepack enable
COPY package*.json ./
COPY pnpm-lock.yaml* ./
COPY pnpm-workspace.yaml* ./
RUN pnpm install --frozen-lockfile
COPY . .
EXPOSE 5173
CMD ["pnpm", "dev"]
