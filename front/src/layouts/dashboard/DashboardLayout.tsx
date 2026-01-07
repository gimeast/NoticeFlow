import OrganizationSidebar from '@/components/dashboard/sidebar/OrganizationSidebar.tsx';
import { Outlet } from 'react-router';
import Header from '@/components/dashboard/header/Header.tsx';
import NormalSidebar from '@/components/dashboard/sidebar/NormalSidebar.tsx';
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
