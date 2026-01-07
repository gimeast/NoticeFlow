import StatusCard from '@/components/dashboard/StatusCard.tsx';
import NoticeIcon from '@/assets/icons/notice.svg?react';
import style from './Dashboard.module.scss';

const Dashboard = () => {
    return (
        <>
            <h2 className={style.title}>대시보드</h2>
            <p className={style.content}>공지 발송 현황을 한눈에 확인하세요</p>
            <section className={style.cardList}>
                <StatusCard
                    icon={<NoticeIcon fill='#F98C1E' />}
                    badgeText='전체'
                    wrapperBgColor='yellow-100'
                    amount={156}
                    content='총 공지 수'
                />
                <StatusCard
                    icon={<NoticeIcon fill='#F98C1E' />}
                    badgeText='이번달'
                    wrapperBgColor='beige-200'
                    amount={24}
                    content='발송된 공지'
                />
                <StatusCard
                    icon={<NoticeIcon fill='#F98C1E' />}
                    badgeText='활성'
                    wrapperBgColor='yellow-100'
                    amount={342}
                    content='등록된 사용자'
                />
                <StatusCard
                    icon={<NoticeIcon fill='#F98C1E' />}
                    badgeText='저장됨'
                    wrapperBgColor='beige-200'
                    amount={8}
                    content='템플릿'
                />
            </section>
        </>
    );
};

export default Dashboard;
