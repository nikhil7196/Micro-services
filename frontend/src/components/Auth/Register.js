import axios from "axios";
import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

export default function Register() {
    const [userName, setUserName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [role, setRole] = useState("");
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const register = (event) => {
        event.preventDefault();
        setError("");
        setSuccess("");
        if(phoneNumber.length!==10){
            setError("Please enter correct phone number");
            return;
        }
        if(password.length<6){
            setError("Password must be at least 6 characters");
            return;
        }
        if (!userName || !email || !password || !role || !phoneNumber) {
            setError("Please fill in all fields.");
            return;
        }
        setLoading(true);
        axios.post("http://localhost:9002/api/auth/register", {
            userName: userName.trim(),
            email: email.trim(),
            password, role, phoneNumber,
        })
        .then(() => {
            localStorage.setItem("email1",email);
            if(role==="DOCTOR"){
                navigate("/doctorreg");
            }else{
                setSuccess("Account created successfully! Redirecting to login...");
                setTimeout(() => navigate("/login"), 1000);
            }
        })
        .catch((err) => {
            if (err.response?.data.httpStatusCode === 404) {
                setError("Email already registered. Try a different one.");
            } else {
                setError("Registration failed. Please try again.");
            }
            setLoading(false);
        });
    };

    return (
        <div
            className="min-vh-100 d-flex align-items-center justify-content-center py-4 px-3"
            style={{ background: "linear-gradient(135deg, #e8f4f8 0%, #d6eaf8 50%, #eaf4fb 100%)" }}
        >
            <div
                className="card shadow-lg border-0"
                style={{ width: "100%", maxWidth: 480, borderRadius: "16px" }}
            >
                <div className="card-body p-4 p-md-5">

                    <div className="text-center mb-4">
                        <div
                            className="d-inline-flex align-items-center justify-content-center rounded-circle mb-3"
                            style={{ width: 64, height: 64, background: "#1a73a7" }}
                        >
                            <svg width="32" height="32" viewBox="0 0 32 32" fill="none">
                                <rect x="12" y="4" width="8" height="24" rx="2" fill="white"/>
                                <rect x="4" y="12" width="24" height="8" rx="2" fill="white"/>
                            </svg>
                        </div>
                        <h5 className="fw-bold mb-1" style={{ color: "#1a3c5e" }}>MediServe</h5>
                        <p className="small mb-0" style={{ color: "#5a8fa8" }}>
                            Create your hospital account
                        </p>
                    </div>

                    <div className="d-flex align-items-center gap-2 mb-4">
                        <hr className="flex-grow-1 m-0" style={{ borderColor: "#c8dfe9" }} />
                        <span className="small px-2 py-1 rounded-pill fw-semibold"
                            style={{ background: "#e3f2fb", color: "#1a73a7", fontSize: "0.7rem", letterSpacing: "0.06em" }}>
                            NEW REGISTRATION
                        </span>
                        <hr className="flex-grow-1 m-0" style={{ borderColor: "#c8dfe9" }} />
                    </div>

                    {error && (
                        <div className="alert alert-danger py-2 small" role="alert">
                            ⚠️ {error}
                        </div>
                    )}
                    {success && (
                        <div className="alert alert-success py-2 small" role="alert">
                             {success}
                        </div>
                    )}

                    <form onSubmit={register}>
                        <div className="row g-3 mb-3">
                            <div className="col-6">
                                <label className="form-label small fw-semibold" style={{ color: "#1a3c5e" }}>
                                    Full Name
                                </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="Jane Doe"
                                    value={userName}
                                    onChange={(e) => setUserName(e.target.value)}
                                    style={{ borderColor: "#b0cfe0", borderRadius: "8px" }}
                                />
                            </div>
                            <div className="col-6">
                                <label className="form-label small fw-semibold" style={{ color: "#1a3c5e" }}>
                                    Phone
                                </label>
                                <input
                                    type="tel"
                                    className="form-control"
                                    placeholder="+91 00000 00000"
                                    value={phoneNumber}
                                    onChange={(e) => setPhoneNumber(e.target.value)}
                                    style={{ borderColor: "#b0cfe0", borderRadius: "8px" }}
                                />
                            </div>
                        </div>

                        <div className="mb-3">
                            <label className="form-label small fw-semibold" style={{ color: "#1a3c5e" }}>
                                Email Address
                            </label>
                            <input
                                type="email"
                                className="form-control"
                                placeholder="jane@gmail.com"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                style={{ borderColor: "#b0cfe0", borderRadius: "8px" }}
                            />
                        </div>

                        <div className="mb-3">
                            <label className="form-label small fw-semibold" style={{ color: "#1a3c5e" }}>
                                Password
                            </label>
                            <input
                                type="password"
                                className="form-control"
                                placeholder="Min. 6 characters"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                style={{ borderColor: "#b0cfe0", borderRadius: "8px" }}
                            />
                        </div>

                        <div className="mb-4">
                            <label className="form-label small fw-semibold" style={{ color: "#1a3c5e" }}>
                                Role
                            </label>
                            <select
                                className="form-select"
                                value={role}
                                onChange={(e) => setRole(e.target.value)}
                                style={{ borderColor: "#b0cfe0", borderRadius: "8px", color: role ? "#212529" : "#6c757d" }}
                            >
                                <option value="" disabled>Select role</option>
                                <option value="RECEPTIONIST">Receptionist</option>
                                <option value="DOCTOR">Doctor</option>
                                <option value="NURSE">Nurse</option>
                                <option value="FINANCEOFFICER">Finance Officer</option>
                                <option value="COMPLIANCE_OFFICER">Compliance Officer</option>
                            </select>
                        </div>

                        <button
                            type="submit"
                            className="btn w-100 fw-semibold text-white"
                            disabled={loading}
                            style={{ background: "#1a73a7", borderRadius: "8px", border: "none", padding: "0.6rem" }}
                        >
                            {loading ? (
                                <>
                                    <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                                    Creating account...
                                </>
                            ) : "🏥 Create Account"}
                        </button>
                    </form>

                    <hr className="my-4" style={{ borderColor: "#c8dfe9" }} />

                    <p className="text-center small mb-0" style={{ color: "#5a8fa8" }}>
                        Already have an account?{" "}
                        <Link to="/login" className="fw-semibold text-decoration-none" style={{ color: "#1a73a7" }}>
                            Sign in
                        </Link>
                    </p>

                </div>
            </div>
        </div>
    );
}
