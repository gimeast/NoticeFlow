import { Link, Outlet } from 'react-router';
import Logo from '@/assets/logo.svg?react';
import style from './LandingLayout.module.scss';
import Button from '@/components/buttons/Button.tsx';

const LandingLayout = () => {
    return (
        <>
            <header>
                <h1>
                    <Link to='/'>
                        <Logo />
                    </Link>
                </h1>
                <div className={style.headerButtonGroup}>
                    <Link to='/login'>로그인</Link>
                    <Button type='button' size='sm' bgColor='orange' color='white'>
                        시작하기
                    </Button>
                </div>
            </header>
            <main>
                <Outlet />
            </main>
            <footer>
                <Logo />
                <small>&copy; 2025 NoticeFlow. All rights reserved.</small>
            </footer>
        </>
    );
};

export default LandingLayout;
