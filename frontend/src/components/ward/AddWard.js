import axios from 'axios';
import { useState } from 'react';
import { toast } from 'react-toastify';

export default function AddWard() {

    const [wardName, setWardName] = useState("");
    const [wardCapacity, setWardCapacity] = useState("");
    const [wardStatus, setWardStatus] = useState("");
    const [loading, setLoading] = useState(false);

    const saveHandler = () => {
        if (loading) return;
        setLoading(true);

        const url = "http://localhost:9002/api/ward/create";
        const data = {
            "ward": {
                "wardname": wardName.trim(),
                "wardcapacity": parseInt(wardCapacity),
                "wardstatus": wardStatus
            }
        };

        axios.post(url, data, {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token")
            }
        })
        .then((response) => {
            toast.success("Ward created successfully! Ward ID: " + response.data.ward.wardId);
            setWardName("");
            setWardCapacity("");
            setWardStatus("");
        })
        .catch((error) => {
            toast.error(error.response?.data?.errorMessage || error.message);
        })
        .finally(() => {
            setLoading(false);
        });
    };

    return (
        <div className="container mt-4">
            <h2 className="mb-4">Add Ward</h2>

            <form onSubmit={(e) => {
                e.preventDefault();
                saveHandler();
            }}>

                <div className="mb-3">
                    <label className="form-label">
                        Ward Name
                        <span style={{ color: "red" }}> *</span>
                    </label>
                    <input
                        className="form-control"
                        type="text"
                        value={wardName}
                        onChange={(e) => setWardName(e.target.value)}
                        placeholder="Enter Ward Name"
                        pattern="[A-Za-z\s]+"
                        title="Only letters allowed"
                        required
                    />
                </div>

                <div className="mb-3">
                    <label className="form-label">
                        Ward Capacity
                        <span style={{ color: "red" }}> *</span>
                    </label>
                    <input
                        className="form-control"
                        type="number"
                        value={wardCapacity}
                        onChange={(e) => setWardCapacity(e.target.value)}
                        placeholder="Enter Ward Capacity"
                        min="1"
                        required
                    />
                </div>

                <div className="mb-3">
                    <label className="form-label">
                        Ward Status
                        <span style={{ color: "red" }}> *</span>
                    </label>
                    <select
                        className="form-select"
                        value={wardStatus}
                        onChange={(e) => setWardStatus(e.target.value)}
                        required
                    >
                        <option value="">-- Select Ward Status --</option>
                        <option value="Active">Active</option>
                        <option value="Inactive">Inactive</option>
                        <option value="Under Maintenance">Under Maintenance</option>
                    </select>
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
                        "Save Ward"
                    )}
                </button>

            </form>
        </div>
    );
}