import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import { toast } from "react-toastify";
export default function DeleteUser(){
    const {id}=useParams();
    const navigate=useNavigate();

    const [data,setData]=useState(" ");
    async function submitHandler(){
        let url=`http://localhost:9002/user/deleteuser/${id}`;
        try{
            let res=await axios.delete(url,{
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token") 
                    }
                });
            setData(res.data);
            toast.success("Successfully deleted");
            navigate("/user/findall");
        }catch(err){
            toast.error(err.response.data.errorMessage)
        }
    }
    useEffect(()=>{
        submitHandler();
        
    },[])
    return(
        <div>
            
                <div>
                    <p>{data}</p>
                </div>
            
        </div>
    )
}