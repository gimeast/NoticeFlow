import Button from '@/components/buttons/Button.tsx';
import arrowIcon from '@/assets/icons/arrow_right_black.svg';
import style from './FooterBanner.module.scss';

const FooterBanner = () => {
    return (
        <section className={style.footerBanner}>
            <h2>지금 바로 시작하세요</h2>
            <p>무료로 가입하고 단방향 공지의 편리함을 경험하세요</p>
            <Button type='button' size='sm' bgColor='white' color='black'>
                무료로 시작하기
                <img src={arrowIcon} alt='' />
            </Button>
        </section>
    );
};

export default FooterBanner;
