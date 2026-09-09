import { cleanup, render, screen, waitFor, within } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import { MemoryRouter } from 'react-router-dom';
import { ServiceRequestsPage } from '../pages/ServiceRequestsPage';
import { findFilteredServiceRequests, openRequest } from '../api/serviceRequestApi';
import { PageResponse } from '../types/pagination';
import { ServiceRequest } from '../types/serviceRequest';

vi.mock('../api/serviceRequestApi', () => ({
  findFilteredServiceRequests: vi.fn(),
  openRequest: vi.fn(),
}));

const findFilteredServiceRequestsMock = vi.mocked(findFilteredServiceRequests);
const openRequestMock = vi.mocked(openRequest);

const pageResponse = (
  content: ServiceRequest[],
): PageResponse<ServiceRequest> => ({
  content,
  number: 0,
  size: 20,
  totalElements: content.length,
  totalPages: content.length > 0 ? 1 : 0,
});

const brakeRequest: ServiceRequest = {
  id: 'request-1',
  vehicleId: 'vehicle-1',
  description: 'Replace front brake pads',
  priority: 'HIGH',
  status: 'OPEN',
  assignedTechnician: 'Sofia',
  createdAt: '2026-09-07T10:00:00Z',
};

const requestToOpen = {
  vehicleId: '550e8400-e29b-41d4-a716-446655440000',
  description: 'Investigate brake noise',
  priority: 'URGENT' as const,
};

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
      <ServiceRequestsPage />
    </MemoryRouter>,
  );
}

