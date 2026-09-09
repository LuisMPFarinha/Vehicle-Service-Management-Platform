import { PageResponse } from "../types/pagination";
import { OpenServiceRequestCommand, ServiceRequest, ServiceRequestFilters } from "../types/serviceRequest";

const apiBaseUrl =
  import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080/api";

export async function findFilteredServiceRequests(
  filters: ServiceRequestFilters = {},
): Promise<PageResponse<ServiceRequest>> {
  const params = new URLSearchParams();

  if (filters.status) params.set("status", filters.status);
  if (filters.priority) params.set("priority", filters.priority);
  if (filters.regNum?.trim()) params.set("regNum", filters.regNum.trim());

  params.set("page", String(filters.page ?? 0));
  params.set("size", String(filters.size ?? 20));

  const response = await fetch(
    `${apiBaseUrl}/service-requests?${params.toString()}`,
  );

  if (!response.ok) {
    throw new Error(`Request failed with status ${response.status}`);
  }

  return response.json();
}

export async function openRequest(serviceRequest: OpenServiceRequestCommand): Promise<void> {
  const response = await fetch(`${apiBaseUrl}/service-requests`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(serviceRequest),
  })

  if (!response.ok) {
    let message = "Could not create service request.";

    try {
      const errorBody = await response.json();
      message = errorBody.message ?? message;
    } catch { }

    throw new Error(message);
  }
}
