import { NavLink } from 'react-router';
import style from './OrganizationSidebar.module.scss';
import DashboardIcon from '@/assets/icons/dashboard.svg?react';
import NoticeIcon from '@/assets/icons/notice.svg?react';
import AddIcon from '@/assets/icons/add_circle.svg?react';
import TemplateIcon from '@/assets/icons/template.svg?react';
import UserIcon from '@/assets/icons/group.svg?react';
import SettingIcon from '@/assets/icons/settings.svg?react';
import LogoutIcon from '@/assets/icons/logout.svg?react';

const OrganizationSidebar = () => {
    return (
        <aside className={style.aside}>
            <nav aria-label='메인 메뉴'>
                <ul>
                    <li>
                        <NavLink to='/dashboard' end className={({ isActive }) => (isActive ? style.active : '')}>
                            {({ isActive }) => (
                                <>
                                    <DashboardIcon fill={isActive ? '#F98C1E' : '#747474'} />
                                    대시보드
                                </>
                            )}
                        </NavLink>
                    </li>
                    <li>
                        <NavLink
                            to='/dashboard/notices'
                            end
                            className={({ isActive }) => (isActive ? style.active : '')}
                        >
                            {({ isActive }) => (
                                <>
                                    <NoticeIcon fill={isActive ? '#F98C1E' : '#747474'} />
                                    공지 목록
                                </>
                            )}
                        </NavLink>
                    </li>
                    <li>
                        <NavLink
                            to='/dashboard/notices/new'
                            className={({ isActive }) => (isActive ? style.active : '')}
                        >
                            {({ isActive }) => (
                                <>
                                    <AddIcon fill={isActive ? '#F98C1E' : '#747474'} />
                                    공지 작성
                                </>
                            )}
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to='/dashboard/templates' className={({ isActive }) => (isActive ? style.active : '')}>
                            {({ isActive }) => (
                                <>
                                    <TemplateIcon fill={isActive ? '#F98C1E' : '#747474'} />
                                    템플릿 관리
                                </>
                            )}
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to='/dashboard/users' className={({ isActive }) => (isActive ? style.active : '')}>
                            {({ isActive }) => (
                                <>
                                    <UserIcon fill={isActive ? '#F98C1E' : '#747474'} />
                                    사용자 관리
                                </>
                            )}
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to='/dashboard/settings' className={({ isActive }) => (isActive ? style.active : '')}>
                            {({ isActive }) => (
                                <>
                                    <SettingIcon fill={isActive ? '#F98C1E' : '#747474'} />
                                    설정
                                </>
                            )}
                        </NavLink>
                    </li>
                </ul>
            </nav>
            <div className={style.logoutBtnWrapper}>
                <button className={style.logoutBtn}>
                    <LogoutIcon />
                    <span>로그아웃</span>
                </button>
            </div>
        </aside>
    );
};

export default OrganizationSidebar;
