import { render, screen } from '@testing-library/react';
import { describe, expect, it } from 'vitest';
import { MemoryRouter } from 'react-router-dom';
import { ServiceRequestsPage } from '../pages/ServiceRequestsPage';

describe.skip('ServiceRequestsPage training exercises', () => {
  it('shows loading state before requests are loaded', () => {
    // Given the API request is pending
    // When the page renders
    // Then a loading state is visible
  });

  it('renders the placeholder page before implementation', () => {
    render(
      <MemoryRouter>
        <ServiceRequestsPage />
      </MemoryRouter>
    );

    expect(screen.getByRole('heading', { name: /service requests/i })).toBeInTheDocument();
  });
});
