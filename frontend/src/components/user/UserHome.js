import { Link, Outlet, useLocation } from "react-router-dom";
import TopNavbar from "../common/TopNavbar";

const navLinks = [
  { to: "add",       label: "Add User",       icon: "➕" },
  { to: "find",      label: "Find User",      icon: "🔍" },
  { to: "findall",   label: "All Users",      icon: "👥" },
  { to: "paginated", label: "Paginated View", icon: "📄" },
  
];

export default function UserHome() {
  const location = useLocation();

  return (
    <div className="min-vh-100 bg-light">

      <TopNavbar />

      <div className="container-fluid px-4 py-4">

        <div className="mb-4">
          <Link to="/user" className="text-decoration-none">
            <h4 className="fw-bold text-dark mb-1">
              User Management
            </h4>
          </Link>
          <p className="text-muted small mb-0">
            Manage users · Roles · Access control
          </p>
        </div>

        <div className="row g-3 mb-4 flex-nowrap">
          {navLinks.map((link) => (
            <div className="col" key={link.to}>
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

        {!location.pathname.startsWith("/user/") ? (

          <div className="card shadow-sm p-4 text-muted text-center">
            <h6 className="mb-2">Welcome to User Module</h6>
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