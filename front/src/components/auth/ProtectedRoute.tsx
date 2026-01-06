import { USER } from '@/api/auth.ts';
import { useEffect, useState } from 'react';
import { Navigate, Outlet } from 'react-router';
import type { RoleType } from '@/types/authTypes.ts';
import { apiClient } from '@/api/client.ts';

interface ProtectedRouteProps {
    allowedRoles: RoleType[];
    fallbackPath: string;
}

const ProtectedRoute = ({ allowedRoles, fallbackPath }: ProtectedRouteProps) => {
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

    if (isLoading) return null;
    if (!isLoading && allowedRoles.length > 0 && allowedRoles.includes(role)) return <Outlet />;
    return <Navigate to={fallbackPath} replace />;
};

export default ProtectedRoute;
