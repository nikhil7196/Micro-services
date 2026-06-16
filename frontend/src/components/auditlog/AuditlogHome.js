import { Link, Outlet, useLocation } from "react-router";
import TopNavbar from "../common/TopNavbar";

const navLinks = [
  { to: "add",       label: "Add Log",        icon: "➕" },
  { to: "find",      label: "Find Log",       icon: "🔍" },
  { to: "findall",   label: "All Logs",       icon: "📋" },
  { to: "paginated", label: "Paginated View", icon: "📄" },
];

export default function AuditlogHome() {
  const location = useLocation();

  return (
    <div className="min-vh-100 bg-light">

      <TopNavbar />

      <div className="container-fluid px-4 py-4">

        <div className="mb-4">
          <Link to="/auditlog" className="text-decoration-none">
            <h4 className="fw-bold text-dark mb-1">
              Audit Log
            </h4>
          </Link>
          <p className="text-muted small mb-0">
            Track actions · Security events · Compliance
          </p>
        </div>

        <div className="row g-3 mb-4">
          {navLinks.map((link) => (
            <div className="col-6 col-sm-4 col-md-3" key={link.to}>
              <Link
                to={link.to}
                className="btn btn-outline-dark w-100 py-4 d-flex flex-column align-items-center gap-2 text-decoration-none"
                style={{
                  borderRadius: "12px",
                  minHeight: "100px",
                }}
              >
                <span style={{ fontSize: "1.8rem" }}>{link.icon}</span>
                <span className="fw-semibold small">{link.label}</span>
              </Link>
            </div>
          ))}
        </div>

        {!location.pathname.startsWith("/auditlog/") ? (

          <div className="card shadow-sm p-4 text-muted text-center">
            <h6 className="mb-2">Welcome to Audit Log Module</h6>
            <p className="mb-0">Select an option above to continue</p>
          </div>

        ) : (

          <div className="card shadow-sm">
            <div className="card-body">
              <Outlet />
            </div>
          </div>

        )}

      </div>
    </div>
  );
}
