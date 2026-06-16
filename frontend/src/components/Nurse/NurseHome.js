import { Outlet } from 'react-router-dom';
import TopNavbar from '../common/TopNavbar';

export default function NurseHome() {
    return (
        <div style={{ minHeight: "100vh", backgroundColor: "#f8f9fa" }}>
            <TopNavbar />
            <div className="container-fluid px-4 py-4">
                <Outlet />
            </div>
        </div>
    );
}
