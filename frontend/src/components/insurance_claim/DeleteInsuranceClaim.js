import axios from "axios";
import { useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {toast} from 'react-toastify';

export default function DeleteInsuranceClaim() {

    const { claimId } = useParams();
    const navigate = useNavigate();

    useEffect(() => {


        if (!window.confirm("Are you sure you want to delete this insurance claim?")) {
                navigate("/insuranceClaim");
                return;
        }

        
        const url = "http://localhost:9002/api/insurance/deleteInsuranceClaim/" + claimId;

        axios.delete(url,{
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token") 
                    }
                })
            .then((res) => {
                toast.success("Insurance Claim deleted successfully");
                navigate("/insuranceClaim/display");
            })
            .catch((err) => {
                toast.error(err.message);
            });

    }, [claimId, navigate]); 

    return (
        <div>
            <h3>Deleting Insurance Claim...</h3>
        </div>
    );
}