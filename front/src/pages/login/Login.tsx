import logoIcon from '@/assets/logoIcon.svg';
import googleIcon from '@/assets/google_color.svg';
import Button from '@/components/buttons/Button.tsx';
import { Link } from 'react-router';
import style from './Login.module.scss';

const Login = () => {
    return (
        <div className={style.container}>
            <h1 className='sr-only'>NoticeFlow 로그인</h1>
            <div className={style.loginWrapper}>
                <img src={logoIcon} alt='' />
                <strong>NoticeFlow</strong>
                <p className={style.about}>공지 전달 시스템</p>
                <Button type='button' size='full' border={1}>
                    <img src={googleIcon} alt='' />
                    Google 계정으로 로그인
                </Button>
                <div className={style.joinWrapper}>
                    <span>계정이 없으신가요?</span>
                    <Link to='/join'>회원가입</Link>
                </div>
            </div>
        </div>
    );
};

export default Login;
