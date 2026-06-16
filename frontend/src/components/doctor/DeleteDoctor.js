import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
import { toast } from "react-toastify";

export default function DeleteDoctor() {

  const { id } = useParams();
  const navigate = useNavigate();

  const [doctorName, setDoctorName] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    axios.get(`http://localhost:9002/api/doctor/get/${id}`, {
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    })
    .then((res) => {
      setDoctorName(res.data.name);
    })
    .catch(() => {
      setDoctorName("Doctor not found");
    });
  }, [id]);

  const deleteHandler = async () => {
    if (loading) return;

    setLoading(true);

    try {
      await axios.delete(
        `http://localhost:9002/api/doctor/delete/${id}`,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        }
      );

      toast.success("Doctor deleted successfully");
      navigate("/doctor/display");

    } catch (err) {
      toast.error(err.response?.data || err.message);
      setLoading(false);
    }
  };

  return (
    <div className="container d-flex justify-content-center align-items-center" style={{ height: "70vh" }}>
      
      <div className="card shadow-sm p-4 text-center" style={{ width: "350px" }}>
        
        <h5 className="mb-3">Delete Doctor</h5>

        <p className="text-muted">
          Are you sure you want to delete this doctor?
        </p>

        <h4 className="text-danger">{doctorName}</h4>

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
            onClick={() => navigate("/doctor/display")}
            disabled={loading}
          >
            Cancel
          </button>

        </div>
      </div>

    </div>
  );
}






