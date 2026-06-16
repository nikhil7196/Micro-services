import { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";

export default function FindAllUser() {

    const [data, setData] = useState([]);

    async function fetchUsers() {
        const url = "http://localhost:9002/user/fetchallusers";

        try {
            const res = await axios.get(url, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            });

            setData(res.data);
        } catch (err) {
            toast.error(err.message);
        }
    }

    useEffect(() => {
        fetchUsers();
    }, []);

    return (
        <div className="container mt-4">

            <h3 className="mb-4">Display All Users</h3>

            {data.length === 0 ? (
                <p>No users found</p>
            ) : (
                <div className="table-responsive">
                    <table className="table table-bordered table-hover table-striped mt-3">
                        <thead className="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Role</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Status</th>
                                <th>Update</th>
                                <th>Delete</th>
                            </tr>
                        </thead>

                        <tbody>
                            {data.map((m) => (
                                <tr key={m.userId}>
                                    <td>{m.userId}</td>
                                    <td>{m.userName}</td>
                                    <td>{m.role}</td>
                                    <td>{m.email}</td>
                                    <td>{m.phoneNumber}</td>
                                    <td>{m.status}</td>
                                    <td className="text-center">
                                        <Link
                                            className="btn btn-warning btn-sm"
                                            to={`/user/update/${m.userId}`}
                                        >
                                            Update
                                        </Link>
                                    </td>

                                    <td className="text-center">
                                        <Link
                                            className="btn btn-danger btn-sm"
                                            to={`/user/delete/${m.userId}`}
                                        >
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