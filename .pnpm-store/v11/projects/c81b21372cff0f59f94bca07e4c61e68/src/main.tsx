import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, NavLink, Route, Routes } from 'react-router-dom';
import { ClipboardList, Gauge, Wrench } from 'lucide-react';
import { DashboardPage } from './pages/DashboardPage';
import { ServiceRequestsPage } from './pages/ServiceRequestsPage';
import { VehiclesPage } from './pages/VehiclesPage';
import './styles.css';

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <BrowserRouter>
      <div className="app-shell">
        <aside className="sidebar" aria-label="Primary navigation">
          <div className="brand">Vehicle Service</div>
          <nav>
            <NavLink to="/" end>
              <Gauge size={18} /> Dashboard
            </NavLink>
            <NavLink to="/requests">
              <ClipboardList size={18} /> Requests
            </NavLink>
            <NavLink to="/vehicles">
              <Wrench size={18} /> Vehicles
            </NavLink>
          </nav>
        </aside>
        <main>
          <Routes>
            <Route path="/" element={<DashboardPage />} />
            <Route path="/requests" element={<ServiceRequestsPage />} />
            <Route path="/vehicles" element={<VehiclesPage />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  </React.StrictMode>
);
