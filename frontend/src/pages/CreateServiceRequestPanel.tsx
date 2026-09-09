import { useEffect, useState } from "react";
import { openRequest } from "../api/serviceRequestApi";
import { Priority } from "../types/serviceRequest";

type CreateServiceRequestPanelProps = {
  closeShowPanel: () => void;
  loadServiceRequests: () => Promise<void>;
};

export const CreateServiceRequestPanel = ({ closeShowPanel, loadServiceRequests }: CreateServiceRequestPanelProps) => {
  const [vehicleId, setVehicleId] = useState("");
  const [description, setDescription] = useState("");
  const [priority, setPriority] = useState<Priority>("LOW");
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");
  const [isSuccess, setIsSuccess] = useState(false);

  useEffect(() => {
    if (!isSuccess) return;

    const timeoutId = window.setTimeout(() => {
      setIsSuccess(false);
      closeShowPanel();
    }, 1000);

    return () => window.clearTimeout(timeoutId);
  }, [isSuccess, closeShowPanel]);

  const handleOnSubmit = async (
    event: React.FormEvent<HTMLFormElement>
  ) => {
    event.preventDefault();

    setError("");
    setIsSuccess(false);
    setIsLoading(true);

    try {
      await openRequest({
        vehicleId,
        description,
        priority,
      });

      setVehicleId("");
      setDescription("");
      setPriority("LOW");
      setIsSuccess(true);
      await loadServiceRequests();
    } catch (err) {
      if (err instanceof Error) {
        setError(err.message);
      } else {
        setError("Could not create service request.");
      }
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <section>
      <h2>Open a Service Request</h2>

      <form className="stacked-form" onSubmit={handleOnSubmit}>
        <label htmlFor="openRequestVehicleId">Vehicle ID</label>
        <input
          id="openRequestVehicleId"
          type="text"
          value={vehicleId}
          onChange={(event) => setVehicleId(event.target.value)}
          required
          disabled={isLoading}
        />

        <label htmlFor="openRequestDescription">Description</label>
        <input
          id="openRequestDescription"
          type="text"
          value={description}
          onChange={(event) => setDescription(event.target.value)}
          required
          disabled={isLoading}
        />

        <label htmlFor="openRequestPriority">Priority</label>
        <select
          id="openRequestPriority"
          value={priority}
          onChange={(event) =>
            setPriority(event.target.value as Priority)
          }
          disabled={isLoading}
        >
          <option value="LOW">Low</option>
          <option value="MEDIUM">Medium</option>
          <option value="HIGH">High</option>
          <option value="URGENT">Urgent</option>
        </select>

        <div className="form-actions">
          <button type="submit" disabled={isLoading}>
            {isLoading ? "Creating..." : "Create Request"}
          </button>
          <button type="button" onClick={closeShowPanel} disabled={isLoading}>Close</button>
        </div>
      </form>
      {error && <div role="alert">{error}</div>}

      {isSuccess && (
        <div role="status">
          Service request created successfully.
        </div>
      )}
    </section>
  );
};
