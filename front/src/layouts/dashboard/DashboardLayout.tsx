import OrganizationSidebar from '@/layouts/dashboard/OrganizationSidebar.tsx';
import { Outlet } from 'react-router';
import Header from '@/layouts/dashboard/Header.tsx';
import NormalSidebar from '@/layouts/dashboard/NormalSidebar.tsx';
import authStore from '@/stores/authStore.ts';

const DashboardLayout = () => {
    const { role } = authStore();
    return (
        <>
            <Header />
            {role === 'ORGANIZATION' && <OrganizationSidebar />}
            {role === 'NORMAL' && <NormalSidebar />}
            <Outlet />
        </>
    );
};

export default DashboardLayout;
