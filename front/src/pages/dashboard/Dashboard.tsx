import NoticeIcon from '@/assets/icons/notice.svg?react';
import SendIcon from '@/assets/icons/send.svg?react';
import GroupIcon from '@/assets/icons/group.svg?react';
import TemplateIcon from '@/assets/icons/template.svg?react';
import ArrowIcon from '@/assets/icons/arrow.svg?react';
import CalendarIcon from '@/assets/icons/calendar.svg?react';
import PersonIcon from '@/assets/icons/person.svg?react';

import StatusCard from '@/components/dashboard/StatusCard.tsx';
import style from './Dashboard.module.scss';
import { useState } from 'react';
import { Link } from 'react-router';

const dummyData = [
    {
        icon: <NoticeIcon fill='#F98C1E' />,
        badgeText: '전체',
        amount: 123,
        content: '총 공지 수',
        wrapperBgColor: 'beige-200' as const,
    },
    {
        icon: <SendIcon fill='#F98C1E' />,
        badgeText: '이번달',
        amount: 56,
        content: '발송된 공지',
        wrapperBgColor: 'yellow-100' as const,
    },
    {
        icon: <GroupIcon fill='#F98C1E' />,
        badgeText: '활성',
        amount: 34,
        content: '등록된 사용자',
        wrapperBgColor: 'beige-200' as const,
    },
    {
        icon: <TemplateIcon fill='#F98C1E' />,
        badgeText: '저장됨',
        amount: 8,
        content: '템플릿',
        wrapperBgColor: 'yellow-100' as const,
    },
];

const Dashboard = () => {
    const [cardData, setCardData] = useState(dummyData);

    return (
        <>
            <h2 className={style.title}>대시보드</h2>
            <p className={style.content}>공지 발송 현황을 한눈에 확인하세요</p>

            <section className={style.cardList}>
                {cardData.map(data => (
                    <StatusCard
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
                        <li>
                            <div className={style.noticeIconWrapper}>
                                <NoticeIcon fill='#F98C1E' />
                            </div>
                            <div className={style.noticeContent}>
                                <p className={style.noticeTitle}>2024학년도 1학기 학부모 총회 안내</p>
                                <div className={style.noticeInfo}>
                                    <div>
                                        <CalendarIcon fill='#747474' />
                                        <time>2024-01-15</time>
                                    </div>
                                    <div>
                                        <PersonIcon fill='#747474' width={16} height={16} />
                                        342명
                                    </div>
                                </div>
                            </div>
                            <span>발송완료</span>
                        </li>
                        <li>
                            <div className={style.noticeIconWrapper}>
                                <NoticeIcon fill='#F98C1E' />
                            </div>
                            <div className={style.noticeContent}>
                                <p className={style.noticeTitle}>겨울방학 특별 프로그램 안내</p>
                                <div className={style.noticeInfo}>
                                    <div>
                                        <CalendarIcon fill='#747474' />
                                        <time>2024-01-12</time>
                                    </div>
                                    <div>
                                        <PersonIcon fill='#747474' width={16} height={16} />
                                        289명
                                    </div>
                                </div>
                            </div>
                            <span>발송완료</span>
                        </li>
                        <li>
                            <div className={style.noticeIconWrapper}>
                                <NoticeIcon fill='#F98C1E' />
                            </div>
                            <div className={style.noticeContent}>
                                <p className={style.noticeTitle}>1월 급식 식단표 안내</p>
                                <div className={style.noticeInfo}>
                                    <div>
                                        <CalendarIcon fill='#747474' />
                                        <time>2024-01-10</time>
                                    </div>
                                    <div>
                                        <PersonIcon fill='#747474' width={16} height={16} />
                                        342명
                                    </div>
                                </div>
                            </div>
                            <span>발송완료</span>
                        </li>
                        <li>
                            <div className={style.noticeIconWrapper}>
                                <NoticeIcon fill='#F98C1E' />
                            </div>
                            <div className={style.noticeContent}>
                                <p className={style.noticeTitle}>학교폭력 예방 교육 실시 안내</p>
                                <div className={style.noticeInfo}>
                                    <div>
                                        <CalendarIcon fill='#747474' />
                                        <time>2024-01-08</time>
                                    </div>
                                    <div>
                                        <PersonIcon fill='#747474' width={16} height={16} />
                                        342명
                                    </div>
                                </div>
                            </div>
                            <span>발송완료</span>
                        </li>
                    </ul>
                </section>
                <section>
                    <h3>빠른 작업</h3>
                    <ul>
                        <li>
                            <Link to='/dashboard/notices/new'>
                                <span>새 공지 작성</span>
                                <p>공지를 작성하고 발송하세요</p>
                            </Link>
                        </li>
                        <li>
                            <Link to='/dashboard/templates'>
                                <span>템플릿 관리</span>
                                <p>템플릿을 생성하고 편집하세요</p>
                            </Link>
                        </li>
                        <li>
                            <Link to='/dashboard/users'>
                                <span>사용자 관리</span>
                                <p>사용자를 관리하세요</p>
                            </Link>
                        </li>
                    </ul>
                </section>
            </div>
        </>
    );
};

export default Dashboard;
