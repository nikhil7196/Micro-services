import { useState } from "react";
import axios from "axios";
import {toast} from 'react-toastify';

export default function FindInsuranceClaim() {

    const [claimId, setClaimId] = useState("");
    const [claim, setClaim] = useState(null);
    const [searched,setSearched]=useState(false);

    const buttonHandler = async () => {
        try {
            if (!claimId) {
                toast.warning("Please enter Insurance Claim ID");
                return;
            }

            setClaim(null);
            setSearched(false);
            const url = "http://localhost:9002/api/insurance/getInsuranceClaimById/" + claimId;
            const res = await axios.get(url,{
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token") 
                    }
                });

            setClaim(res.data.insuranceClaim);
            

        } catch (err) {
            if (err.response && err.response.status === 404) {
                setClaim(null);
            } else {
                toast.error(err.message);
            }
        }
        setSearched(true);
    };

    return (
        <div className="container mt-4">
            <h3 className="mb-4">Find Insurance Claim</h3>

            <div className="mb-3">
                <label className="form-label">
                    Claim ID
                    <span className="text-danger"> *</span>
                </label>
                <input
                    className="form-control"
                    type="number"
                    value={claimId}
                    placeholder="Enter Insurance Claim ID"
                    onChange={(e) => setClaimId(e.target.value)}
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
                disabled={!claimId}
            >
                Find
            </button>

            {searched && !claim && claimId && (
                <p className="mt-3 text-danger">No records found</p>
            )}

            {claim && (
                <div className="table-responsive">
                    <table className="table table-bordered table-hover table-striped mt-3">
                        <thead className="table-dark">
                            <tr>
                                <th>Claim Id</th>
                                <th>Patient Id</th>
                                <th>Patient Name</th>
                                <th>Policy Number</th>
                                <th>Amount</th>
                                <th>Status</th>
                            </tr>
                        </thead>

                        <tbody>
                            <tr>
                                <td>{claim.insuranceClaimId}</td>
                                <td>{claim.patient?.patientId}</td>
                                <td>{claim.patient?.patientName}</td>
                                <td>{claim.policyNumber}</td>
                                <td>₹ {Number(claim.amount).toFixed(2)}</td>
                                <td>{claim.status}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
}