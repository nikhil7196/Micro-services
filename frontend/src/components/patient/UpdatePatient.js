import axios from "axios";
import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

export default function UpdatePatient() {

  const { pid } = useParams();
  const navigate = useNavigate();

  const [patientName, setPatientName] = useState("");
  const [patientDOB, setPatientDOB] = useState("");
  const [patientGender, setPatientGender] = useState("");
  const [patientPN, setPatientPN] = useState("");
  const [patientMH, setPatientMH] = useState("");
  const [patientStatus, setPatientStatus] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const url = "http://localhost:9002/api/patient/getPatientById/" + pid;

    axios.get(url, {
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })
    .then((res) => {
      const p = res.data.patient;
      setPatientName(p.patientName);
      setPatientDOB(p.patientDOB);
      setPatientGender(p.patientGender);
      setPatientPN(p.patientPhoneNumber);
      setPatientMH(p.patientMedicalHistory);
      setPatientStatus(p.patientStatus);
    })
    .catch((err) => {
      toast.error(err.response?.data || err.message);
    });
  }, [pid]);

  const handleSubmit = (e) => {
    e.preventDefault();

    if (loading) return;
    setLoading(true);

    const url = "http://localhost:9002/api/patient/updatePatient";

    const data = {
      patient: {
        patientId: pid,
        patientName: patientName.trim(),
        patientDOB,
        patientGender,
        patientPhoneNumber: patientPN,
        patientMedicalHistory: patientMH.trim(),
        patientStatus
      }
    };

    axios.put(url, data, {
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })
    .then(() => {
      toast.success("Patient updated successfully");
      navigate("/patient/display");
    })
    .catch((err) => {
      toast.error(err.response?.data || err.message);
      setLoading(false);
    });
  };

  return (
    <div className="container mt-4">
      <h3 className="mb-4">Update Patient</h3>

      <form onSubmit={handleSubmit}>

        <div className="mb-3">
          <label className="form-label">
            Patient Name <span className="text-danger">*</span>
          </label>
          <input
            className="form-control"
            type="text"
            value={patientName}
            onChange={(e) => setPatientName(e.target.value)}
            pattern="[A-Za-z\s]+"
            title="Only letters allowed"
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">
            Patient DOB <span className="text-danger">*</span>
          </label>
          <input
            className="form-control"
            type="date"
            value={patientDOB}
            onChange={(e) => setPatientDOB(e.target.value)}
            max={new Date().toISOString().split("T")[0]}
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">
            Patient Gender <span className="text-danger">*</span>
          </label>

          <div className="form-check">
            <input
              className="form-check-input"
              type="radio"
              name="gender"
              value="Male"
              checked={patientGender === "Male"}
              onChange={(e) => setPatientGender(e.target.value)}
              required
            />
            <label className="form-check-label">Male</label>
          </div>

          <div className="form-check">
            <input
              className="form-check-input"
              type="radio"
              name="gender"
              value="Female"
              checked={patientGender === "Female"}
              onChange={(e) => setPatientGender(e.target.value)}
            />
            <label className="form-check-label">Female</label>
          </div>

          <div className="form-check">
            <input
              className="form-check-input"
              type="radio"
              name="gender"
              value="Other"
              checked={patientGender === "Other"}
              onChange={(e) => setPatientGender(e.target.value)}
            />
            <label className="form-check-label">Other</label>
          </div>
        </div>

        <div className="mb-3">
          <label className="form-label">
            Patient Phone Number <span className="text-danger">*</span>
          </label>
          <input
            className="form-control"
            type="tel"
            value={patientPN}
            onChange={(e) => setPatientPN(e.target.value)}
            pattern="[6-9][0-9]{9}"
            title="Phone number must start with 6, 7, 8, or 9 and be 10 digits"
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">
            Patient Medical History <span className="text-danger">*</span>
          </label>
          <textarea
            className="form-control"
            value={patientMH}
            onChange={(e) => setPatientMH(e.target.value)}
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">
            Patient Status <span className="text-danger">*</span>
          </label>
          <select
            className="form-select"
            value={patientStatus}
            onChange={(e) => setPatientStatus(e.target.value)}
            required
          >
            <option value="">--Select Status--</option>
            <option value="Admitted">Admitted</option>
            <option value="Discharged">Discharged</option>
            <option value="Under Treatment">Under Treatment</option>
            <option value="Recovered">Recovered</option>
          </select>
        </div>

        <button
          className="btn btn-warning w-100"
          type="submit"
          disabled={loading}
        >
          {loading ? (
                        <>
                            <span className="spinner-border spinner-border-sm me-2"></span>
                            Updating...
                        </>
                    ) : (
                        "Update Patient"
                    )}
        </button>

      </form>
    </div>
  );
}