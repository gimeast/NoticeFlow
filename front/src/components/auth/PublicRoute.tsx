import authStore from '@/stores/authStore.ts';
import { useEffect } from 'react';
import { USER } from '@/api/auth.ts';
import { Outlet, useNavigate } from 'react-router';

const PublicRoute = () => {
    const { isAuth, login } = authStore();
    const navigate = useNavigate();

    useEffect(() => {
        if (isAuth) {
            navigate('/', { replace: true });
        } else {
            fetch(USER, {
                method: 'GET',
                credentials: 'include',
            })
                .then(res => res.json())
                .then(({ data }) => login(data.email, data.name, data.role))
                .catch(error => console.error(error));
        }
    }, [isAuth, login, navigate]);

    return <Outlet />;
};

export default PublicRoute;
