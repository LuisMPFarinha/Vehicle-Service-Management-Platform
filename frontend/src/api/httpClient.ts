const apiBaseUrl = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080/api';

export async function getJson<T>(path: string): Promise<T> {
  // TODO: Add error mapping and request cancellation during frontend exercises.
  const response = await fetch(`${apiBaseUrl}${path}`);
  return response.json() as Promise<T>;
}
