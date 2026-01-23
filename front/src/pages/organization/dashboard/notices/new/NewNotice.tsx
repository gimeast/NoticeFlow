import style from './NewNotice.module.scss';
import { Link } from 'react-router';
import ArrowIcon from '@/assets/icons/arrow.svg?react';

const NewNotice = () => {
    return (
        <>
            <div className={style.headerWrapper}>
                <Link to='/dashboard/notices'>
                    <ArrowIcon fill='#F98C1E' style={{ transform: 'rotate(180deg)' }} />
                    공지 목록으로
                </Link>
                <h2 className={style.title}>새 공지 작성</h2>
                <p className={style.content}>템플릿을 선택하고 공지를 작성하세요</p>
            </div>
            <ol className={style.stepList}>
                <li className={`${style.step} ${style.active}`}>
                    <div>
                        <span>1</span>
                        <p>템플릿 선택</p>
                    </div>
                </li>
                <li className={`${style.step}`}>
                    <div>
                        <span>2</span>
                        <p>내용 작성</p>
                    </div>
                </li>
                <li className={`${style.step}`}>
                    <div>
                        <span>3</span>
                        <p>발송 확인</p>
                    </div>
                </li>
            </ol>
        </>
    );
};

export default NewNotice;
