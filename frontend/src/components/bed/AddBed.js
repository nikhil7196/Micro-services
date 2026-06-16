import axios from 'axios';
import { useState } from 'react';
import { toast } from 'react-toastify';

export default function AddBed() {

    const [bedStatus, setBedStatus] = useState("");
    const [wardId, setWardId] = useState("");
    const [loading, setLoading] = useState(false);

    const saveHandler = () => {
        if (loading) return;
        setLoading(true);

        const url = "http://localhost:9002/api/beds/create";
        const data = {
            "bed": {
                "bedStatus": bedStatus,
                "ward": {
                    "wardId": parseInt(wardId)
                }
            }
        };

        axios.post(url, data, {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token")
            }
        })
        .then((response) => {
            toast.success("Bed saved successfully! Bed ID: " + response.data.bed.bedId);
            setBedStatus("");
            setWardId("");
        })
        .catch((error) => {
            toast.error(error.response?.data?.message || error.message);
        })
        .finally(() => {
            setLoading(false);
        });
    };

    return (
        <div className="container mt-4">
            <h2 className="mb-4">Add Bed</h2>

            <form onSubmit={(e) => {
                e.preventDefault();
                saveHandler();
            }}>

                <div className="mb-3">
                    <label className="form-label">
                        Bed Status
                        <span style={{ color: "red" }}> *</span>
                    </label>
                    <select
                        className="form-select"
                        value={bedStatus}
                        onChange={(e) => setBedStatus(e.target.value)}
                        required
                    >
                        <option value="">-- Select Bed Status --</option>
                        <option value="Available">Available</option>
                        <option value="Occupied">Occupied</option>
                        <option value="Under Maintenance">Under Maintenance</option>
                    </select>
                </div>

                <div className="mb-3">
                    <label className="form-label">
                        Ward ID
                        <span style={{ color: "red" }}> *</span>
                    </label>
                    <input
                        className="form-control"
                        type="number"
                        value={wardId}
                        onChange={(e) => setWardId(e.target.value)}
                        placeholder="Enter Ward ID"
                        min="1"
                        required
                    />
                </div>

                <button
                    className="btn btn-primary w-100"
                    type="submit"
                    disabled={loading}
                >
                    {loading ? (
                        <>
                            <span className="spinner-border spinner-border-sm me-2"></span>
                            Saving...
                        </>
                    ) : (
                        "Save Bed"
                    )}
                </button>

            </form>
        </div>
    );
}