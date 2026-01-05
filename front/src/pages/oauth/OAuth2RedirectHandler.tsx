import { useEffect } from 'react';
import { USER } from '@/api/auth.ts';
import { useNavigate } from 'react-router';
import authStore from '@/stores/authStore.ts';
import { apiClient } from '@/api/client.ts';

const OAuth2RedirectHandler = () => {
    const { login } = authStore();
    const navigate = useNavigate();

    useEffect(() => {
        const getUser = async () => {
            try {
                const { data } = await apiClient(USER, {
                    method: 'GET',
                    credentials: 'include',
                });

                login(data.email, data.name, data.role);

                if (data.status === 'PENDING') {
                    navigate('/join');
                } else {
                    //TODO: 기관사용자, 일반사용자 분기처리 필요
                    navigate('/dashboard');
                }
            } catch (error) {
                console.error(error);
            }
        };

        getUser();
    }, [login, navigate]);
    return <div>로그인 처리 중...</div>;
};

export default OAuth2RedirectHandler;
