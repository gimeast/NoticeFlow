import { BrowserRouter, Route, Routes } from 'react-router';
import Landing from './pages/Landing.tsx';
import LandingLayout from './layouts/LandingLayout.tsx';
import Login from '@/pages/login/Login.tsx';
import JoinType from '@/pages/join/JoinType.tsx';
import JoinLayout from '@/layouts/JoinLayout.tsx';
import OrganJoin from '@/pages/join/organ/OrganJoin.tsx';
import OAuth2RedirectHandler from '@/pages/oauth/OAuth2RedirectHandler.tsx';
import ProtectedRoute from '@/components/auth/ProtectedRoute.tsx';
import DashboardLayout from '@/layouts/DashboardLayout.tsx';
import Dashboard from '@/pages/dashboard/Dashboard.tsx';

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route element={<LandingLayout />}>
                    <Route path='/' element={<Landing />} />
                </Route>
                <Route path='/login' element={<Login />} />
                <Route path='/oauth2/redirect' element={<OAuth2RedirectHandler />} />
                <Route element={<ProtectedRoute allowedRoles={['ANONYMOUS']} fallbackPath='/' />}>
                    <Route element={<JoinLayout />}>
                        <Route path='/join' element={<JoinType />} />
                        <Route path='/join/organ' element={<OrganJoin />} />
                    </Route>
                </Route>
                <Route element={<ProtectedRoute allowedRoles={['ORGANIZATION', 'NORMAL']} fallbackPath='/' />}>
                    <Route element={<DashboardLayout />}>
                        <Route path='/dashboard' element={<Dashboard />} />
                    </Route>
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
