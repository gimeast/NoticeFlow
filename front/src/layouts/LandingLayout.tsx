import { Link, Outlet, useNavigate } from 'react-router';
import Logo from '@/assets/logo.svg?react';
import style from './LandingLayout.module.scss';
import Button from '@/components/buttons/Button.tsx';
import { apiClient } from '@/api/client.ts';
import { USER } from '@/api/auth.ts';

const LandingLayout = () => {
    const navigate = useNavigate();

    const handleStart = async () => {
        try {
            const { data } = await apiClient(USER, {
                method: 'GET',
                credentials: 'include',
            });

            if (data.role === 'ORGANIZATION' || data.role === 'NORMAL') navigate('/dashboard', { replace: true });
            else navigate('/login', { replace: true });
        } catch (error) {
            if (error instanceof Error) {
                navigate('/login');
            }
        }
    };
    return (
        <>
            <header className={style.header}>
                <h1>
                    <Link to='/'>
                        <Logo />
                    </Link>
                </h1>
                <div className={style.headerButtonGroup}>
                    <Link to='/login'>로그인</Link>
                    <Button type='button' size='sm' bgColor='orange' color='white' onClick={handleStart}>
                        시작하기
                    </Button>
                </div>
            </header>
            <main className={style.main}>
                <Outlet context={{ handleStart }} />
            </main>
            <footer className={style.footer}>
                <Logo />
                <small>&copy; 2025 NoticeFlow. All rights reserved.</small>
            </footer>
        </>
    );
};

export default LandingLayout;
