import { useNavigate } from "react-router-dom";

const BedIcon = ({ size = 22, color = "currentColor" }) => (
    <svg width={size} height={size} fill={color} viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
        <rect x="2" y="16" width="20" height="2.5" rx="1"/>
        <rect x="2" y="7" width="3" height="9.5" rx="1"/>
        <rect x="5" y="12" width="17" height="4.5" rx="1"/>
        <rect x="5.5" y="9.5" width="6" height="3" rx="1"/>
        <rect x="5" y="18.5" width="2" height="3" rx="1"/>
        <rect x="20" y="18.5" width="2" height="3" rx="1"/>
    </svg>
);

export default function NurseDashboard() {
    const navigate = useNavigate();
    const userName = localStorage.getItem("userName");

    const modules = [
        {
            useBedIcon: true,
            title: "Bed Management",
            description: "Add beds, assign patients, discharge",
            textColor: "#0d6efd",
            borderColor: "#0d6efd",
            btnColor: "#0d6efd",
            mainPath: "/bed",
            actions: [
                { label: "Assign Bed", path: "/bed/assignBed" },
                { label: "Discharge",  path: "/bed/dischargeBed" },
                { label: "All Beds",   path: "/bed/findAll" },
            ]
        },
        {
            icon: "bi-building",
            title: "Ward Management",
            description: "View wards and occupancy reports",
            textColor: "#6f42c1",
            borderColor: "#6f42c1",
            btnColor: "#6f42c1",
            mainPath: "/ward",
            actions: [
                { label: "All Wards",        path: "/ward/findAll" },
                { label: "Occupancy Report", path: "/ward/occupancy" },
            ]
        },
        {
            icon: "bi-heart-pulse-fill",
            title: "Patient Vitals",
            description: "Record and view patient vitals",
            textColor: "#dc3545",
            borderColor: "#dc3545",
            btnColor: "#dc3545",
            mainPath: "/nursedd/vitals/view",
            actions: [
                { label: "Add Vitals",  path: "/nursedd/vitals/add" },
                { label: "View Vitals", path: "/nursedd/vitals/view" },
            ]
        },
        {
            icon: "bi-pencil-square",
            title: "Care Notes",
            description: "Add and review patient care notes",
            textColor: "#198754",
            borderColor: "#198754",
            btnColor: "#198754",
            mainPath: "/nursedd/carenotes/view",
            actions: [
                { label: "Add Note",   path: "/nursedd/carenotes/add" },
                { label: "View Notes", path: "/nursedd/carenotes/view" },
            ]
        },
    ];

    return (
        <div>
            <div className="rounded-3 p-4 mb-4 text-white bg-primary bg-gradient">
                <h4 className="mb-1">👋 Welcome back, {userName}!</h4>
                <p className="mb-0 opacity-75">Nurse Dashboard — MediServe 360</p>
            </div>

            <div className="row g-4">
                {modules.map((mod) => (
                    <div className="col-md-6" key={mod.title}>
                        <div
                            className="card h-100 shadow-sm"
                            style={{
                                cursor: "pointer",
                                borderTop: `3px solid ${mod.borderColor}`,
                                borderLeft: "1px solid #dee2e6",
                                borderRight: "1px solid #dee2e6",
                                borderBottom: "1px solid #dee2e6",
                            }}
                            onClick={() => navigate(mod.mainPath)}
                            onMouseEnter={e => {
                                e.currentTarget.style.transform = "translateY(-3px)";
                                e.currentTarget.style.boxShadow = "0 6px 20px rgba(0,0,0,0.12)";
                            }}
                            onMouseLeave={e => {
                                e.currentTarget.style.transform = "translateY(0)";
                                e.currentTarget.style.boxShadow = "";
                            }}
                        >
                            <div className="card-body">
                                <div className="d-flex align-items-center gap-2 mb-2">
                                    {mod.useBedIcon
                                        ? <BedIcon size={22} color={mod.textColor} />
                                        : <i className={`bi ${mod.icon} fs-4`} style={{ color: mod.textColor }} aria-hidden="true" />
                                    }
                                    <h6 className="mb-0 fw-bold">{mod.title}</h6>
                                    <i className="bi bi-arrow-right ms-auto text-muted" />
                                </div>
                                <p className="text-muted small mb-3">{mod.description}</p>

                                <div className="d-flex flex-wrap gap-2">
                                    {mod.actions.map((action) => (
                                        <button
                                            key={action.label}
                                            className="btn btn-sm"
                                            style={{
                                                borderColor: mod.btnColor,
                                                color: mod.btnColor,
                                                backgroundColor: "transparent"
                                            }}
                                            onClick={(e) => {
                                                e.stopPropagation();
                                                navigate(action.path);
                                            }}
                                        >
                                            {action.label}
                                        </button>
                                    ))}
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}