describe('ServiceRequestsPage training exercises', () => {
  afterEach(() => {
    cleanup();
  });

  beforeEach(() => {
    findFilteredServiceRequestsMock.mockReset();
    openRequestMock.mockReset();
  });

  it('shows loading state before requests are loaded', () => {
    findFilteredServiceRequestsMock.mockReturnValue(new Promise(() => {}));

    renderPage();

    expect(screen.getByText(/loading service requests/i)).toBeInTheDocument();
    expect(screen.getByRole('heading', { name: /service requests/i })).toBeInTheDocument();
  });

  it('renders service requests returned by the API', async () => {
    findFilteredServiceRequestsMock.mockResolvedValue(pageResponse([brakeRequest]));

    renderPage();

    expect(await screen.findByText('Replace front brake pads')).toBeInTheDocument();
    const requestRow = screen.getByRole('row', { name: /replace front brake pads/i });
    expect(within(requestRow).getByText('HIGH')).toBeInTheDocument();
    expect(within(requestRow).getByText('OPEN')).toBeInTheDocument();
    expect(within(requestRow).getByText('Sofia')).toBeInTheDocument();
  });

  it('shows an empty state when no service requests match', async () => {
    findFilteredServiceRequestsMock.mockResolvedValue(pageResponse([]));

    renderPage();

    expect(await screen.findByText(/no service requests found/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/vehicle registration/i)).toBeInTheDocument();
  });

  it('shows an error state when the API request fails', async () => {
    findFilteredServiceRequestsMock.mockRejectedValue(new Error('Network failed'));

    renderPage();

    expect(await screen.findByRole('alert')).toHaveTextContent(/could not load service requests/i);
  });

  it('reloads service requests when filters change', async () => {
    const user = userEvent.setup();

    findFilteredServiceRequestsMock.mockResolvedValue(pageResponse([brakeRequest]));

    renderPage();

    await screen.findByText('Replace front brake pads');

    await user.selectOptions(screen.getByLabelText(/status/i), 'OPEN');
    await user.selectOptions(screen.getByLabelText(/priority/i), 'HIGH');
    await user.type(screen.getByLabelText(/vehicle registration/i), 'AA-00');

    await waitFor(() => {
      expect(findFilteredServiceRequestsMock).toHaveBeenLastCalledWith({
        regNum: 'AA-00',
        status: 'OPEN',
        priority: 'HIGH',
      });
    });
  });

  it('opens the request form when starting a new request', async () => {
    const user = userEvent.setup();

    findFilteredServiceRequestsMock.mockResolvedValue(pageResponse([]));
    renderPage();

    await user.click(await screen.findByRole('button', { name: /new request/i }));

    expect(screen.getByRole('heading', { name: /open a service request/i })).toBeInTheDocument();
    expect(screen.getByLabelText(/vehicle id/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/description/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/priority/i)).toBeInTheDocument();
  });

  it('opens a service request through the API', async () => {
    const user = userEvent.setup();

    findFilteredServiceRequestsMock.mockResolvedValue(pageResponse([]));
    openRequestMock.mockResolvedValue(undefined);
    renderPage();

    await user.click(await screen.findByRole('button', { name: /new request/i }));
    await user.type(screen.getByLabelText(/vehicle id/i), requestToOpen.vehicleId);
    await user.type(screen.getByLabelText(/description/i), requestToOpen.description);
    await user.selectOptions(screen.getByLabelText(/priority/i), requestToOpen.priority);
    await user.click(screen.getByRole('button', { name: /create request/i }));

    expect(openRequestMock).toHaveBeenCalledWith(requestToOpen);
  });

  it('does not submit when required request fields are empty', async () => {
    const user = userEvent.setup();

    findFilteredServiceRequestsMock.mockResolvedValue(pageResponse([]));
    renderPage();

    await user.click(await screen.findByRole('button', { name: /new request/i }));
    await user.click(screen.getByRole('button', { name: /create request/i }));

    expect(openRequestMock).not.toHaveBeenCalled();
    expect(screen.getByLabelText(/vehicle id/i)).toBeRequired();
    expect(screen.getByLabelText(/description/i)).toBeRequired();
  });

  it('shows a loading state while the request is being created', async () => {
    const user = userEvent.setup();
    const openRequest = deferred<void>();

    findFilteredServiceRequestsMock.mockResolvedValue(pageResponse([]));
    openRequestMock.mockReturnValue(openRequest.promise);
    renderPage();

    await user.click(await screen.findByRole('button', { name: /new request/i }));
    await user.type(screen.getByLabelText(/vehicle id/i), requestToOpen.vehicleId);
    await user.type(screen.getByLabelText(/description/i), requestToOpen.description);
    await user.selectOptions(screen.getByLabelText(/priority/i), requestToOpen.priority);
    await user.click(screen.getByRole('button', { name: /create request/i }));

    expect(screen.getByRole('button', { name: /creating/i })).toBeDisabled();
    expect(screen.getByRole('button', { name: /close/i })).toBeDisabled();

    openRequest.resolve(undefined);
  });

  it('shows an error message when opening a request fails', async () => {
    const user = userEvent.setup();

    findFilteredServiceRequestsMock.mockResolvedValue(pageResponse([]));
    openRequestMock.mockRejectedValue(new Error('Vehicle not found.'));
    renderPage();

    await user.click(await screen.findByRole('button', { name: /new request/i }));
    await user.type(screen.getByLabelText(/vehicle id/i), requestToOpen.vehicleId);
    await user.type(screen.getByLabelText(/description/i), requestToOpen.description);
    await user.click(screen.getByRole('button', { name: /create request/i }));

    expect(await screen.findByRole('alert')).toHaveTextContent(/vehicle not found/i);
    expect(screen.getByLabelText(/vehicle id/i)).toHaveValue(requestToOpen.vehicleId);
    expect(screen.getByLabelText(/description/i)).toHaveValue(requestToOpen.description);
  });

  it('shows success feedback and reloads requests after opening a request', async () => {
    const user = userEvent.setup();

    findFilteredServiceRequestsMock.mockResolvedValue(pageResponse([]));
    openRequestMock.mockResolvedValue(undefined);
    renderPage();

    await user.click(await screen.findByRole('button', { name: /new request/i }));
    await user.type(screen.getByLabelText(/vehicle id/i), requestToOpen.vehicleId);
    await user.type(screen.getByLabelText(/description/i), requestToOpen.description);
    await user.click(screen.getByRole('button', { name: /create request/i }));

    expect(await screen.findByRole('status')).toHaveTextContent(/service request created successfully/i);

    await waitFor(() => {
      expect(findFilteredServiceRequestsMock).toHaveBeenCalledTimes(2);
    });
  });

  it('closes the request form', async () => {
    const user = userEvent.setup();

    findFilteredServiceRequestsMock.mockResolvedValue(pageResponse([]));
    renderPage();

    await user.click(await screen.findByRole('button', { name: /new request/i }));
    await user.click(screen.getByRole('button', { name: /close/i }));

    expect(screen.queryByRole('heading', { name: /open a service request/i })).not.toBeInTheDocument();
    expect(screen.getByRole('heading', { name: /service requests/i })).toBeInTheDocument();
  });
});
