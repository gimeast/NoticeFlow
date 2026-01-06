import { useEffect } from 'react';
import { USER } from '@/api/auth.ts';
import { useNavigate } from 'react-router';
import { apiClient } from '@/api/client.ts';

const OAuth2RedirectHandler = () => {
    const navigate = useNavigate();

    useEffect(() => {
        apiClient(USER, {
            method: 'GET',
            credentials: 'include',
        })
            .then(({ data }) => {
                if (data.status === 'PENDING') {
                    navigate('/join');
                } else {
                    navigate('/dashboard');
                }
            })
            .catch(error => console.error(error));
    }, [navigate]);
    return <div>로그인 처리 중...</div>;
};

export default OAuth2RedirectHandler;
