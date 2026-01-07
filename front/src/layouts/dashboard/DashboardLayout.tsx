import OrganizationSidebar from '@/layouts/dashboard/OrganizationSidebar.tsx';
import { Outlet } from 'react-router';
import Header from '@/layouts/dashboard/Header.tsx';
import NormalSidebar from '@/layouts/dashboard/NormalSidebar.tsx';

const DashboardLayout = () => {
    return (
        <>
            <Header />
            <OrganizationSidebar />
            <NormalSidebar />
            <Outlet />
        </>
    );
};

export default DashboardLayout;
