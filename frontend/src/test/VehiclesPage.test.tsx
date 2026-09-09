import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import { createVehicle } from '../api/vehicleApi';
import { Vehicle } from '../types/vehicle';
import { MemoryRouter } from 'react-router-dom';
import { VehiclesPage } from '../pages/VehiclesPage';
import { cleanup, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

vi.mock('../api/vehicleApi', () => ({
  createVehicle: vi.fn(),
}));

const createVehicleMock = vi.mocked(createVehicle);

const pageResponse = (
  vehicle: Vehicle
): Vehicle => ({
  registrationNumber: vehicle.registrationNumber,
  model: vehicle.model,
  ownerName: vehicle.ownerName
})

const vehicle: Vehicle = {
  registrationNumber: "17-09-VI",
  model: "Laguna",
  ownerName: "Luis"
}

function deferred<T>() {
  let resolve!: (value: T) => void;
  let reject!: (reason?: unknown) => void;

  const promise = new Promise<T>((promiseResolve, promiseReject) => {
    resolve = promiseResolve;
    reject = promiseReject;
  });

  return { promise, resolve, reject };
}

function renderPage() {
  render(
    <MemoryRouter>
      <VehiclesPage />
    </MemoryRouter>
  );
}

describe("VehiclePage training exercises", () => {
  afterEach(() => {
    cleanup();
  })

  beforeEach(() => {
    createVehicleMock.mockReset();
  })

  it('Vehicle is created by the API', async () => {
    const user = userEvent.setup();

    createVehicleMock.mockResolvedValue(pageResponse(vehicle));
    renderPage();

    await user.type(screen.getByLabelText(/registration number/i), vehicle.registrationNumber);
    await user.type(screen.getByLabelText(/model/i), vehicle.model);
    await user.type(screen.getByLabelText(/owner name/i), vehicle.ownerName);
    await user.click(screen.getByRole('button', { name: /create/i }));

    expect(createVehicleMock).toHaveBeenCalledWith({
      registrationNumber: vehicle.registrationNumber,
      model: vehicle.model,
      ownerName: vehicle.ownerName,
    });
  })

  it('does not submit when required fields are empty', async () => {
    const user = userEvent.setup();

    renderPage();

    await user.click(screen.getByRole('button', { name: /create/i }));

    expect(createVehicleMock).not.toHaveBeenCalled();

    expect(screen.getByLabelText(/registration number/i)).toBeRequired();
    expect(screen.getByLabelText(/model/i)).toBeRequired();
    expect(screen.getByLabelText(/owner name/i)).toBeRequired();
  });

  it('shows a loading state while the vehicle is being created', async () => {
    const user = userEvent.setup();
    const createVehicleRequest = deferred<Vehicle>();

    createVehicleMock.mockReturnValue(createVehicleRequest.promise);
    renderPage();

    await user.type(screen.getByLabelText(/registration number/i), vehicle.registrationNumber);
    await user.type(screen.getByLabelText(/model/i), vehicle.model);
    await user.type(screen.getByLabelText(/owner name/i), vehicle.ownerName);
    await user.click(screen.getByRole('button', { name: /create/i }));

    expect(screen.getByText(/loading/i)).toBeInTheDocument();
    expect(createVehicleMock).toHaveBeenCalledWith({
      registrationNumber: vehicle.registrationNumber,
      model: vehicle.model,
      ownerName: vehicle.ownerName,
    });

    createVehicleRequest.resolve(pageResponse(vehicle));

    await waitFor(() => {
      expect(screen.queryByText(/loading/i)).not.toBeInTheDocument();
    });
  })

  it('prevents duplicate submissions while create is in progress', async () => {
    const user = userEvent.setup();
    const createVehicleRequest = deferred<Vehicle>();

    createVehicleMock.mockReturnValue(createVehicleRequest.promise);
    renderPage();

    await user.type(screen.getByLabelText(/registration number/i), vehicle.registrationNumber);
    await user.type(screen.getByLabelText(/model/i), vehicle.model);
    await user.type(screen.getByLabelText(/owner name/i), vehicle.ownerName);
    await user.dblClick(screen.getByRole('button', { name: /create/i }));

    expect(createVehicleMock).toHaveBeenCalledTimes(1);

    createVehicleRequest.resolve(pageResponse(vehicle));
  });

  it('shows a success message after a vehicle is created', async () => {
    const user = userEvent.setup();

    createVehicleMock.mockResolvedValue(pageResponse(vehicle));
    renderPage();

    await user.type(screen.getByLabelText(/registration number/i), vehicle.registrationNumber);
    await user.type(screen.getByLabelText(/model/i), vehicle.model);
    await user.type(screen.getByLabelText(/owner name/i), vehicle.ownerName);
    await user.click(screen.getByRole('button', { name: /create/i }));

    expect(await screen.findByRole('status')).toHaveTextContent(/new vehicle created/i);
  });

  it('clears the form after a vehicle is created', async () => {
    const user = userEvent.setup();

    createVehicleMock.mockResolvedValue(pageResponse(vehicle));
    renderPage();

    await user.type(screen.getByLabelText(/registration number/i), vehicle.registrationNumber);
    await user.type(screen.getByLabelText(/model/i), vehicle.model);
    await user.type(screen.getByLabelText(/owner name/i), vehicle.ownerName);
    await user.click(screen.getByRole('button', { name: /create/i }));

    await waitFor(() => {
      expect(screen.getByLabelText(/registration number/i)).toHaveValue("");
      expect(screen.getByLabelText(/model/i)).toHaveValue("");
      expect(screen.getByLabelText(/owner name/i)).toHaveValue("");
    });
  });

  it('shows an error message when vehicle creation fails', async () => {
    const user = userEvent.setup();

    createVehicleMock.mockRejectedValue(new Error("Vehicle already exists."));
    renderPage();

    await user.type(screen.getByLabelText(/registration number/i), vehicle.registrationNumber);
    await user.type(screen.getByLabelText(/model/i), vehicle.model);
    await user.type(screen.getByLabelText(/owner name/i), vehicle.ownerName);
    await user.click(screen.getByRole('button', { name: /create/i }));

    expect(await screen.findByRole('alert')).toHaveTextContent(/vehicle already exists/i);
    expect(screen.getByLabelText(/registration number/i)).toHaveValue(vehicle.registrationNumber);
    expect(screen.getByLabelText(/model/i)).toHaveValue(vehicle.model);
    expect(screen.getByLabelText(/owner name/i)).toHaveValue(vehicle.ownerName);
  });
})
