import { Link } from 'react-router';
import LogoIcon from '@/assets/logoIcon.svg?react';
import BellIcon from '@/assets/icons/bell.svg?react';
import style from './Header.module.scss';

const Header = () => {
    return (
        <header className={style.header}>
            <Link to='/dashboard' className={style.logoLink}>
                <LogoIcon />
                <div>
                    <h1>NoticeFlow</h1>
                    <small>서울고등학교</small>
                </div>
            </Link>
            <div className={style.headerActions}>
                <button>
                    <BellIcon stroke='#000' />
                </button>
                <div className={style.verticalLine}></div>
                <div className={style.userInfoWrapper}>
                    <div className={style.userInfo}>
                        <span className={style.userName}>김선생</span>
                        <span className={style.userRole}>기관 관리자</span>
                    </div>
                    <div className={style.userProfile}>김</div>
                </div>
            </div>
        </header>
    );
};

export default Header;
