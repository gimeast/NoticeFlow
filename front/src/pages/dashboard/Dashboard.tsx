import NoticeIcon from '@/assets/icons/notice.svg?react';
import SendIcon from '@/assets/icons/send.svg?react';
import GroupIcon from '@/assets/icons/group.svg?react';
import TemplateIcon from '@/assets/icons/template.svg?react';
import StatusCard from '@/components/dashboard/StatusCard.tsx';
import style from './Dashboard.module.scss';
import { useState } from 'react';

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
        </>
    );
};

export default Dashboard;
