import axios from "axios";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";

export default function DisplayCompliancePaginated() {

  const [records, setRecords] = useState([]);
  const [pgno, setPgno] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const size = 10;

  const [sorting, setSorting] = useState("reportId");
  const [asc, setAsc] = useState(true);
  const [loading, setLoading] = useState(false);

  const fetchReports = async (page = pgno, sortCol = sorting, order = asc) => {
    try {
      setLoading(true);

      const url = "http://localhost:9002/api/compliance-reports/paginated";

      const res = await axios.get(url, {
        params: {
          page: page,       
          size: size,
          sortBy: sortCol,   
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
    fetchReports(0);
  }, []);

  const handleSort = (column) => {
    if (sorting === column) {
      const newAsc = !asc;
      setAsc(newAsc);
      fetchReports(pgno, column, newAsc);
    } else {
      setSorting(column);
      setAsc(true);
      fetchReports(pgno, column, true);
    }
  };

  const sortArrow = (column) => {
    if (sorting !== column) return "";
    return asc ? " ↑" : " ↓";
  };

  return (
    <div className="container mt-4">
      <h3 className="mb-4">Compliance Reports (Paginated)</h3>

      {loading && <p>Loading...</p>}

      {!loading && records.length === 0 && (
        <p className="text-danger">No reports found</p>
      )}

      {records.length > 0 && (
        <>
          <div className="table-responsive">
            <table className="table table-bordered table-hover table-striped">
              <thead className="table-dark">
                <tr>
                  <th onClick={() => handleSort("reportId")} style={{ cursor: "pointer" }}>
                    ID{sortArrow("reportId")}
                  </th>
                  <th onClick={() => handleSort("reportScope")} style={{ cursor: "pointer" }}>
                    Scope{sortArrow("reportScope")}
                  </th>
                  <th onClick={() => handleSort("reportMetrics")} style={{ cursor: "pointer" }}>
                    Metrics{sortArrow("reportMetrics")}
                  </th>
                  <th onClick={() => handleSort("reportGeneratedDate")} style={{ cursor: "pointer" }}>
                    Date{sortArrow("reportGeneratedDate")}
                  </th>
                </tr>
              </thead>

              <tbody>
                {records.map((e) => (
                  <tr key={e.reportId}>
                    <td>{e.reportId}</td>
                    <td>{e.reportScope}</td>
                    <td>{e.reportMetrics}</td>
                    <td>{new Date(e.reportGeneratedDate).toLocaleDateString()}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <div className="d-flex justify-content-between mt-3">
            <button
              className="btn btn-secondary"
              disabled={pgno === 0 || loading}
              onClick={() => fetchReports(pgno - 1)}
            >
              Prev
            </button>

            <span className="align-self-center">
              Page {pgno + 1} of {totalPages}
            </span>

            <button
              className="btn btn-secondary"
              disabled={pgno >= totalPages - 1 || loading}
              onClick={() => fetchReports(pgno + 1)}
            >
              Next
            </button>
          </div>
        </>
      )}
    </div>
  );
}
