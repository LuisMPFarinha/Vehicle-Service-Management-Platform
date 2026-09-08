import { PageResponse } from "../types/pagination";
import { ServiceRequest, ServiceRequestFilters } from "../types/serviceRequest";
import { getJson } from "./httpClient";

export function findFilteredServiceRequests(
  filters: ServiceRequestFilters = {},
): Promise<PageResponse<ServiceRequest>> {
  const params = new URLSearchParams();

  if (filters.status) params.set("status", filters.status);
  if (filters.priority) params.set("priority", filters.priority);
  if (filters.regNum?.trim()) params.set("regNum", filters.regNum.trim());

  params.set("page", String(filters.page ?? 0));
  params.set("size", String(filters.size ?? 20));

  return getJson(`/service-requests?${params.toString()}`);
}