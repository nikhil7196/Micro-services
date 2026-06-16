import axios from "axios";
import { useState, useEffect } from "react";

export default function PaginatedWard() {

    const [records, setRecords] = useState([]);
    const [pgno, setPgno] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        fetchWards(0);
    }, []);

    const fetchWards = async (page) => {
        try {
            setLoading(true);
            setError("");
            const res = await axios.get("http://localhost:9002/api/ward/getAllWardsPaginated", {
                params: { pgno: page, size: 5, sorting: "wardId", asc: true },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            });
            setRecords(res.data.content);
            setPgno(res.data.number);
            setTotalPages(res.data.totalPages);
        } catch (err) {
            setError(err.response?.data?.message || err.message || "Failed to load wards.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container mt-4">
            <h2 className="mb-4">All Wards</h2>

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
                                <th>Ward ID</th>
                                <th>Ward Name</th>
                                <th>Ward Capacity</th>
                                <th>Ward Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            {records.map((e) => (
                                <tr key={e.wardId}>
                                    <td>{e.wardId}</td>
                                    <td>{e.wardname}</td>
                                    <td>{e.wardcapacity}</td>
                                    <td>
                                        <span className={`badge ${e.wardstatus === "Active" ? "bg-success" : "bg-secondary"}`}>
                                            {e.wardstatus}
                                        </span>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>

                    <div className="d-flex justify-content-between align-items-center mt-3">
                        <button
                            className="btn btn-outline-secondary"
                            disabled={pgno === 0 || loading}
                            onClick={() => fetchWards(pgno - 1)}
                        >
                            &laquo; Prev
                        </button>

                        <span className="text-muted fw-semibold">
                            Page {pgno + 1} of {totalPages}
                        </span>

                        <button
                            className="btn btn-outline-secondary"
                            disabled={pgno >= totalPages - 1 || loading}
                            onClick={() => fetchWards(pgno + 1)}
                        >
                            Next &raquo;
                        </button>
                    </div>
                </>
            )}

            {!loading && records.length === 0 && !error && (
                <div className="alert alert-warning mt-3">No wards found.</div>
            )}
        </div>
    );
}
