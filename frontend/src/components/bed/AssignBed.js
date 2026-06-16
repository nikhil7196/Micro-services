import { useState } from "react";
import axios from "axios";

export default function AssignBed() {

    const [bedId, setBedId] = useState("");
    const [patientId, setPatientId] = useState("");
    const [success, setSuccess] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const assignHandler = async () => {
        setSuccess("");
        setError("");
        setLoading(true);
        try {
            await axios.post(
                `http://localhost:9002/api/beds/${bedId}/assign`,
                { patientId: parseInt(patientId) },
                { headers: { Authorization: "Bearer " + localStorage.getItem("token") } }
            );
            setSuccess(`Patient ${patientId} assigned to Bed ${bedId} successfully!`);
            setBedId("");
            setPatientId("");
        } catch (err) {
            if (err.response) {
                setError("Error " + err.response.status + ": " + (err.response.data?.errorMessage || JSON.stringify(err.response.data)));
            } else if (err.request) {
                setError("No response from server. Make sure the backend is running on port 9002.");
            } else {
                setError(err.message);
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container mt-4">
            <h2 className="mb-4">Assign Patient to Bed</h2>

            {success && (
                <div className="alert alert-success alert-dismissible" role="alert">
                    {success}
                    <button type="button" className="btn-close" onClick={() => setSuccess("")}></button>
                </div>
            )}

            {error && (
                <div className="alert alert-danger alert-dismissible" role="alert">
                    {error}
                    <button type="button" className="btn-close" onClick={() => setError("")}></button>
                </div>
            )}

            <div className="mb-3">
                <label className="form-label fw-semibold">Bed ID</label>
                <input className="form-control" value={bedId} onChange={e => setBedId(e.target.value)} placeholder="Enter Bed ID" />
            </div>

            <div className="mb-3">
                <label className="form-label fw-semibold">Patient ID</label>
                <input className="form-control" value={patientId} onChange={e => setPatientId(e.target.value)} placeholder="Enter Patient ID" />
            </div>

            <button className="btn btn-success" onClick={assignHandler} disabled={loading || !bedId || !patientId}>
                {loading ? (
                    <><span className="spinner-border spinner-border-sm me-2" role="status"></span>Assigning...</>
                ) : "Assign"}
            </button>
        </div>
    );
}
