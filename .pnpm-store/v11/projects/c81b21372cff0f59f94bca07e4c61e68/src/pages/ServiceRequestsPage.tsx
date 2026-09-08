import { Plus } from 'lucide-react';
import { useEffect, useState } from 'react';
import { Priority, ServiceRequest, ServiceRequestStatus } from '../types/serviceRequest';
import { PageResponse } from '../types/pagination';
import { findFilteredServiceRequests } from '../api/serviceRequestApi';

export function ServiceRequestsPage() {
  const [regNum, setRegNum] = useState("");
  const [debouncedRegNum, setDebouncedRegNum] = useState("");
  const [status, setStatus] = useState<ServiceRequestStatus | "">("");
  const [priority, setPriority] = useState<Priority | "">("");
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [page, setPage] = useState<PageResponse<ServiceRequest> | null>(null);

  useEffect(() => {
    loadServiceRequests();
  }, [debouncedRegNum, status, priority])

  useEffect(() => {
    const timeout = window.setTimeout(() => {
      setDebouncedRegNum(regNum);
    }, 300);

    return () => window.clearTimeout(timeout);
  }, [regNum]);

  const loadServiceRequests = async () => {
    setIsLoading(true);
    setError(null);

    try {
      const result = await findFilteredServiceRequests({
        regNum: debouncedRegNum || undefined,
        status: status || undefined,
        priority: priority || undefined,
      });

      setPage(result);
    } catch {
      setError("Could not load service requests.");
    } finally {
      setIsLoading(false);
    }
  };

  const renderServiceRequests = () => {
    if (isLoading) {
      return (
        <tr>
          <td colSpan={5}>Loading service requests...</td>
        </tr>
      );
    }

    if (error) {
      return (
        <tr>
          <td colSpan={5} role="alert">{error}</td>
        </tr>
      );
    }

    if (page?.content.length === 0) {
      return (
        <tr>
          <td colSpan={5}>No service requests found.</td>
        </tr>
      );
    }

    return page?.content.map((request) =>
      <tr key={request.id}>
        <td>{request.vehicleId}</td>
        <td>{request.description}</td>
        <td>{request.priority}</td>
        <td>
          <span className="status">{request.status}</span>
        </td>
        <td>{request.assignedTechnician ?? "Unassigned"}</td>
      </tr>
    );
  }

  return (
    <section>
      <header className="page-header">
        <div>
          <h1>Service Requests</h1>
          <p>Build loading, empty, success, error, and filter states here.</p>
        </div>
        <button type="button">
          <Plus size={18} /> New request
        </button>
      </header>

      <div className="toolbar" aria-label="Request filters">
        <input aria-label="Vehicle registration" placeholder="Registration"
          value={regNum} onChange={(e) => setRegNum(e.target.value)} />
        <select aria-label="Status" value={status}
          onChange={(e) => setStatus(e.target.value as ServiceRequestStatus | "")}>
          <option value="">All statuses</option>
          <option>OPEN</option>
          <option>IN_PROGRESS</option>
          <option>WAITING_FOR_PARTS</option>
          <option>COMPLETED</option>
          <option>CANCELLED</option>
        </select>
        <select aria-label="Priority" value={priority}
          onChange={(e) => setPriority(e.target.value as Priority | "")}>
          <option value="">All priorities</option>
          <option>LOW</option>
          <option>MEDIUM</option>
          <option>HIGH</option>
          <option>URGENT</option>
        </select>
      </div>

      <div className="panel">
        <table>
          <thead>
            <tr>
              <th>Vehicle</th>
              <th>Description</th>
              <th>Priority</th>
              <th>Status</th>
              <th>Technician</th>
            </tr>
          </thead>
          <tbody>
            {renderServiceRequests()}
          </tbody>
        </table>
      </div>
    </section>
  );
}
