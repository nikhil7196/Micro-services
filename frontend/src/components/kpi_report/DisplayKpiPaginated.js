import axios from "axios";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";

export default function DisplayKpiPaginated() {

  const [records, setRecords] = useState([]);
  const [pgno, setPgno] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const size = 10;

  const [sorting, setSorting] = useState("kpiId");
  const [asc, setAsc] = useState(true);
  const [loading, setLoading] = useState(false);

  const fetchKpis = async (page = pgno, sortCol = sorting, order = asc) => {
    try {
      setLoading(true);

      const token = localStorage.getItem("token");

      if (!token) {
        toast.error("Please login first");
        return;
      }

      const url = "http://localhost:9002/api/kpi-report/fetchAllKPIReports/paginated";

      const res = await axios.get(url, {
        params: {
          page: page,
          size: size,
          sortBy: sortCol,
          asc: order
        },
        headers: {
          Authorization: "Bearer " + token
        }
      });

      // Correct response handling
      setRecords(res.data.content || []);
      setPgno(res.data.number);         // current page from backend
      setTotalPages(res.data.totalPages); // total pages

    } catch (err) {
      console.error(err);
      toast.error(err.response?.data || "Error fetching KPI reports");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchKpis(0);
  }, []);

  const handleSort = (column) => {
    if (sorting === column) {
      const newAsc = !asc;   // fix toggle bug
      setAsc(newAsc);
      fetchKpis(pgno, column, newAsc);
    } else {
      setSorting(column);
      setAsc(true);
      fetchKpis(pgno, column, true);
    }
  };

  const sortArrow = (column) => {
    if (sorting !== column) return "";
    return asc ? " ↑" : " ↓";
  };

  return (
    <div className="container mt-4">
      <h3 className="mb-4">KPI Reports (Paginated)</h3>

      {loading && <p>Loading...</p>}

      {!loading && records.length === 0 && (
        <p className="text-danger">No KPI reports found</p>
      )}

      {records.length > 0 && (
        <>
          <div className="table-responsive">
            <table className="table table-bordered table-hover table-striped">
              <thead className="table-dark">
                <tr>
                  <th onClick={() => handleSort("kpiId")} style={{ cursor: "pointer" }}>
                    KPI ID{sortArrow("kpiId")}
                  </th>

                  <th onClick={() => handleSort("kpiReportScope")} style={{ cursor: "pointer" }}>
                    Scope{sortArrow("kpiReportScope")}
                  </th>

                  <th onClick={() => handleSort("kpiMetrics")} style={{ cursor: "pointer" }}>
                    Metrics{sortArrow("kpiMetrics")}
                  </th>

                  <th onClick={() => handleSort("kpiGeneratedDate")} style={{ cursor: "pointer" }}>
                    Date{sortArrow("kpiGeneratedDate")}
                  </th>

                  <th>Compliance Report ID</th>
                </tr>
              </thead>

              <tbody>
                {records.map((e) => (
                  <tr key={e.kpiId}>
                    <td>{e.kpiId}</td>
                    <td>{e.kpiReportScope}</td>
                    <td>{e.kpiMetrics}</td>
                    <td>{new Date(e.kpiGeneratedDate).toLocaleDateString()}</td>
                    <td>{e.complianceReportId || "N/A"}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <div className="d-flex justify-content-between mt-3">
            <button
              className="btn btn-secondary"
              disabled={pgno === 0 || loading}
              onClick={() => fetchKpis(pgno - 1)}
            >
              Prev
            </button>

            <span className="align-self-center">
              Page {pgno + 1} of {totalPages}
            </span>

            <button
              className="btn btn-secondary"
              disabled={pgno >= totalPages - 1 || loading}
              onClick={() => fetchKpis(pgno + 1)}
            >
              Next
            </button>
          </div>
        </>
      )}
    </div>
  );
}