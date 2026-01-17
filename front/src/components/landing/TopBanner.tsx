import SendIcon from '@/assets/icons/send.svg?react';
import ArrowIcon from '@/assets/icons/arrow.svg?react';
import style from './TopBanner.module.scss';
import Button from '@/components/buttons/Button.tsx';
import { useOutletContext } from 'react-router';

const TopBanner = () => {
    const { handleStart } = useOutletContext<{ handleStart: () => void }>();

    return (
        <section className={style.topBanner}>
            <div className={style.badge}>
                <SendIcon />
                <span>단방향 소통으로 효율적인 공지 관리</span>
            </div>
            <p className={style.title}>
                공지는 한 방향으로 <br /> 깔끔하게 전달하세요
            </p>
            <p className={style.content}>
                카카오톡, 라인의 양방향 소통이 불편하셨나요? <br />
                NoticeFlow로 가정통신문, 회사 공지를 PDF로 깔끔하게 전달하세요.
            </p>
            <Button type='button' size='md' bgColor='orange' color='white' onClick={handleStart}>
                무료로 시작하기
                <ArrowIcon fill='#fff' />
            </Button>
        </section>
    );
};

export default TopBanner;
