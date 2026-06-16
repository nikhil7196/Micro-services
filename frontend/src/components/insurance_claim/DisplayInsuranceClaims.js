import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {toast} from 'react-toastify';

export default function DisplayInsuranceClaims() {

    const [claims, setClaims] = useState([]);

    useEffect(() => {
        const url = "http://localhost:9002/api/insurance/fetchAllInsuranceClaims";

        axios.get(url,{
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token") 
                    }
                })
            .then((res) => {
                setClaims(res.data);
            })
            .catch((err) => {
                toast.error(err.message);
            });
    }, []);

    return (
        <div className="container mt-4">
            <h3 className="mb-4">Display All Insurance Claims</h3>
            {claims.length ===0 ?(<p>No insurance claims found</p>):
            (<div className="table-responsive">
            <table className="table table-bordered table-hover table-striped mt-3">
                <thead className="table-dark">
                    <tr>
                        <th>Claim Id</th>
                        <th>Patient Id</th>
                        <th>Patient Name</th>
                        <th>Policy Number</th>
                        <th>Amount</th>
                        <th>Status</th>
                        <th>Update</th>
                        <th>Delete</th>
                    </tr>
                </thead>

                <tbody>
                    {claims.map((c) => (
                        <tr key={c.insuranceClaimId}>
                            <td>{c.insuranceClaimId}</td>

                            <td>{c.patient?.patientId}</td>
                            <td>{c.patient?.patientName}</td>

                            <td>{c.policyNumber}</td>
                            <td>₹ {Number(c.amount).toFixed(2)}</td>
                            <td>{c.status}</td>

                            <td className="text-center">
                                <Link className="btn btn-warning btn-sm" to={`/insuranceClaim/update/${c.insuranceClaimId}`}>
                                    Update
                                </Link>
                            </td>

                            <td className="text-center">
                                <Link className="btn btn-danger btn-sm" to={`/insuranceClaim/delete/${c.insuranceClaimId}`}>
                                    Delete
                                </Link>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            </div>
            )}
        </div>
    );
}