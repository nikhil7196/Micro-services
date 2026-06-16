import { Link } from "react-router-dom";

const features = [
  {
    icon: "🧑‍⚕️",
    title: "Patient Registration & Records",
    desc: "Centralized patient demographics, medical history, and care records accessible to authorized staff in real time.",
  },
  {
    icon: "📅",
    title: "Appointment Scheduling",
    desc: "Streamlined booking system for patients and doctors with automated reminders and conflict detection.",
  },
  {
    icon: "🧾",
    title: "Billing & Invoicing",
    desc: "End-to-end financial workflows including invoice generation, insurance claim processing, and payment tracking.",
  },
  {
    icon: "📋",
    title: "Compliance Reporting",
    desc: "Built-in tools to generate regulatory reports and meet healthcare compliance standards with audit trail support.",
  },
  {
    icon: "🛏️",
    title: "Bed & Ward Management",
    desc: "Live visibility into bed availability, ward occupancy, and patient-to-nurse assignments across all units.",
  },
  {
    icon: "🔔",
    title: "Smart Notifications",
    desc: "Role-targeted alerts for appointments, critical patient events, billing updates, and security incidents.",
  },
];

const roles = [
  { icon: "⚙️", label: "Admin" },
  { icon: "👨‍⚕️", label: "Doctor" },
  { icon: "🏥", label: "Nurse" },
  { icon: "🧑", label: "Receptionist" },
  { icon: "💰", label: "Finance Officer" },
  { icon: "📋", label: "Compliance Officer" },
];

const stats = [
  { value: "6", label: "Role-Based Portals" },
  { value: "360°", label: "Operational Visibility" },
  { value: "1", label: "Unified Platform" },
  { value: "∞", label: "Scalable for Any Hospital" },
];

