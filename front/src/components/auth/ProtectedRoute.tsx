import { USER } from '@/api/auth.ts';
import { useEffect, useState } from 'react';
import { Navigate, Outlet } from 'react-router';
import type { RoleType } from '@/types/authTypes.ts';
import { apiClient } from '@/api/client.ts';

interface ProtectedRouteProps {
    allowedRole: RoleType;
    fallbackPath: string;
}

const ProtectedRoute = ({ allowedRole, fallbackPath }: ProtectedRouteProps) => {
    const [isLoading, setIsLoading] = useState(true);
    const [role, setRole] = useState(null);

    useEffect(() => {
        apiClient(USER, {
            method: 'GET',
            credentials: 'include',
        })
            .then(({ data }) => {
                setRole(data.role);
            })
            .catch(error => console.error(error))
            .finally(() => setIsLoading(false));
    }, []);

    if (isLoading) return <div>Loading...</div>;
    if (allowedRole && role && allowedRole !== role) return <Navigate to={fallbackPath} replace />;
    return <Outlet />;
};

export default ProtectedRoute;
