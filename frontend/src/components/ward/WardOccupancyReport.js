import { useState } from "react";
import axios from "axios";

export default function WardOccupancyReport() {

    let [wardId, setWardId] = useState("");
    let [report, setReport] = useState(null);
    let [error, setError] = useState("");

    let wardIdHandler = (e) => {
        setWardId(e.target.value);
    }

    let searchHandler = () => {
        setError("");
        setReport(null);

        let url = `http://localhost:9002/api/ward/${wardId}/occupancy-report`;
        axios.get(url,{
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token") 
                    }
                })
            .then((response) => {
                if (!response.data) {
                    setError("No report found for Ward ID: " + wardId);
                } else {
                    setReport(response.data);
                }
            })
            .catch((error) => {
                if (error.response) {
                    setError("Error " + error.response.status + ": " + (error.response.data?.errorMessage || "Ward not found"));
                } else if (error.request) {
                    setError("No response from server. Make sure the backend is running on port 9002.");
                } else {
                    setError("Error: " + error.message);
                }
            });
    }

    return (
        <div className="container mt-4">
            <h2>Ward Occupancy Report</h2>

            <div className="input-group mb-3">
                <input
                    className="form-control"
                    value={wardId}
                    onChange={wardIdHandler}
                    placeholder="Enter Ward ID"
                />
                <button className="btn btn-primary" onClick={searchHandler}>Get Report</button>
            </div>

            {error && <div className="alert alert-danger">{error}</div>}

            {report && (
                <div className="alert alert-info mt-3">
                    <h5>Occupancy Report</h5>
                    <p className="mb-0">{report}</p>
                </div>
            )}
        </div>
    );
}
