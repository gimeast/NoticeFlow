import OrganizationSidebar from '@/layouts/dashboard/OrganizationSidebar.tsx';
import { Outlet } from 'react-router';
import Header from '@/layouts/dashboard/Header.tsx';
import NormalSidebar from '@/layouts/dashboard/NormalSidebar.tsx';
import authStore from '@/stores/authStore.ts';
import style from './DashboardLayout.module.scss';

const DashboardLayout = () => {
    const { role } = authStore();
    return (
        <div className={style.layout}>
            <Header />
            {role === 'ORGANIZATION' && <OrganizationSidebar />}
            {role === 'NORMAL' && <NormalSidebar />}
            <main>
                <Outlet />
            </main>
        </div>
    );
};

export default DashboardLayout;
