import axios from "axios";
import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

export default function UpdateInvoice() {

    const { iid } = useParams();
    const navigate = useNavigate();

    const [patientId,     setPatientId]     = useState("");
    const [amount,        setAmount]        = useState("");
    const [invoiceDate,   setInvoiceDate]   = useState("");
    const [paymentStatus, setPaymentStatus] = useState("");
    const [loading,       setLoading]       = useState(false);

    useEffect(() => {
        axios.get(`http://localhost:9002/api/invoice/getInvoiceById/${iid}`, {
            headers: { Authorization: "Bearer " + localStorage.getItem("token") }
        })
        .then((res) => {
            const inv = res.data.invoice;
            setPatientId(inv.patient?.patientId || "");
            setAmount(inv.amount);
            setInvoiceDate(inv.invoiceDate);
            setPaymentStatus(inv.paymentStatus);
        })
        .catch((err) => {
            toast.error(err.response?.data?.message || err.message);
        });
    }, [iid]);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (loading) return;

        if (amount <= 0) {
            toast.warning("Amount must be greater than 0");
            return;
        }

        setLoading(true);

        const data = {
            invoice: {
                invoiceId: Number(iid),
                patient: { patientId: Number(patientId) },
                amount: Number(amount),
                invoiceDate,
                paymentStatus
            }
        };

        axios.put("http://localhost:9002/api/invoice/updateInvoice", data, {
            headers: { Authorization: "Bearer " + localStorage.getItem("token") }
        })
        .then(() => {
            toast.success("Invoice updated successfully");
            navigate("/invoice/display");
        })
        .catch((err) => {
            toast.error(err.response?.data?.message || err.message);
            setLoading(false);
        });
    };

    return (
        <div className="container mt-4">
            <h3 className="mb-4">Update Invoice</h3>

            <form onSubmit={handleSubmit}>

                <div className="mb-3">
                    <label className="form-label">
                        Patient ID <span className="text-danger">*</span>
                    </label>
                    <input
                        className="form-control"
                        type="number"
                        value={patientId}
                        onChange={(e) => setPatientId(e.target.value === "" ? "" : Number(e.target.value))}
                        required
                    />
                </div>

                <div className="mb-3">
                    <label className="form-label">
                        Amount <span className="text-danger">*</span>
                    </label>
                    <input
                        className="form-control"
                        type="number"
                        min="0.01"
                        step="0.01"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value === "" ? "" : Number(e.target.value))}
                        required
                    />
                </div>

                <div className="mb-3">
                    <label className="form-label">Invoice Date</label>
                    <input
                        className="form-control"
                        type="date"
                        value={invoiceDate}
                        onChange={(e) => setInvoiceDate(e.target.value)}
                        max={new Date().toISOString().split("T")[0]}
                        required
                    />
                </div>

                <div className="mb-3">
                    <label className="form-label">
                        Payment Status <span className="text-danger">*</span>
                    </label>
                    <select
                        className="form-select"
                        value={paymentStatus}
                        onChange={(e) => setPaymentStatus(e.target.value)}
                        required
                    >
                        <option value="">-- Select --</option>
                        <option value="PAID">PAID</option>
                        <option value="PENDING">PENDING</option>
                    </select>
                </div>

                <button
                    className="btn btn-warning w-100"
                    type="submit"
                    disabled={loading}
                >
                    {loading ? (
                        <><span className="spinner-border spinner-border-sm me-2"></span>Updating...</>
                    ) : "Update Invoice"}
                </button>

            </form>
        </div>
    );
}