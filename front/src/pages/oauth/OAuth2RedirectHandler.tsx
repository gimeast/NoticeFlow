import { useEffect } from 'react';
import { USER } from '@/api/auth.ts';
import { useNavigate } from 'react-router';
import authStore from '@/stores/authStore.ts';

const OAuth2RedirectHandler = () => {
    const { login } = authStore();
    const navigate = useNavigate();

    useEffect(() => {
        const getUser = async () => {
            try {
                const res = await fetch(USER, {
                    method: 'GET',
                    credentials: 'include',
                });

                if (!res.ok) throw new Error('사용자 정보 조회 중 에러 발생');
                const { data } = await res.json();

                login(data.email, data.name, data.role);

                if (data.status === 'PENDING') {
                    navigate('/join');
                } else {
                    //TODO: 기관사용자, 일반사용자 분기처리 필요
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
