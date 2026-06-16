import { useState } from "react";
import axios from "axios";

export default function FindWard() {

    let [wardId, setWardId] = useState("");
    let [ward, setWard] = useState(null);
    let [error, setError] = useState("");

    let wardIdHandler = (e) => {
        setWardId(e.target.value);
    }

    let searchHandler = () => {
        setError("");
        setWard(null);

        let url = `http://localhost:9002/api/ward/getWard/${wardId}`;
        axios.get(url,{
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token") 
                    }
                })
            .then((response) => {
                if (response.data.ward === null) {
                    setError("Ward not found with ID: " + wardId);
                } else {
                    setWard(response.data.ward);
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
            <h2>Find Ward</h2>

            <div className="input-group mb-3">
                <input
                    className="form-control"
                    value={wardId}
                    onChange={wardIdHandler}
                    placeholder="Enter Ward ID"
                />
                <button className="btn btn-primary" onClick={searchHandler}>Search</button>
            </div>

            {error && <div className="alert alert-danger">{error}</div>}

            {ward && (
                <table className="table table-bordered table-striped mt-3">
                    <thead className="table-dark">
                        <tr>
                            <th>Ward ID</th>
                            <th>Ward Name</th>
                            <th>Ward Capacity</th>
                            <th>Ward Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>{ward.wardId}</td>
                            <td>{ward.wardname}</td>
                            <td>{ward.wardcapacity}</td>
                            <td>{ward.wardstatus}</td>
                        </tr>
                    </tbody>
                </table>
            )}
        </div>
    );
}
