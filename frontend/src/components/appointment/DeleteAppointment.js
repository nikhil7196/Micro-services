import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
import { toast } from "react-toastify";

export default function DeleteAppointment() {
  const { aid } = useParams();
  const navigate = useNavigate();

  const [appointmentDetails, setAppointmentDetails] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    axios.get(`http://localhost:9002/api/appointment/get/${aid}`, {
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    })
    .then((res) => {
      const appt = res.data.appointment;
      if (appt) {
        const details = `${appt.date} at ${appt.time?.slice(0, 5)} with ${appt.doctor?.name}`;
        setAppointmentDetails(details);
      } else {
        setAppointmentDetails("Appointment details not available");
      }
    })
    .catch(() => {
      setAppointmentDetails("Appointment not found");
    });
  }, [aid]);

  const deleteHandler = async () => {
    if (loading) return;
    setLoading(true);

    try {
      await axios.delete(`http://localhost:9002/api/appointment/delete/${aid}`, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      });

      toast.success("Appointment deleted successfully");
      navigate("/appointment/display");

    } catch (err) {
      toast.error(err.response?.data?.message || err.message);
      setLoading(false);
    }
  };

  return (
    <div className="container d-flex justify-content-center align-items-center" style={{ height: "70vh" }}>
      <div className="card shadow-sm p-4 text-center" style={{ width: "350px" }}>
        
        <h5 className="mb-3">Delete Appointment</h5>

        <p className="text-muted">
          Are you sure you want to delete this appointment?
        </p>

        <h4 className="text-danger">{appointmentDetails}</h4>

        <div className="d-flex gap-2 mt-4">
          <button
            className="btn btn-danger w-50"
            onClick={deleteHandler}
            disabled={loading}
          >
            {loading ? "Deleting..." : "Delete"}
          </button>

          <button
            className="btn btn-outline-secondary w-50"
            onClick={() => navigate("/appointment/display")}
            disabled={loading}
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
}




