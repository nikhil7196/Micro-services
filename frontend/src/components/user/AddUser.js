import { useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import { useNavigate } from "react-router";

export default function AddUser(){

    let [name,setName]=useState("");
    let [role,setRole]=useState("");
    let [phone,setPhone]=useState("");
    let [email,setEmail]=useState("");
    let [password,setPassword]=useState("");
    let [error,setError]=useState("");
    const navigate=useNavigate();

    const nameHandler=(event)=>{
        setName(event.target.value)
    }

    const roleHandler=(event)=>{
        setRole(event.target.value)
    }

    const emailHandler=(event)=>{
        setEmail(event.target.value)
    }

    const phoneHandler=(event)=>{
        setPhone(event.target.value)
    }
    const passwordHandler=(event)=>{
        setPassword(event.target.value);
    }

    async function submitHandler(){
        if(phone.length!==10){
            setError("Please enter correct phone number");
            return;
        }
        let data={
                
                    "userName": name,
                    "userRole": role,
                    "userEmail": email,
                    "phonenumber": phone,
                    "password":password
                
            }
             try {
            const res = await axios.post(
                "http://localhost:9002/user/insertuserdata",
                data,
                {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token") 
                    }
                }
            );
            toast.success("Successfully added");
            navigate("/user");
        }
        catch(err){
            toast.error(err.response.data.errorMessage);
        }
    }
    return(
        <div className="container mt-4">
            {error && (
                        <div className="alert alert-danger py-2 small border-danger-subtle" role="alert">
                            {error}
                        </div>
                    )}
            <div className="mb-3">
                <label className="form-label">Name</label>
                <input className="form-control" type="text" placeholder="Enter name" onChange={nameHandler} />
            </div>

            <div className="mb-3">
                <label className="form-label">Role</label>
                <select className="form-select" onChange={roleHandler}>
                    <option value="">--Select Role--</option>
                    <option value="RECEPTIONIST">Receptionist</option>
                    <option value="DOCTOR">Doctor</option>
                    <option value="NURSE">Nurse</option>
                    <option value="FINANCEOFFICER">Finance Officer</option>
                    <option value="COMPLIANCE_OFFICER">Compilance officer</option>
                    
                </select>
            </div>
            <div className="mb-3">
                <label className="form-label">Email</label>
                <input className="form-control" type="email" placeholder="Enter email" onChange={emailHandler} />
            </div>
            <div className="mb-3">
                <label className="form-label">Phone</label>
                <input className="form-control" type="text" placeholder="Enter phone number" onChange={phoneHandler} />
            </div>
            <div className="mb-3">
                <label className="form-label">Password</label>
                <input className="form-control" type="text" placeholder="Enter Password (min 6 Chars)" onChange={passwordHandler} />
            </div>

                <button class="btn btn-primary w-100" onClick={submitHandler} type="submit">Submit</button>
           
        </div>
    )
}