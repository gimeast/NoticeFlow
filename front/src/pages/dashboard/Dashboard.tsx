import NoticeIcon from '@/assets/icons/notice.svg?react';
import SendIcon from '@/assets/icons/send.svg?react';
import GroupIcon from '@/assets/icons/group.svg?react';
import TemplateIcon from '@/assets/icons/template.svg?react';
import ArrowIcon from '@/assets/icons/arrow.svg?react';
import CalendarIcon from '@/assets/icons/calendar.svg?react';
import PersonIcon from '@/assets/icons/person.svg?react';
import AddIcon from '@/assets/icons/add.svg?react';
import KeyIcon from '@/assets/icons/key.svg?react';
import CopyIcon from '@/assets/icons/copy.svg?react';

import StatusCard from '@/components/dashboard/StatusCard.tsx';
import style from './Dashboard.module.scss';
import { useEffect, useState } from 'react';
import { Link } from 'react-router';
import { getNotices } from '@/api/dashboardApi.ts';
import type { NoticeListType } from '@/types/dashboardTypes.ts';

const statusCardDummyData = [
    {
        id: 1,
        icon: <NoticeIcon fill='#F98C1E' />,
        badgeText: '전체',
        amount: 123,
        content: '총 공지 수',
        wrapperBgColor: 'beige-200' as const,
    },
    {
        id: 2,
        icon: <SendIcon fill='#F98C1E' />,
        badgeText: '이번달',
        amount: 56,
        content: '발송된 공지',
        wrapperBgColor: 'yellow-100' as const,
    },
    {
        id: 3,
        icon: <GroupIcon fill='#F98C1E' />,
        badgeText: '활성',
        amount: 34,
        content: '등록된 사용자',
        wrapperBgColor: 'beige-200' as const,
    },
    {
        id: 4,
        icon: <TemplateIcon fill='#F98C1E' />,
        badgeText: '저장됨',
        amount: 8,
        content: '템플릿',
        wrapperBgColor: 'yellow-100' as const,
    },
];

const Dashboard = () => {
    const [statusList, setStatusList] = useState(statusCardDummyData);
    const [noticeList, setNoticeList] = useState<NoticeListType[]>([]);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const [noticeData] = await Promise.all([getNotices(0, 4)]);
                setNoticeList(noticeData.data.content);
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
    }, []);
    return (
        <>
            <h2 className={style.title}>대시보드</h2>
            <p className={style.content}>공지 발송 현황을 한눈에 확인하세요</p>

            <section className={style.cardList}>
                {statusList.map(data => (
                    <StatusCard
                        key={data.id}
                        icon={data.icon}
                        badgeText={data.badgeText}
                        amount={data.amount}
                        content={data.content}
                        wrapperBgColor={data.wrapperBgColor}
                    />
                ))}
            </section>
            <div className={style.contentsContainer}>
                <section className={style.noticeContainer}>
                    <div className={style.noticeHeader}>
                        <h3>최근 발송 공지</h3>
                        <Link to='/dashboard/notices'>
                            전체보기
                            <ArrowIcon fill='#F98C1E' />
                        </Link>
                    </div>
                    <ul className={style.noticeList}>
                        {noticeList.map(notice => (
                            <li key={notice.id}>
                                <div className={style.noticeIconWrapper}>
                                    <NoticeIcon fill='#F98C1E' />
                                </div>
                                <div className={style.noticeContent}>
                                    <p className={style.noticeTitle}>{notice.title}</p>
                                    <div className={style.noticeInfo}>
                                        <div>
                                            <CalendarIcon fill='#747474' />
                                            <time>{notice.createdAt.split('T')[0]}</time>
                                        </div>
                                        <div>
                                            <PersonIcon fill='#747474' width={16} height={16} />
                                            {notice.personnel}명
                                        </div>
                                    </div>
                                </div>
                                <span>발송완료</span>
                            </li>
                        ))}
                    </ul>
                </section>
                <section className={style.quickWrapper}>
                    <h3 className={style.quickTitle}>빠른 작업</h3>
                    <ul className={style.quickList}>
                        <li>
                            <Link to='/dashboard/notices/new'>
                                <div className={style.iconWrapper}>
                                    <AddIcon fill='#fff' />
                                </div>
                                <div className={style.quickContents}>
                                    <h4 className={style.quickContentTitle}>새 공지 작성</h4>
                                    <p>공지를 작성하고 발송하세요</p>
                                </div>
                                <div className={style.quickArrow}>
                                    <ArrowIcon fill='#fff' />
                                </div>
                            </Link>
                        </li>
                        <li>
                            <Link to='/dashboard/templates'>
                                <div className={style.iconWrapper}>
                                    <TemplateIcon fill='#747474' />
                                </div>
                                <div className={style.quickContents}>
                                    <h4 className={style.quickContentTitle}>템플릿 관리</h4>
                                    <p>템플릿을 생성하고 편집하세요</p>
                                </div>
                                <div className={style.quickArrow}>
                                    <ArrowIcon fill='#747474' />
                                </div>
                            </Link>
                        </li>
                        <li>
                            <Link to='/dashboard/users'>
                                <div className={style.iconWrapper}>
                                    <GroupIcon fill='#747474' />
                                </div>
                                <div className={style.quickContents}>
                                    <h4 className={style.quickContentTitle}>사용자 관리</h4>
                                    <p>사용자를 관리하세요</p>
                                </div>
                                <div className={style.quickArrow}>
                                    <ArrowIcon fill='#747474' />
                                </div>
                            </Link>
                        </li>
                    </ul>
                    <div className={style.organConnectCodeContainer}>
                        <div className={style.organConnectCodeWrapper}>
                            <div className={style.keyIconWrapper}>
                                <KeyIcon fill='#F98C1E' />
                            </div>
                            <div className={style.organConnectCodeBox}>
                                <h4>기관 연결 코드</h4>
                                <div className={style.organConnectCodeContent}>
                                    <p className='ellipsis'>a1b2c3d4-e5f6-7890-abcd-1234567890ef</p>
                                    <button aria-label='기관 연결 코드 복사'>
                                        <CopyIcon fill='#F98C1E' />
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </>
    );
};

export default Dashboard;
