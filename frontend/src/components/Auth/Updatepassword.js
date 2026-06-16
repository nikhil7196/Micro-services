import axios from "axios";
import { useNavigate, Link } from "react-router";
import { toast } from "react-toastify";
import { useState } from "react";

export default function Updatepassword() {
    const navigate = useNavigate();
    const [email, setEmail] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [newPassword1, setNewPassword1] = useState("");
    const [loading, setLoading] = useState(false);

    async function changePassword(e) {
        e.preventDefault();
        if (!email || !newPassword || !newPassword1) {
            toast.error("Please fill in all fields.");
            return;
        }
        if (newPassword !== newPassword1) {
            toast.error("Passwords must match");
            return;
        }
        try {
            setLoading(true);
            let url = `http://localhost:9002/api/auth/updatepassword/${email}/${newPassword}`;
            await axios.put(url);
            toast.success("Password successfully changed");
            navigate("/login");
        } catch (err) {
            toast.error(err.response?.data?.message || err.message || "Failed to update password.");
        } finally {
            setLoading(false);
        }
    }

    return (
        <div
            className="min-vh-100 d-flex align-items-center justify-content-center px-3"
            style={{ background: "#0f2d3d" }}
        >
            <div
                className="card border-0 shadow-lg overflow-hidden"
                style={{ width: "100%", maxWidth: 780, borderRadius: "18px", minHeight: "420px" }}
            >
                <div className="row g-0 h-100">

                    <div
                        className="col-md-5 d-none d-md-flex flex-column align-items-center justify-content-center p-4 text-white text-center"
                        style={{ background: "linear-gradient(160deg, #1a73a7 0%, #0d4f73 100%)" }}
                    >
                        <div
                            className="rounded-circle d-flex align-items-center justify-content-center mb-4"
                            style={{ width: 88, height: 88, background: "rgba(255,255,255,0.15)", border: "2px solid rgba(255,255,255,0.3)" }}
                        >
                            <svg width="40" height="40" viewBox="0 0 40 40" fill="none">
                                <rect x="15" y="5" width="10" height="30" rx="3" fill="white"/>
                                <rect x="5" y="15" width="30" height="10" rx="3" fill="white"/>
                            </svg>
                        </div>
                        <h4 className="fw-bold mb-2">MediServe</h4>
                        <p className="small mb-4" style={{ opacity: 0.8, lineHeight: 1.6 }}>
                            Integrated Hospital Management System
                        </p>
                        <div className="d-flex flex-column gap-2 w-100 px-2">
                            {["Patient Care", "Staff Management", "Finance & Billing"].map((item) => (
                                <div
                                    key={item}
                                    className="d-flex align-items-center gap-2 px-3 py-2 rounded-3"
                                    style={{ background: "rgba(255,255,255,0.1)", fontSize: "0.8rem" }}
                                >
                                    <span style={{ color: "#7dd3f7" }}>✦</span>
                                    {item}
                                </div>
                            ))}
                        </div>
                    </div>

                    <div className="col-md-7 d-flex align-items-center bg-white">
                        <div className="w-100 p-4 p-md-5">

                            <div className="d-flex d-md-none align-items-center gap-2 mb-4">
                                <div
                                    className="rounded-circle d-flex align-items-center justify-content-center"
                                    style={{ width: 36, height: 36, background: "#1a73a7" }}
                                >
                                    <svg width="18" height="18" viewBox="0 0 32 32" fill="none">
                                        <rect x="12" y="4" width="8" height="24" rx="2" fill="white"/>
                                        <rect x="4" y="12" width="24" height="8" rx="2" fill="white"/>
                                    </svg>
                                </div>
                                <span className="fw-bold" style={{ color: "#1a3c5e" }}>MediServe</span>
                            </div>

                            <h5 className="fw-bold mb-1" style={{ color: "#1a3c5e" }}>Reset Password</h5>
                            <p className="small mb-4" style={{ color: "#5a8fa8" }}>Enter your email and choose a new password</p>

                            <form onSubmit={changePassword}>
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
                                        New Password
                                    </label>
                                    <input
                                        type="password"
                                        className="form-control"
                                        placeholder="••••••••"
                                        value={newPassword}
                                        onChange={(e) => setNewPassword(e.target.value)}
                                        style={{ borderColor: "#b0cfe0", borderRadius: "8px" }}
                                    />
                                </div>

                                <div className="mb-4">
                                    <label className="form-label small fw-semibold" style={{ color: "#1a3c5e" }}>
                                        Re-Enter New Password
                                    </label>
                                    <input
                                        type="password"
                                        className="form-control"
                                        placeholder="••••••••"
                                        value={newPassword1}
                                        onChange={(e) => setNewPassword1(e.target.value)}
                                        style={{ borderColor: "#b0cfe0", borderRadius: "8px" }}
                                    />
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
                                            Updating...
                                        </>
                                    ) : "Change Password →"}
                                </button>
                            </form>

                            <hr className="my-4" style={{ borderColor: "#c8dfe9" }} />

                            <p className="text-center small mb-0" style={{ color: "#5a8fa8" }}>
                                Remembered your password?{" "}
                                <Link to="/login" className="fw-semibold text-decoration-none" style={{ color: "#1a73a7" }}>
                                    Sign In
                                </Link>
                            </p>

                        </div>
                    </div>

                </div>
            </div>
        </div>
    );
}