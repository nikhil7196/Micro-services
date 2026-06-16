import axios from 'axios';
import { useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { toast } from "react-toastify";

export default function DeletePatient(){
    
    let {pid}=useParams();
    let navigate=useNavigate();
    useEffect(()=>{
        
        
        if (!window.confirm("Are you sure you want to delete this patient?")) {
                navigate("/patient");
                return;
        }

        let url="http://localhost:9002/api/patient/deletePatient/"+pid;
        axios.delete(url,{
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    }
                })
        .then((res)=>{
            toast.success("Deleted successfully");
            navigate("/patient/display");

        })
        .catch((err)=>{
            toast.error(err.message);
        })
    },[pid,navigate])

    return(
        <div>
            <h3>Deleting Patient...</h3>
        </div>
    )
}
