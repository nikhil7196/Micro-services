import axios from "axios";
import { useState ,useEffect} from "react";
import { useParams } from "react-router";
import { toast } from "react-toastify";
export default function DeleteNotification(){
    const {id}=useParams();

    const [data,setData]=useState("");

    async function submitHandler(){
        let url=`http://localhost:9002/notification/deletenotification/${id}`;
        try{

            let res=await axios.delete(url,{
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token") 
                    }
                });
            setData(res.data);
            toast.success("Successfully deleted");

        }catch(err){
            toast.error(err.response.data.errorMessage)
        }
    };

    useEffect(()=>{
        submitHandler();
    },[])
    
    return(
        <div>
            <p>{data}</p>
        </div>
    )
}