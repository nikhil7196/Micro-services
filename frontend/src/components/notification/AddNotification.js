import { useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";
export default function AddNotification() {
    let [message, setMessage] = useState("");
    let [category, setCategory] = useState("");
    let [status, setStatus] = useState("");
    let [userId, setUserId] = useState("");

    async function submitHandler() {
        let data = {
            "userID": parseInt(userId),
            "message": message,
            "category": category,
            "status": status
        };

        try {
            let res = await axios.post(
                "http://localhost:9002/notification/insertnotificationdata",
                data,
                {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    }
                }
            );
            toast.success("Notification added");
        } catch (err) {
            toast.error(err.response?.data?.errorMessage || err.message);
        }
    }

    return (
        <div className="container mt-4">
            <div className="mb-3">
                <label className="form-label">User ID</label>
                <input
                    className="form-control"
                    type="number"
                    placeholder="Enter user ID"
                    onChange={(e) => setUserId(e.target.value)}
                />
            </div>
            <div className="mb-3">
                <label className="form-label">Message</label>
                <input
                    className="form-control"
                    type="text"
                    placeholder="Enter message"
                    onChange={(e) => setMessage(e.target.value)}
                />
            </div>
            <div className="mb-3">
                <label className="form-label">Category</label>
                <input
                    className="form-control"
                    type="text"
                    placeholder="Enter category"
                    onChange={(e) => setCategory(e.target.value)}
                />
            </div>
            <div className="mb-3">
                <label className="form-label">Status</label>
                <input
                    className="form-control"
                    type="text"
                    placeholder="Enter status"
                    onChange={(e) => setStatus(e.target.value)}
                />
            </div>

            <button
                className="btn btn-primary w-100"
                onClick={submitHandler}
                type="button"
            >
                Submit
            </button>
        </div>
    );
}