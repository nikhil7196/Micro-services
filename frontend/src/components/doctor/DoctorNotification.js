import { useState, useEffect } from "react";
import axios from "axios";
import TopNavbar from "../common/TopNavbar";

const BASE = "http://localhost:9002";

export default function DoctorNotification() {

    const [notifications, setNotifications] = useState([]);
    const [loading, setLoading] = useState(true);
    const token = localStorage.getItem("token");
    const headers = { Authorization: "Bearer " + token };

    async function fetchNotifications() {
        try {
            // ✅ Fixed: /notification not /api/notification
            const res = await axios.get(`${BASE}/notification/fetchAllNotificationsPaginated`, {
                params: { pgno: 0, size: 20, sorting: "notificationId", asc: false },
                headers
            });
            setNotifications(res.data.content || []);
        } catch (err) {
            console.error("Failed to fetch notifications:", err.message);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        fetchNotifications();
    }, []);

    const statusColor = (status) => {
        switch ((status || "").toUpperCase()) {
            case "UNREAD": return { bg: "#fee2e2", color: "#dc2626" };
            case "READ":   return { bg: "#dcfce7", color: "#16a34a" };
            default:       return { bg: "#f1f5f9", color: "#475569" };
        }
    };

    const categoryIcon = (category) => {
        switch ((category || "").toUpperCase()) {
            case "APPOINTMENT":  return "📅";
            case "CANCELLATION": return "❌";
            case "RESCHEDULE":   return "🔄";
            case "ALERT":        return "🚨";
            case "REMINDER":     return "⏰";
            default:             return "ℹ️";
        }
    };

    if (loading) return (
        <>
            <TopNavbar />
            <div className="d-flex justify-content-center align-items-center py-5">
                <div className="spinner-border text-primary"></div>
            </div>
        </>
    );

    return (
        <div className="min-vh-100" style={{ backgroundColor: "#f0f2f5" }}>
            <TopNavbar />

            <div className="container py-4" style={{ maxWidth: 900 }}>

                <h5 className="fw-bold mb-4" style={{ color: "#0f172a" }}>
                    🔔 My Notifications
                </h5>

                {notifications.length === 0 ? (
                    <div className="card border-0 shadow-sm text-center py-5"
                        style={{ borderRadius: "14px" }}>
                        <div style={{ fontSize: "3rem" }}>🔕</div>
                        <h6 className="fw-semibold mt-3 mb-1">No notifications yet</h6>
                        <p className="text-muted small mb-0">You're all caught up!</p>
                    </div>
                ) : (
                    <div className="d-flex flex-column gap-3">
                        {notifications.map((m) => {
                            const id = m.notificationId || m.notificationID;
                            const st = statusColor(m.status);
                            return (
                                <div key={id} className="card border-0 shadow-sm"
                                    style={{ borderRadius: "14px", borderLeft: "4px solid #3b82f6" }}>
                                    <div className="card-body d-flex align-items-start gap-3 p-4">

                                        <div style={{
                                            width: 44, height: 44, borderRadius: "12px",
                                            background: "#dbeafe", flexShrink: 0,
                                            display: "flex", alignItems: "center",
                                            justifyContent: "center", fontSize: "1.3rem"
                                        }}>
                                            {categoryIcon(m.category)}
                                        </div>

                                        <div style={{ flex: 1 }}>
                                            <div className="d-flex align-items-center gap-2 mb-1 flex-wrap">
                                                <span style={{
                                                    fontSize: "11px", fontWeight: 700,
                                                    padding: "2px 10px", borderRadius: "20px",
                                                    background: "#dbeafe", color: "#1d4ed8"
                                                }}>
                                                    {m.category}
                                                </span>
                                                <span style={{
                                                    fontSize: "11px", fontWeight: 700,
                                                    padding: "2px 10px", borderRadius: "20px",
                                                    background: st.bg, color: st.color
                                                }}>
                                                    {m.status}
                                                </span>
                                                <span style={{ fontSize: "11px", color: "#94a3b8", marginLeft: "auto" }}>
                                                    #{id}
                                                </span>
                                            </div>
                                            <p className="mb-1" style={{ fontSize: "14px", color: "#334155", lineHeight: 1.6 }}>
                                                {m.message}
                                            </p>
                                            {m.createdDate && (
                                                <small className="text-muted">
                                                    🕐 {new Date(m.createdDate).toLocaleString()}
                                                </small>
                                            )}
                                        </div>

                                    </div>
                                </div>
                            );
                        })}
                    </div>
                )}
            </div>
        </div>
    );
}