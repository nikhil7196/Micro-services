import axios from 'axios';
import { useState } from 'react';
import { toast } from "react-toastify";

export default function AddPatient(){

    const [patientName,setPatientName]=useState("");
    const [patientDOB,setPatientDOB]=useState("");
    const [patientGender,setPatientGender]=useState("");
    const [patientPN,setPatientPN]=useState("");
    const [patientMH,setPatientMH]=useState("");
    const [patientStatus,setPatientStatus]=useState("");
    const [loading, setLoading] = useState(false);

    const patientNameHandler=(event)=>{
        setPatientName(event.target.value);
    }

    const patientDOBHandler=(event)=>{
        setPatientDOB(event.target.value);
    }

    const patientGenderHandler=(event)=>{
        setPatientGender(event.target.value);
    }

    const patientPNHandler=(event)=>{
        setPatientPN(event.target.value);
    }

    const patientMHHandler=(event)=>{
        setPatientMH(event.target.value);
    }

    const patientStatusHandler=(event)=>{
        setPatientStatus(event.target.value);
    }

    const buttonHandler=()=>{
        if(loading) return;
        setLoading(true);
        let url="http://localhost:9002/api/patient/addPatient";

        let data={
            "patient":{
                "patientName": patientName.trim(),
                "patientDOB": patientDOB,
                "patientGender": patientGender,
                "patientPhoneNumber": patientPN,
                "patientMedicalHistory": patientMH.trim(),
                "patientStatus": patientStatus
            }
        };

        axios.post(url,data,{
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    }
                })
            .then((response)=>{
                toast.success("Patient Added successfully");
                setPatientName("");
                setPatientDOB("");
                setPatientGender("");
                setPatientPN("");
                setPatientMH("");
                setPatientStatus("");
                setLoading(false);
            })
            .catch((error)=>{
                toast.error(error.response?.data || error.message);
                setLoading(false);
            })
    }

    return(
        <div className='container mt-4'>

            <h3 className="mb-4">Add Patient</h3>
            
            <form onSubmit={(e) => {
                e.preventDefault();
                buttonHandler();
                }}
            >

            <div className='mb-3'>
                <label className='form-label'>
                    Patient Name 
                    <span style={{color: "red"}}> *</span>
                </label>
                <input 
                    className='form-control' 
                    type='text'
                    value={patientName}
                    placeholder="Enter patient name" 
                    onChange={patientNameHandler}
                    pattern="[A-Za-z\s]+"
                    title="Only letters allowed"
                    required
                />
            </div>

            <div className='mb-3'>
                <label className='form-label'>
                    Patient DOB
                    <span style={{color: "red"}}> *</span>
                </label>
                <input 
                    className='form-control' 
                    type='date'
                    value={patientDOB} 
                    onChange={patientDOBHandler}
                    max={new Date().toISOString().split("T")[0]}
                    required
                />
            </div>

            <div className="mb-3">
                <label className="form-label">
                    Patient Gender
                    <span style={{color: "red"}}> *</span>
                </label>

                <div className="form-check">
                    <input
                        className="form-check-input"
                        type="radio"
                        name="gender"
                        value="Male"
                        checked={patientGender === "Male"}
                        onChange={patientGenderHandler}
                        required

                    />
                    <label className="form-check-label">Male</label>
                </div>

                <div className="form-check">
                    <input
                        className="form-check-input"
                        type="radio"
                        name="gender"
                        value="Female"
                        checked={patientGender === "Female"}
                        onChange={patientGenderHandler}
                    />
                    <label className="form-check-label">Female</label>
                </div>

                <div className="form-check">
                    <input
                        className="form-check-input"
                        type="radio"
                        name="gender"
                        value="Other"
                        checked={patientGender === "Other"}
                        onChange={patientGenderHandler}
                    />
                    <label className="form-check-label">Other</label>
                </div>
            </div>

            <div className='mb-3'>
                <label className='form-label'>
                    Patient PhoneNumber
                    <span style={{color: "red"}}> *</span>
                </label>
                <input 
                    className='form-control' 
                    type='tel'
                    value={patientPN}
                    placeholder="Enter patient phone number" 
                    onChange={patientPNHandler}                     
                    pattern="[6-9][0-9]{9}"
                    title="Phone number must start with 6, 7, 8, or 9 and be 10 digits"
                    required
                />
            </div>

            <div className='mb-3'>
                <label className='form-label'>
                    Patient MedicalHistory
                    <span style={{color: "red"}}> *</span>
                </label>
                <textarea 
                    className='form-control'
                    value={patientMH}
                    placeholder="Enter patient medical history" 
                    onChange={patientMHHandler} 
                    required 
                />
            </div>

            <div className='mb-3'>
                <label className='form-label'>
                    Patient Status
                    <span style={{color: "red"}}> *</span>
                </label>

                <select
                    className='form-select'
                    value={patientStatus}
                    onChange={patientStatusHandler}
                    required
                >
                    <option value="">--Select Status--</option>                 
                    <option value="REGISTERED">REGISTERED</option>
                    <option value="ADMITTED">ADMITTED</option>
                    <option value="DISCHARGED">DISCHARGED</option>
                    <option value="OUTPATIENT">OUTPATIENT</option>

                </select>
            </div>


            <button className='btn btn-primary w-100' type='submit' 
            disabled={loading}>{loading ? (
                        <>
                            <span className="spinner-border spinner-border-sm me-2"></span>
                            Submitting...
                        </>
                    ) : (
                        "Add Patient"
                    )}</button>
            </form>
        </div>
    )
} 