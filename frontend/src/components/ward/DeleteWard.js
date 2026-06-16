import { useParams,useNavigate } from "react-router";
import axios from 'axios';
import { useEffect } from "react";

export default function DeleteWard() {
    let { wardId } = useParams();
      let navigate = useNavigate();

    useEffect(() => {
        let url = `http://localhost:9002/api/ward/deleteWard/${wardId}`;

        axios.delete(url,{
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token") 
                    }
                })
            .then((response) => {
                alert(response.data);
                 navigate("/ward/findAll");
            })
            .catch((error) => {
                if (error.response) {
                    alert("Error " + error.response.status + ": " + (error.response.data?.errorMessage || JSON.stringify(error.response.data)));
                } else if (error.request) {
                    alert("No response from server. Make sure the backend is running on port 9002.");
                } else {
                    alert("Error: " + error.message);
                }
            });
    }, [wardId]);

    return (
        <div>
            <h1>Delete Ward</h1>
            <span>Deleting Ward ID: {wardId}</span>
        </div>
    );
}
