import arrowIcon from '@/assets/icons/arrow_orange.svg';
import style from './JoinLayout.module.scss';
import { Link, Outlet } from 'react-router';

const JoinLayout = () => {
    return (
        <div className={style.container}>
            <h1 className='sr-only'>NoticeFlow 회원가입</h1>
            <Link className={style.backWrapper} to='/login'>
                <img src={arrowIcon} alt='' />
                <span>로그인으로 돌아가기</span>
            </Link>
            <strong>NoticeFlow</strong>
            <p className={style.about}>공지 전달 시스템</p>
            <Outlet />
        </div>
    );
};

export default JoinLayout;
