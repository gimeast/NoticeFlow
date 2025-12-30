import style from './JoinType.module.scss';
import organIcon from '@/assets/icons/organ.svg';
import personIcon from '@/assets/icons/person.svg';
import { Link } from 'react-router';

const JoinType = () => {
    return (
        <div className={style.wrapper}>
            <p className={style.title}>가입 유형을 선택해주세요</p>
            <ul>
                <li>
                    <Link to='/join/organ'>
                        <img src={organIcon} alt='' />
                        <div>
                            <span className={style.type}>기관 사용자</span>
                            <p className={style.typeContent}>학교, 회사, 헬스장 등 공지를 발송하는 기관</p>
                        </div>
                    </Link>
                </li>
                <li>
                    <Link to='/join/user'>
                        <img src={personIcon} alt='' />
                        <div>
                            <span className={style.type}>일반 사용자</span>
                            <p className={style.typeContent}>학부모, 직원, 회원 등 공지를 받는 사용자</p>
                        </div>
                    </Link>
                </li>
            </ul>
        </div>
    );
};

export default JoinType;
