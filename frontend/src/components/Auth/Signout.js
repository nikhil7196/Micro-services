import { useNavigate } from "react-router-dom";

export default function Signout() {
  const navigate = useNavigate();
  return (
    <button
            className="btn btn-sm"
            title="Logout"
            style={{
              background: "#ef4444",
              color: "white",
            }}
            onClick={() => {
              localStorage.clear();
              navigate("/");
            }}
          >
            <i className="bi bi-box-arrow-right"></i>
          </button>
  );
}
