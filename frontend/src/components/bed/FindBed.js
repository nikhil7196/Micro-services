import { useState } from "react";
import axios from "axios";

export default function FindBed() {

    let [bedId, setBedId] = useState("");
    let [bed, setBed] = useState(null);
    let [error, setError] = useState("");

    let bedIdHandler = (e) => {
        setBedId(e.target.value);
    }

    let searchHandler = () => {
        setError("");
        setBed(null);

        let url = `http://localhost:9002/api/beds/getBed/${bedId}`;
        axios.get(url,{
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token") 
                    }
                })
            .then((response) => {
                if (response.data.bed) {
                    setBed(response.data.bed);
                } else {
                    setError("Bed not found for ID: " + bedId);
                }
            })
            .catch((error) => {
                if (error.response) {
                    setError("Error " + error.response.status + ": " + (error.response.data?.errorMessage || "Bed not found"));
                } else if (error.request) {
                    setError("No response from server. Make sure the backend is running on port 9002.");
                } else {
                    setError("Error: " + error.message);
                }
            });
    }

    return (
        <div className="container mt-4">
            <h2>Find Bed</h2>

            <div className="input-group mb-3">
                <input
                    className="form-control"
                    value={bedId}
                    onChange={bedIdHandler}
                    placeholder="Enter Bed ID"
                />
                <button className="btn btn-primary" onClick={searchHandler}>Search</button>
            </div>

            {error && <div className="alert alert-danger">{error}</div>}

            {bed && (
                <table className="table table-bordered table-striped mt-3">
                    <thead className="table-dark">
                        <tr>
                            <th>Bed ID</th>
                            <th>Bed Status</th>
                            <th>Patient ID</th>
                            <th>Patient Name</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>{bed.bedId}</td>
                            <td>{bed.bedStatus}</td>
                            <td>{bed.patient ? bed.patient.patientId : "Not Assigned"}</td>
                            <td>{bed.patient ? bed.patient.patientName : "Not Assigned"}</td>
                        </tr>
                    </tbody>
                </table>
            )}
        </div>
    );
}
