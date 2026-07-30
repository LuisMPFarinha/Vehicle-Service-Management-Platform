import { Plus } from 'lucide-react';

export function VehiclesPage() {
  return (
    <section>
      <header className="page-header">
        <div>
          <h1>Vehicles</h1>
          <p>Create the vehicle form and list during the first exercise.</p>
        </div>
        <button type="button">
          <Plus size={18} /> New vehicle
        </button>
      </header>

      <div className="panel">
        TODO: Add vehicle list, create form, validation, and API integration.
      </div>
    </section>
  );
}
