import { useEffect, useState } from 'react';
import { createVehicle } from '../api/vehicleApi';

export function VehiclesPage() {
  const [regNum, setRegNum] = useState("");
  const [model, setModel] = useState("");
  const [ownerName, setOwnerName] = useState("");
  const [error, setError] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    setTimeout(() => {
      setSuccess(false);
    }, 2000);
  }, [success])

  const handleOnSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    setError("");

    try {
      await createVehicle({
        registrationNumber: regNum,
        model,
        ownerName
      });
      setSuccess(true);
      setRegNum("");
      setModel("");
      setOwnerName("");
    } catch (error) {
      if (error instanceof Error) {
        setError(error.message);
      } else {
        setError("Could not create vehicle.");
      }
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <section>
      <header className="page-header">
        <div>
          <h1>Vehicles</h1>
          <p>Create the vehicle form and list during the first exercise.</p>
        </div>
      </header>

      <div className="panel">
        {isLoading ? <div>Loading</div> :
          <form className="stacked-form" onSubmit={(e) => handleOnSubmit(e)}>
            <label>
              Registration Number
              <input type="text" required value={regNum} onChange={(e) => setRegNum(e.target.value)} />
            </label>
            <label>
              Model
              <input type="text" required value={model} onChange={(e) => setModel(e.target.value)} />
            </label>
            <label>
              Owner Name
              <input type="text" required value={ownerName} onChange={(e) => setOwnerName(e.target.value)} />
            </label>
            <button type="submit">Create</button>
          </form>
        }
        {error && <div role="alert">{error}</div>}
        {success && <div role="status">New vehicle created</div>}
      </div>
    </section>
  );
}
