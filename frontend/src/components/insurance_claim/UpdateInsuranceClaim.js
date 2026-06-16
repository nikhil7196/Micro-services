import axios from "axios";
import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

export default function UpdateInsuranceClaim() {

  const { claimId } = useParams();
  const navigate = useNavigate();

  const [patientId, setPatientId] = useState("");
  const [policyNumber, setPolicyNumber] = useState("");
  const [amount, setAmount] = useState("");
  const [status, setStatus] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const url = "http://localhost:9002/api/insurance/getInsuranceClaimById/" + claimId;

    axios.get(url, {
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })
    .then((res) => {
      const c = res.data.insuranceClaim;
      setPatientId(c.patient?.patientId || "");
      setPolicyNumber(c.policyNumber);
      setAmount(c.amount);
      setStatus(c.status);
    })
    .catch((err) => {
      toast.error(err.response?.data || err.message);
    });
  }, [claimId]);

  const handleSubmit = (e) => {
    e.preventDefault();

    if (loading) return;

    if (!patientId || !policyNumber || !amount || !status) {
      toast.warning("Please fill all required fields");
      return;
    }

    if (amount <= 0) {
      toast.warning("Amount must be greater than 0");
      return;
    }

    setLoading(true);

    const url = "http://localhost:9002/api/insurance/updateInsuranceClaim";

    const data = {
      insuranceClaim: {
        insuranceClaimId: claimId,
        patient: {
          patientId: patientId
        },
        policyNumber: policyNumber.trim(),
        amount: Number(amount),
        status: status
      }
    };

    axios.put(url, data, {
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })
    .then(() => {
      toast.success("Insurance Claim updated successfully");
      navigate("/insuranceClaim/display");
    })
    .catch((err) => {
      toast.error(err.response?.data || err.message);
      setLoading(false);
    });
  };

  return (
    <div className="container mt-4">
      <h3 className="mb-4">Update Insurance Claim</h3>

      <form onSubmit={handleSubmit}>

        <div className="mb-3">
          <label className="form-label">
            Patient ID <span className="text-danger">*</span>
          </label>
          <input
            className="form-control"
            type="number"
            value={patientId}
            onChange={(e) =>
              setPatientId(e.target.value === "" ? "" : Number(e.target.value))
            }
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">
            Policy Number <span className="text-danger">*</span>
          </label>
          <input
            className="form-control"
            type="text"
            value={policyNumber}
            onChange={(e) => setPolicyNumber(e.target.value)}
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">
            Claim Amount <span className="text-danger">*</span>
          </label>
          <input
            className="form-control"
            type="number"
            min="0"
            step="0.01"
            value={amount}
            onChange={(e) =>
              setAmount(e.target.value === "" ? "" : Number(e.target.value))
            }
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">
            Status <span className="text-danger">*</span>
          </label>
          <select
            className="form-select"
            value={status}
            onChange={(e) => setStatus(e.target.value)}
            required
          >
            <option value="">--Select--</option>
            <option value="SUBMITTED">SUBMITTED</option>
            <option value="APPROVED">APPROVED</option>
            <option value="REJECTED">REJECTED</option>
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
            "Update Insurance Claim"
          )}
        </button>

      </form>
    </div>
  );
}