import { cleanup, render, screen, waitFor, within } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import { MemoryRouter } from 'react-router-dom';
import { ServiceRequestsPage } from '../pages/ServiceRequestsPage';
import { findFilteredServiceRequests } from '../api/serviceRequestApi';
import { PageResponse } from '../types/pagination';
import { ServiceRequest } from '../types/serviceRequest';

vi.mock('../api/serviceRequestApi', () => ({
  findFilteredServiceRequests: vi.fn(),
}));

const findFilteredServiceRequestsMock = vi.mocked(findFilteredServiceRequests);

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
});
