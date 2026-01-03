import authStore from '@/stores/authStore.ts';
import { USER } from '@/api/auth.ts';
import { useEffect, useState } from 'react';
import { Navigate, Outlet } from 'react-router';
import type { RoleType } from '@/types/authTypes.ts';

interface ProtectedRouteProps {
    allowedRole: RoleType;
    fallbackPath: string;
}

const ProtectedRoute = ({ allowedRole, fallbackPath }: ProtectedRouteProps) => {
    const { isAuth, role, login } = authStore();
    const [isLoading, setIsLoading] = useState(!isAuth);

    useEffect(() => {
        if (!isAuth) {
            fetch(USER, {
                method: 'GET',
                credentials: 'include',
            })
                .then(res => {
                    if (!res.ok) throw new Error('인증 실패');
                    return res.json();
                })
                .then(({ data }) => login(data.email, data.name, data.role))
                .catch(error => console.error(error))
                .finally(() => setIsLoading(false));
        }
    }, [isAuth, login]);

    if (isLoading) return <div>Loading...</div>;
    if (!isAuth) return <Navigate to='/login' replace />;
    if (allowedRole && role && allowedRole !== role) return <Navigate to={fallbackPath} replace />;
    return <Outlet />;
};

export default ProtectedRoute;
