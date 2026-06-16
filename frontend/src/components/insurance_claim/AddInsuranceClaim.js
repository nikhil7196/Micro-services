import axios from "axios";
import { useState } from "react";
import { toast } from "react-toastify";

export default function AddInsuranceClaim() {

    const [patientId, setPatientId] = useState("");
    const [policyNumber, setPolicyNumber] = useState("");
    const [amount, setAmount] = useState("");
    const [status, setStatus] = useState("");
    const [loading, setLoading] = useState(false);

    const handleSubmit = (e) => {
        e.preventDefault();

        if (loading) return;
        setLoading(true);

        const url = "http://localhost:9002/api/insurance/addInsuranceClaim";

        // ✅ Extra validation
        if (amount <= 0) {
            toast.warning("Amount must be greater than 0");
            setLoading(false);
            return;
        }

        const data = {
            insuranceClaim: {
                patient: {
                    patientId: patientId
                },
                policyNumber: policyNumber.trim(),
                amount: Number(amount),
                status: status
            }
        };

        axios.post(url, data, {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token")
            }
        })
        .then(() => {
            toast.success("Insurance Claim added successfully");

            setPatientId("");
            setPolicyNumber("");
            setAmount("");
            setStatus("");
            setLoading(false);
        })
        .catch((err) => {
            toast.error(err.response?.data || err.message);
            setLoading(false);
        });
    };

    return (
        <div className="container mt-4">
            <h3 className="mb-4">Add Insurance Claim</h3>

            <form onSubmit={handleSubmit}>

                <div className="mb-3">
                    <label className="form-label">
                        Patient ID <span className="text-danger">*</span>
                    </label>
                    <input
                        className="form-control"
                        type="number"
                        value={patientId}
                        placeholder="Enter patient ID"
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
                        placeholder="Enter policy number"
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
                        placeholder="Enter claim amount"
                        onChange={(e) =>
                            setAmount(e.target.value === "" ? "" : Number(e.target.value))
                        }
                        required
                    />
                </div>

                <div className="mb-3">
                    <label className="form-label">
                        Status <span className="text-danger"> *</span>
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
                    className="btn btn-primary w-100"
                    type="submit"
                    disabled={loading}
                >
                    {loading ? (
                        <>
                            <span className="spinner-border spinner-border-sm me-2"></span>
                            Submitting...
                        </>
                    ) : (
                        "Add Insurance Claim"
                    )}
                </button>

            </form>
        </div>
    );
}