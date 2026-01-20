import { createBrowserRouter, RouterProvider } from 'react-router';
import Landing from './pages/Landing.tsx';
import LandingLayout from './layouts/landing/LandingLayout.tsx';
import Login from '@/pages/login/Login.tsx';
import JoinType from '@/pages/join/JoinType.tsx';
import JoinLayout from '@/layouts/join/JoinLayout.tsx';
import OrganJoin from '@/pages/join/organ/OrganJoin.tsx';
import OAuth2RedirectHandler from '@/pages/oauth/OAuth2RedirectHandler.tsx';
import ProtectedRoute from '@/components/auth/ProtectedRoute.tsx';
import DashboardLayout from '@/layouts/dashboard/DashboardLayout.tsx';
import Dashboard from '@/pages/organization/dashboard/Dashboard.tsx';
import { dashboardStatusData } from '@/api/dashboard/dashboardApi.ts';
import Notices from '@/pages/organization/dashboard/notices/Notices.tsx';
import { getNoticesWithCategories } from '@/api/notices/noticesApi.ts';

function App() {
    const router = createBrowserRouter([
        {
            element: <LandingLayout />,
            children: [{ path: '/', Component: Landing }],
        },
        {
            path: '/login',
            element: <Login />,
        },
        {
            path: '/oauth2/redirect',
            element: <OAuth2RedirectHandler />,
        },
        {
            element: <ProtectedRoute allowedRoles={['ANONYMOUS']} fallbackPath='/' />,
            children: [
                {
                    element: <JoinLayout />,
                    children: [
                        {
                            path: '/join',
                            element: <JoinType />,
                        },
                        {
                            path: '/join/organ',
                            element: <OrganJoin />,
                        },
                    ],
                },
            ],
        },
        {
            path: '/dashboard',
            element: <ProtectedRoute allowedRoles={['ORGANIZATION', 'NORMAL']} fallbackPath='/' />,
            children: [
                {
                    element: <DashboardLayout />,
                    children: [
                        {
                            index: true,
                            element: <Dashboard />,
                            loader: dashboardStatusData,
                        },
                        {
                            path: 'notices',
                            element: <Notices />,
                            loader: getNoticesWithCategories,
                        },
                    ],
                },
            ],
        },
    ]);
    return <RouterProvider router={router} />;
}

export default App;