export default function Home() {
  return (
    <div style={{ fontFamily: "'Georgia', 'Times New Roman', serif", minHeight: "100vh", background: "#f0f4f8" }}>

      <nav style={{
        background: "#0d2d45",
        padding: "0 2rem",
        height: "64px",
        display: "flex",
        alignItems: "center",
        justifyContent: "space-between",
        boxShadow: "0 2px 12px rgba(0,0,0,0.25)",
        position: "sticky",
        top: 0,
        zIndex: 100,
      }}>
        <div style={{ display: "flex", alignItems: "center", gap: "0.75rem" }}>
          <div style={{
            width: 36, height: 36,
            background: "#1a73a7",
            borderRadius: "8px",
            display: "flex", alignItems: "center", justifyContent: "center",
          }}>
            <svg width="20" height="20" viewBox="0 0 32 32" fill="none">
              <rect x="12" y="4" width="8" height="24" rx="2" fill="white"/>
              <rect x="4" y="12" width="24" height="8" rx="2" fill="white"/>
            </svg>
          </div>
          <span style={{ color: "white", fontWeight: "700", fontSize: "1.15rem", letterSpacing: "0.02em" }}>
            MediServe<span style={{ color: "#7ec8f7" }}>360</span>
          </span>
        </div>

        <div style={{ display: "flex", gap: "0.75rem", alignItems: "center" }}>
          <Link
            to="/login"
            style={{
              color: "#a8d8f5",
              textDecoration: "none",
              padding: "0.4rem 1.1rem",
              border: "1.5px solid rgba(126,200,247,0.4)",
              borderRadius: "6px",
              fontSize: "0.88rem",
              fontWeight: "600",
              transition: "all 0.2s",
            }}
          >
            Sign In
          </Link>
          <Link
            to="/register"
            style={{
              background: "#1a73a7",
              color: "white",
              textDecoration: "none",
              padding: "0.4rem 1.2rem",
              borderRadius: "6px",
              fontSize: "0.88rem",
              fontWeight: "600",
              border: "1.5px solid #1a73a7",
            }}
          >
            Register
          </Link>
        </div>
      </nav>

      <div style={{
        background: "linear-gradient(135deg, #0d2d45 0%, #1a4a6e 50%, #0d3d5c 100%)",
        color: "white",
        padding: "5rem 2rem 4rem",
        textAlign: "center",
        position: "relative",
        overflow: "hidden",
      }}>
        <div style={{
          position: "absolute", top: "10%", right: "8%", opacity: 0.05,
        }}>
          <svg width="200" height="200" viewBox="0 0 32 32" fill="none">
            <rect x="12" y="0" width="8" height="32" rx="2" fill="white"/>
            <rect x="0" y="12" width="32" height="8" rx="2" fill="white"/>
          </svg>
        </div>
        <div style={{ position: "absolute", bottom: "5%", left: "5%", opacity: 0.04 }}>
          <svg width="140" height="140" viewBox="0 0 32 32" fill="none">
            <rect x="12" y="0" width="8" height="32" rx="2" fill="white"/>
            <rect x="0" y="12" width="32" height="8" rx="2" fill="white"/>
          </svg>
        </div>

        <div style={{
          display: "inline-flex", alignItems: "center", justifyContent: "center",
          width: 80, height: 80,
          background: "rgba(26,115,167,0.4)",
          borderRadius: "50%",
          border: "2px solid rgba(126,200,247,0.4)",
          marginBottom: "1.5rem",
        }}>
          <svg width="40" height="40" viewBox="0 0 32 32" fill="none">
            <rect x="12" y="4" width="8" height="24" rx="2" fill="white"/>
            <rect x="4" y="12" width="24" height="8" rx="2" fill="white"/>
          </svg>
        </div>

        <h1 style={{ fontSize: "clamp(2rem, 5vw, 3.2rem)", fontWeight: "700", marginBottom: "0.75rem", lineHeight: 1.2 }}>
          MediServe<span style={{ color: "#7ec8f7" }}>360</span>
        </h1>
        <p style={{ fontSize: "1.1rem", color: "#a8d8f5", maxWidth: "640px", margin: "0 auto 1rem", lineHeight: 1.7 }}>
          A unified Hospital Administration & Patient Care Platform built for hospitals, clinics, and healthcare networks.
        </p>
        <p style={{ fontSize: "0.95rem", color: "#7bb8d4", maxWidth: "560px", margin: "0 auto 2.5rem", lineHeight: 1.6 }}>
          From patient registration to compliance reporting — every workflow, every role, one platform.
        </p>

        {/* <div style={{ display: "flex", gap: "1rem", justifyContent: "center", flexWrap: "wrap" }}>
          <Link to="/register" style={{
            background: "#1a73a7",
            color: "white",
            textDecoration: "none",
            padding: "0.75rem 2rem",
            borderRadius: "8px",
            fontWeight: "700",
            fontSize: "0.95rem",
            border: "2px solid #1a73a7",
          }}>
            🏥 Get Started
          </Link>
          <Link to="/login" style={{
            background: "transparent",
            color: "white",
            textDecoration: "none",
            padding: "0.75rem 2rem",
            borderRadius: "8px",
            fontWeight: "600",
            fontSize: "0.95rem",
            border: "2px solid rgba(255,255,255,0.3)",
          }}>
            Sign In →
          </Link>
        </div> */}
      </div>

      <div style={{ background: "#1a73a7", padding: "1.5rem 2rem" }}>
        <div style={{
          maxWidth: "900px", margin: "0 auto",
          display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(160px, 1fr))",
          gap: "1rem", textAlign: "center",
        }}>
          {stats.map((s) => (
            <div key={s.label}>
              <div style={{ fontSize: "1.8rem", fontWeight: "700", color: "white" }}>{s.value}</div>
              <div style={{ fontSize: "0.78rem", color: "#c8e8f8", textTransform: "uppercase", letterSpacing: "0.08em" }}>{s.label}</div>
            </div>
          ))}
        </div>
      </div>

      <div style={{ maxWidth: "860px", margin: "0 auto", padding: "4rem 2rem 2rem" }}>
        <div style={{ textAlign: "center", marginBottom: "2.5rem" }}>
          <span style={{
            background: "#dbeef8", color: "#1a73a7",
            padding: "0.3rem 1rem", borderRadius: "20px",
            fontSize: "0.75rem", fontWeight: "700", letterSpacing: "0.1em", textTransform: "uppercase",
          }}>
            About the Platform
          </span>
          <h2 style={{ fontSize: "1.8rem", fontWeight: "700", color: "#0d2d45", marginTop: "1rem", marginBottom: "0.75rem" }}>
            End-to-End Hospital Management
          </h2>
          <p style={{ color: "#4a7a96", lineHeight: 1.8, fontSize: "0.95rem", maxWidth: "640px", margin: "0 auto" }}>
            MediServe360 consolidates patient demographics, medical records, appointments, and financial
            transactions into a single platform — giving administrators, clinicians, and finance teams
            complete operational visibility and streamlined workflows.
          </p>
        </div>

        <div style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit, minmax(250px, 1fr))",
          gap: "1.2rem",
          marginBottom: "4rem",
        }}>
          {features.map((f) => (
            <div key={f.title} style={{
              background: "white",
              borderRadius: "12px",
              padding: "1.5rem",
              boxShadow: "0 2px 10px rgba(13,45,69,0.08)",
              borderLeft: "4px solid #1a73a7",
            }}>
              <div style={{ fontSize: "1.8rem", marginBottom: "0.75rem" }}>{f.icon}</div>
              <h6 style={{ fontWeight: "700", color: "#0d2d45", marginBottom: "0.4rem", fontSize: "0.95rem" }}>{f.title}</h6>
              <p style={{ color: "#5a8a9f", fontSize: "0.82rem", lineHeight: 1.6, margin: 0 }}>{f.desc}</p>
            </div>
          ))}
        </div>

        <div style={{
          background: "white",
          borderRadius: "14px",
          padding: "2rem",
          boxShadow: "0 2px 10px rgba(13,45,69,0.08)",
          marginBottom: "4rem",
          textAlign: "center",
        }}>
          <h5 style={{ fontWeight: "700", color: "#0d2d45", marginBottom: "0.4rem" }}>Built for Every Role</h5>
          <p style={{ color: "#5a8a9f", fontSize: "0.85rem", marginBottom: "1.8rem" }}>
            Role-based access ensures every user sees only what they need — securely and efficiently.
          </p>
          <div style={{ display: "flex", flexWrap: "wrap", gap: "0.75rem", justifyContent: "center" }}>
            {roles.map((r) => (
              <div key={r.label} style={{
                background: "#f0f7fc",
                border: "1.5px solid #c8dfe9",
                borderRadius: "8px",
                padding: "0.6rem 1.2rem",
                display: "flex", alignItems: "center", gap: "0.5rem",
                fontSize: "0.85rem", fontWeight: "600", color: "#1a3c5e",
              }}>
                <span>{r.icon}</span> {r.label}
              </div>
            ))}
          </div>
        </div>

        <div style={{
          background: "linear-gradient(135deg, #0d2d45 0%, #1a4a6e 100%)",
          borderRadius: "14px",
          padding: "2.5rem 2rem",
          textAlign: "center",
          color: "white",
          marginBottom: "3rem",
        }}>
          <h5 style={{ fontWeight: "700", fontSize: "1.4rem", marginBottom: "0.5rem" }}>
            Ready to streamline your hospital operations?
          </h5>
          <p style={{ color: "#a8d8f5", fontSize: "0.9rem", marginBottom: "1.8rem" }}>
            Create your account and get full access to MediServe360's complete suite of tools.
          </p>
          <div style={{ display: "flex", gap: "1rem", justifyContent: "center", flexWrap: "wrap" }}>
            <Link to="/register" style={{
              background: "#1a73a7", color: "white", textDecoration: "none",
              padding: "0.7rem 1.8rem", borderRadius: "8px", fontWeight: "700", fontSize: "0.9rem",
            }}>
              🏥 Create Account
            </Link>
            <Link to="/login" style={{
              background: "transparent", color: "white", textDecoration: "none",
              padding: "0.7rem 1.8rem", borderRadius: "8px", fontWeight: "600", fontSize: "0.9rem",
              border: "2px solid rgba(255,255,255,0.3)",
            }}>
              Sign In →
            </Link>
          </div>
        </div>

      </div>

      <div style={{
        background: "#0d2d45",
        color: "#5a8a9f",
        textAlign: "center",
        padding: "1.5rem",
        fontSize: "0.8rem",
      }}>
        © {new Date().getFullYear()} MediServe360 · Hospital Administration & Patient Care Platform
      </div>

    </div>
  );
}
