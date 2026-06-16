import { Link, Outlet, useLocation } from "react-router";
import { useState, useEffect } from "react";
import axios from "axios";
import TopNavbar from "../common/TopNavbar";
import DisplayCompliancePaginated from "../compliance_report/DisplayCompliancePaginated";
import DisplayKpiPaginated from "../kpi_report/DisplayKpiPaginated";

const navLinks = [
  { to: "userapproval",  label: "User Approval",  icon: "✅" },
  { to: "userp",         label: "User",          icon: "👤" },
  { to: "patientp",      label: "Patient",        icon: "🏥" },
  { to: "doctorp",       label: "Doctor",         icon: "👨‍⚕️" },
  { to: "bedp",          label: "Bed",            icon: "🛏️" },
  { to: "wardp",         label: "Ward",           icon: "🏨" },
  { to: "adminp",        label: "Audit Log",      icon: "🔍" },
  { to: "notificationp", label: "Notification",   icon: "🔔" },
  { to: "appointmentp",  label: "Appointment",    icon: "📅" },
  { to: "compliancep",   label: "Compliance",     icon: "📋" },
  { to: "kpip",          label: "KPI Reports",    icon: "📈" },
];

export default function AdminDash() {
  const location = useLocation();
  const [sidebarOpen, setSidebarOpen] = useState(true);

  const [totalUsers,    setTotalUsers]    = useState(0);
  const [totalPatients, setTotalPatients] = useState(0);
  const [totalBeds,     setTotalBeds]     = useState(0);
  const [totalDoctors,  setTotalDoctors]  = useState(0);

  const stats = [
    { label: "Total Users",     value: totalUsers,    icon: "👤", color: "#3b82f6", bg: "#dbeafe" },
    { label: "Active Patients", value: totalPatients, icon: "🏥", color: "#10b981", bg: "#d1fae5" },
    { label: "Beds Occupied",   value: totalBeds,     icon: "🛏️", color: "#f59e0b", bg: "#fef3c7" },
    { label: "Active Doctors",  value: totalDoctors,  icon: "👨‍⚕️", color: "#ef4444", bg: "#fee2e2" },
  ];

  async function getUsers() {
    try {
      const res = await axios.get("http://localhost:9002/user/fetchallusers", {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      });
      setTotalUsers(res.data.length);
    } catch (err) { console.error(err.message); }
  }

  async function getDoctors() {
    try {
      const res = await axios.get("http://localhost:9002/api/doctor/getAll", {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      });
      setTotalDoctors(res.data.length);
    } catch (err) { console.error(err.message); }
  }

  async function getPatient() {
    try {
      const res = await axios.get("http://localhost:9002/api/patient/fetchAllPatients", {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      });
      setTotalPatients(res.data.filter((p) => p.patientStatus === "Admitted").length);
    } catch (err) { console.error(err.message); }
  }

  async function getBeds() {
    try {
      const res = await axios.get("http://localhost:9002/api/beds/getAllBeds", {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      });
      setTotalBeds(res.data.filter((b) => b.bedStatus === "OCCUPIED").length);
    } catch (err) { console.error(err.message); }
  }

  useEffect(() => {
    getUsers();
    getPatient();
    getBeds();
    getDoctors();
  }, []);

  const isSubRoute = navLinks.some((link) => location.pathname.includes(link.to));
  const userName = localStorage.getItem("userName") || "Admin";

  return (
    <div style={{ minHeight: "100vh", display: "flex", flexDirection: "column", backgroundColor: "#f0f2f5" }}>

      <TopNavbar />

      <div style={{ display: "flex", flex: 1, overflow: "hidden" }}>

        <aside style={{
          width: sidebarOpen ? "240px" : "0px",
          minWidth: sidebarOpen ? "240px" : "0px",
          backgroundColor: "#0f172a",
          overflowY: "auto",
          overflowX: "hidden",
          display: "flex",
          flexDirection: "column",
          transition: "width 0.2s, min-width 0.2s",
        }}>

          <div style={{
            padding: "20px 16px 12px",
            borderBottom: "1px solid rgba(255,255,255,0.08)",
            whiteSpace: "nowrap",
          }}>
            <div style={{ fontSize: "10px", fontWeight: 700, color: "#64748b", letterSpacing: "0.1em", textTransform: "uppercase" }}>
              Navigation
            </div>
          </div>

          {navLinks.map((link) => {
            const isActive = location.pathname.includes(link.to);
            return (
              <Link
                key={link.to}
                to={link.to}
                style={{
                  display: "flex",
                  alignItems: "center",
                  gap: "12px",
                  padding: "11px 18px",
                  fontSize: "13.5px",
                  color: isActive ? "#fff" : "#94a3b8",
                  textDecoration: "none",
                  borderLeft: isActive ? "3px solid #3b82f6" : "3px solid transparent",
                  backgroundColor: isActive ? "rgba(59,130,246,0.15)" : "transparent",
                  fontWeight: isActive ? 600 : 400,
                  transition: "all 0.15s",
                  whiteSpace: "nowrap",
                }}
              >
                <span style={{ fontSize: "16px", width: "20px", textAlign: "center" }}>{link.icon}</span>
                <span>{link.label}</span>
              </Link>
            );
          })}

          <div style={{ marginTop: "auto", padding: "16px", borderTop: "1px solid rgba(255,255,255,0.08)" }}>
            <div style={{ display: "flex", alignItems: "center", gap: "10px" }}>
              <div style={{
                width: 34, height: 34, borderRadius: "50%",
                background: "linear-gradient(135deg,#0ea5e9,#3b82f6)",
                display: "flex", alignItems: "center", justifyContent: "center",
                fontWeight: 700, color: "#fff", fontSize: 14, flexShrink: 0,
              }}>
                {userName.charAt(0).toUpperCase()}
              </div>
              <div style={{ overflow: "hidden" }}>
                <div style={{ fontSize: "13px", color: "#e2e8f0", fontWeight: 600, whiteSpace: "nowrap", overflow: "hidden", textOverflow: "ellipsis" }}>{userName}</div>
                <div style={{ fontSize: "11px", color: "#64748b" }}>Administrator</div>
              </div>
            </div>
          </div>

        </aside>

        <main style={{ flex: 1, overflowY: "auto", padding: "28px 32px" }}>

          {!isSubRoute && (
            <>
              <div
                className="mb-4 p-4 rounded-4 text-white d-flex align-items-center justify-content-between"
                style={{
                  background: "linear-gradient(135deg, #0f172a 0%, #1e3a5f 60%, #0f3460 100%)",
                  minHeight: 100,
                }}
              >
                <div>
                  <h4 className="fw-bold mb-1">Welcome back, {userName} 👋</h4>
                  <p className="mb-0 small" style={{ color: "#93c5fd" }}>
                    Here's what's happening in your hospital today.
                  </p>
                </div>
                <div style={{ fontSize: "3rem", opacity: 0.3 }}>🏥</div>
              </div>
              <div className="row g-3 mb-4">
                {stats.map((s) => (
                  <div className="col-12 col-sm-6 col-xl-3" key={s.label}>
                    <div
                      className="card border-0 shadow-sm h-100"
                      style={{ borderRadius: "14px", overflow: "hidden" }}
                    >
                      <div className="card-body p-4">
                        <div className="d-flex align-items-center justify-content-between mb-3">
                          <div style={{
                            width: 48, height: 48, borderRadius: "12px",
                            background: s.bg,
                            display: "flex", alignItems: "center", justifyContent: "center",
                            fontSize: "1.4rem",
                          }}>
                            {s.icon}
                          </div>
                          <span style={{
                            fontSize: "11px", fontWeight: 700, color: s.color,
                            background: s.bg, padding: "3px 10px", borderRadius: "20px",
                            letterSpacing: "0.04em",
                          }}>
                            LIVE
                          </span>
                        </div>
                        <h3 className="fw-bold mb-1" style={{ color: "#0f172a" }}>{s.value}</h3>
                        <p className="mb-0 small fw-semibold" style={{ color: "#64748b", textTransform: "uppercase", fontSize: "0.7rem", letterSpacing: "0.06em" }}>
                          {s.label}
                        </p>
                      </div>
                      <div style={{ height: 4, background: s.color }} />
                    </div>
                  </div>
                ))}
              </div>
            </>
          )}

          <Outlet />
        </main>
      </div>
    </div>
  );
}