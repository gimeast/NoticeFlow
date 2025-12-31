import LogoIcon from '@/assets/logoIcon.svg?react';
import GoogleIcon from '@/assets/google_color.svg?react';
import Button from '@/components/buttons/Button.tsx';
import style from './Login.module.scss';

const Login = () => {
    return (
        <div className={style.container}>
            <h1 className='sr-only'>NoticeFlow 로그인</h1>
            <div className={style.loginWrapper}>
                <LogoIcon />
                <strong>NoticeFlow</strong>
                <p className={style.about}>공지 전달 시스템</p>
                <Button type='button' size='full' border={1}>
                    <GoogleIcon />
                    Google 계정으로 로그인
                </Button>
            </div>
        </div>
    );
};

export default Login;
