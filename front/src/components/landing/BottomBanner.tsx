import Button from '@/components/buttons/Button.tsx';
import ArrowIcon from '@/assets/icons/arrow.svg?react';
import style from './BottomBanner.module.scss';

const BottomBanner = () => {
    return (
        <section className={style.bottomBanner}>
            <p className={style.title}>지금 바로 시작하세요</p>
            <p className={style.content}>무료로 가입하고 단방향 공지의 편리함을 경험하세요</p>
            <Button type='button' size='sm' bgColor='white' color='black'>
                무료로 시작하기
                <ArrowIcon fill='#000' />
            </Button>
        </section>
    );
};

export default BottomBanner;
