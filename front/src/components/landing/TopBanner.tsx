import sendIcon from '@/assets/icons/send.svg';
import arrowIcon from '@/assets/icons/arrow_right_white.svg';
import style from './TopBanner.module.scss';
import Button from '@/components/buttons/Button.tsx';

const TopBanner = () => {
    return (
        <section className={style.topBanner}>
            <div className={style.badge}>
                <img src={sendIcon} alt='' />
                <span>단방향 소통으로 효율적인 공지 관리</span>
            </div>
            <h2>
                공지는 한 방향으로 <br /> 깔끔하게 전달하세요
            </h2>
            <p>
                카카오톡, 라인의 양방향 소통이 불편하셨나요? <br />
                NoticeFlow로 가정통신문, 회사 공지를 PDF로 깔끔하게 전달하세요.
            </p>
            <Button type='button' size='sm' bgColor='orange' color='white'>
                무료로 시작하기
                <img src={arrowIcon} alt='' />
            </Button>
        </section>
    );
};

export default TopBanner;
