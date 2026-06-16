import axios from "axios";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";

export default function Patientpage() {

    const [records, setRecords] = useState([]);
    const [pgno, setPgno] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const size = 10;

    const [sorting, setSorting] = useState("patientId");
    const [asc, setAsc] = useState(true);
    const [loading, setLoading] = useState(false);

    const fetchPatients = async (page = pgno, sortCol = sorting, order = asc) => {
        try {
            setLoading(true);

            const url = "http://localhost:9002/api/patient/fetchAllPatientsPaginated";

            const res = await axios.get(url, {
                params: {
                    pgno: page,
                    size: size,
                    sorting: sortCol,
                    asc: order
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            });

            setRecords(res.data.content || []);
            setPgno(res.data.number);
            setTotalPages(res.data.totalPages);

        } catch (err) {
            toast.error(err.response?.data || err.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchPatients(0);
    }, []);

    const handleSort = (column) => {
        if (sorting === column) {
            const newAsc = !asc;
            setAsc(newAsc);
            fetchPatients(pgno, column, newAsc);
        } else {
            setSorting(column);
            setAsc(true);
            fetchPatients(pgno, column, true);
        }
    };

    const sortArrow = (column) => {
        if (sorting !== column) return "";
        return asc ? " ↑" : " ↓";
    };

    return (
        <div className="container mt-4">
            <h3 className="mb-4">Display Patients (Paginated)</h3>

            {loading && <p>Loading...</p>}

            {!loading && records.length === 0 && (
                <p className="text-danger">No patients found</p>
            )}

            {records.length > 0 && (
                <>
                    <div className="table-responsive">
                        <table className="table table-bordered table-hover table-striped">
                            <thead className="table-dark">
                                <tr>
                                    <th onClick={() => handleSort("patientId")} style={{ cursor: "pointer" }}>
                                        Id{sortArrow("patientId")}
                                    </th>
                                    <th onClick={() => handleSort("patientName")} style={{ cursor: "pointer" }}>
                                        Name{sortArrow("patientName")}
                                    </th>
                                    <th onClick={() => handleSort("patientDOB")} style={{ cursor: "pointer" }}>
                                        DOB{sortArrow("patientDOB")}
                                    </th>
                                    <th onClick={() => handleSort("patientGender")} style={{ cursor: "pointer" }}>
                                        Gender{sortArrow("patientGender")}
                                    </th>
                                    <th>Phone</th>
                                    <th>Medical History</th>
                                    <th>Status</th>
                                </tr>
                            </thead>

                            <tbody>
                                {records.map((e) => (
                                    <tr key={e.patientId}>
                                        <td>{e.patientId}</td>
                                        <td>{e.patientName}</td>
                                        <td>{new Date(e.patientDOB).toLocaleDateString()}</td>
                                        <td>{e.patientGender}</td>
                                        <td>{e.patientPhoneNumber}</td>
                                        <td>{e.patientMedicalHistory}</td>
                                        <td>{e.patientStatus}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>

                    <div className="d-flex justify-content-between mt-3">
                        <button
                            className="btn btn-secondary"
                            disabled={pgno === 0 || loading}
                            onClick={() => fetchPatients(pgno - 1)}
                        >
                            Prev
                        </button>

                        <span className="align-self-center">
                            Page {pgno + 1} of {totalPages}
                        </span>

                        <button
                            className="btn btn-secondary"
                            disabled={pgno >= totalPages - 1 || loading}
                            onClick={() => fetchPatients(pgno + 1)}
                        >
                            Next
                        </button>
                    </div>
                </>
            )}
        </div>
    );
}