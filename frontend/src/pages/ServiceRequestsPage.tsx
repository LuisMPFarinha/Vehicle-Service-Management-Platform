import { Plus } from 'lucide-react';

export function ServiceRequestsPage() {
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
        <input aria-label="Vehicle registration" placeholder="Registration" />
        <select aria-label="Status">
          <option>All statuses</option>
          <option>OPEN</option>
          <option>IN_PROGRESS</option>
          <option>WAITING_FOR_PARTS</option>
          <option>COMPLETED</option>
          <option>CANCELLED</option>
        </select>
        <select aria-label="Priority">
          <option>All priorities</option>
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
            <tr>
              <td colSpan={5}>TODO: Replace this placeholder with API-driven request rows.</td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  );
}
