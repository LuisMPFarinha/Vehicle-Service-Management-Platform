export function DashboardPage() {
  return (
    <section>
      <header className="page-header">
        <div>
          <h1>Operations Dashboard</h1>
          <p>Placeholder metrics for the dashboard training exercise.</p>
        </div>
      </header>

      <div className="metrics">
        <div className="metric">
          <span>Open requests</span>
          <strong>0</strong>
        </div>
        <div className="metric">
          <span>Waiting for parts</span>
          <strong>0</strong>
        </div>
        <div className="metric">
          <span>Completed today</span>
          <strong>0</strong>
        </div>
      </div>
    </section>
  );
}
