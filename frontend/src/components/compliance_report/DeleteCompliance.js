import axios from 'axios';
import { useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { toast } from "react-toastify";

export default function DeleteCompliance() {

    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {

        const deleteReport = async () => {
            try {
                const url = `http://localhost:9002/api/compliance-reports/deleteComplianceReport/${id}`;

                await axios.delete(url, {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem("token")}`
                    }
                });

                
                toast.success("Compliance Report deleted successfully");

                setTimeout(() => {
                    navigate("/compliance_report/display");
                }, 1000);

            } catch (error) {
                console.error(error);

                
                toast.error(
                    error.response?.data?.message || "Error deleting report"
                );
            }
        };

        deleteReport();

    }, [id, navigate]);

    return (
        <div className="container mt-4">
            <h3>Deleting Compliance Report...</h3>
        </div>
    );
}