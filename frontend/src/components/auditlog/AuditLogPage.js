import { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';

export default function AuditLogPage() {

    const [data, setData] = useState([]);
    const [count, setCount] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [loading, setLoading] = useState(false);

    const size = 10;

    const nextHandler = () => {
        if (count < totalPages - 1) setCount(count + 1);
    };

    const prevHandler = () => {
        if (count > 0) setCount(count - 1);
    };

    async function fetchFunction() {
        setLoading(true);
        try {
            const res = await axios.get(
                `http://localhost:9002/auditlog/fetchAllAuditlogsPaginated`,
                {
                    params: { pgno: count, size, sorting: "auditId", asc: true },
                    headers: { Authorization: "Bearer " + localStorage.getItem("token") }
                }
            );
            setData(res.data.content || []);
            setTotalPages(res.data.totalPages);
        } catch (err) {
            toast.error(err.response?.data?.message || err.message);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        fetchFunction();
    }, [count]);

    return (
        <div className="container mt-4">
            <h3 className="mb-4">Audit Logs</h3>

            {loading && <p className="text-muted">Loading...</p>}

            {!loading && data.length === 0 && (
                <p className="text-danger">No audit logs found</p>
            )}

            {data.length > 0 && (
                <>
                    <div className="table-responsive">
                        <table className="table table-bordered table-striped">
                            <thead className="table-dark">
                                <tr>
                                    <th>Audit ID</th>
                                    <th>Action</th>
                                    <th>Timestamp</th>
                                </tr>
                            </thead>
                            <tbody>
                                {data.map((m) => (
                                    // ✅ Fixed: added key prop
                                    <tr key={m.auditId}>
                                        <td>{m.auditId}</td>
                                        <td>{m.action}</td>
                                        <td>{m.timestamp
                                            ? new Date(m.timestamp).toLocaleString()
                                            : "—"}
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>

                    <div className="d-flex justify-content-between mt-3">
                        <button
                            className="btn btn-secondary"
                            onClick={prevHandler}
                            disabled={count === 0 || loading}
                        >
                            Prev
                        </button>
                        <span className="align-self-center">
                            Page {count + 1} of {totalPages}
                        </span>
                        <button
                            className="btn btn-secondary"
                            onClick={nextHandler}
                            disabled={count >= totalPages - 1 || loading}
                        >
                            Next
                        </button>
                    </div>
                </>
            )}
        </div>
    );
}