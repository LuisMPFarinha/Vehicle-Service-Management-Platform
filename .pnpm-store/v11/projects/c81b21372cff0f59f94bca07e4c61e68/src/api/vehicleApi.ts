import { Vehicle } from "../types/vehicle";

const apiBaseUrl =
  import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080/api";

export async function createVehicle(vehicle: Vehicle): Promise<Vehicle> {
  const response = await fetch(`${apiBaseUrl}/vehicles`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(vehicle),
  });

  if (!response.ok) {
    let message = "Could not create vehicle.";

    try {
      const errorBody = await response.json();
      message = errorBody.message ?? message;
    } catch { }

    throw new Error(message);
  }

  return response.json();
}
