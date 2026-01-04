import authStore from '@/stores/authStore.ts';
import { useEffect } from 'react';
import { USER } from '@/api/auth.ts';
import { Outlet, useNavigate } from 'react-router';
import { apiClient } from '@/api/client.ts';

const PublicRoute = () => {
    const { isAuth, login } = authStore();
    const navigate = useNavigate();

    useEffect(() => {
        if (isAuth) {
            navigate('/', { replace: true });
        } else {
            apiClient(USER, {
                method: 'GET',
                credentials: 'include',
            })
                .then(({ data }) => login(data.email, data.name, data.role))
                .catch(error => console.error(error));
        }
    }, [isAuth, login, navigate]);

    return <Outlet />;
};

export default PublicRoute;
