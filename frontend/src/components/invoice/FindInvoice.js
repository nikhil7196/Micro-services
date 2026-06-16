import { useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";

export default function FindInvoice() {

    const [invoiceId, setInvoiceId] = useState("");
    const [invoice, setInvoice] = useState(null);
    const [searched,setSearched]=useState(false);

    const buttonHandler = async () => {
        try {
            if (!invoiceId) {
                toast.warning("Please enter Invoice ID");
                return;
            }


            setInvoice(null);
            setSearched(false);

            

            const url = "http://localhost:9002/api/invoice/getInvoiceById/" + invoiceId;
            const res = await axios.get(url,{
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token") 
                    }
                });


            setInvoice(res.data.invoice);

        } catch (err) {
            if (err.response && err.response.status === 404) {
                setInvoice(null);
            } else {
                toast.error(err.message);
            }
        }
        setSearched(true);
    };

    return (
        <div className="container mt-4">
            <h3 className="mb-4">Find Invoice by ID</h3>

            <div className="mb-3">
                <label className="form-label">
                    Invoice ID
                    <span className="text-danger"> *</span>
                </label>
                <input
                    className="form-control"
                    type="number"
                    value={invoiceId}
                    placeholder="Enter Invoice ID"
                    onChange={(e) => setInvoiceId(e.target.value)}
                    onKeyDown={(e) => {
                            if (e.key === "Enter") {
                                buttonHandler();
                            }
                    }}
                />
            </div>

            <button
                className="btn btn-primary w-100"
                onClick={buttonHandler}
                disabled={!invoiceId}
            >
                Find
            </button>

            {searched && !invoice && invoiceId && (
                <p className="mt-3 text-danger">No records found</p>
            )}

            {invoice && (
                <div className="table-responsive">
                    <table className="table table-bordered table-hover table-striped mt-3">
                        <thead className="table-dark">
                            <tr>
                                <th>Invoice Id</th>
                                <th>Patient Id</th>
                                <th>Patient Name</th>
                                <th>Amount</th>
                                <th>Invoice Date</th>
                                <th>Payment Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>{invoice.invoiceId}</td>
                                <td>{invoice.patient?.patientId}</td>
                                <td>{invoice.patient?.patientName}</td>
                                <td>₹ {Number(invoice.amount).toFixed(2)}</td>
                                <td>{new Date(invoice.invoiceDate).toLocaleDateString()}</td>
                                <td>{invoice.paymentStatus}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
}