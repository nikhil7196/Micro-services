import axios from "axios";
import { useState, useEffect } from "react";

export default function Bedpage() {

    const [records, setRecords] = useState([]);
    const [pgno, setPgno] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        fetchBeds(0);
    }, []);

    const fetchBeds = async (page) => {
        try {
            setLoading(true);
            setError("");
            const res = await axios.get("http://localhost:9002/api/beds/getAllPatientsPaginated", {
                params: { pgno: page, size: 5, sorting: "bedId", asc: true },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            });
            setRecords(res.data.content);
            setPgno(res.data.number);
            setTotalPages(res.data.totalPages);
        } catch (err) {
            setError(err.response?.data?.message || err.message || "Failed to load beds.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container mt-4">
            <h2 className="mb-4">All Beds</h2>

            {error && (
                <div className="alert alert-danger alert-dismissible" role="alert">
                    {error}
                    <button type="button" className="btn-close" onClick={() => setError("")}></button>
                </div>
            )}

            {loading && (
                <div className="d-flex justify-content-center my-4">
                    <div className="spinner-border text-primary" role="status">
                        <span className="visually-hidden">Loading...</span>
                    </div>
                </div>
            )}

            {!loading && records.length > 0 && (
                <>
                    <table className="table table-bordered table-striped table-hover align-middle">
                        <thead className="table-dark">
                            <tr>
                                <th>Bed ID</th>
                                <th>Bed Status</th>
                                <th>Patient ID</th>
                                <th>Patient Name</th>
                            </tr>
                        </thead>
                        <tbody>
                            {records.map((e) => (
                                <tr key={e.bedId}>
                                    <td>{e.bedId}</td>
                                    <td>
                                        <span className={`badge ${e.bedStatus === "OCCUPIED" ? "bg-danger" : "bg-success"}`}>
                                            {e.bedStatus}
                                        </span>
                                    </td>
                                    <td>{e.patient ? e.patient.patientId : <span className="text-muted">Not Assigned</span>}</td>
                                    <td>{e.patient ? e.patient.patientName : <span className="text-muted">Not Assigned</span>}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>

                    <div className="d-flex justify-content-between align-items-center mt-3">
                        <button
                            className="btn btn-outline-secondary"
                            disabled={pgno === 0 || loading}
                            onClick={() => fetchBeds(pgno - 1)}
                        >
                            &laquo; Prev
                        </button>

                        <span className="text-muted fw-semibold">
                            Page {pgno + 1} of {totalPages}
                        </span>

                        <button
                            className="btn btn-outline-secondary"
                            disabled={pgno >= totalPages - 1 || loading}
                            onClick={() => fetchBeds(pgno + 1)}
                        >
                            Next &raquo;
                        </button>
                    </div>
                </>
            )}

            {!loading && records.length === 0 && !error && (
                <div className="alert alert-warning mt-3">No beds found.</div>
            )}
        </div>
    );
}
