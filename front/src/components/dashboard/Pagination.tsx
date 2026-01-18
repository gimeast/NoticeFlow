import style from './Pagination.module.scss';
import { Link } from 'react-router';
import PaginationArrowIcon from '@/assets/icons/pagination_arrow.svg?react';

const Pagination = () => {
    return (
        <nav className={style.paginationContainer} aria-label='페이지네이션'>
            <p className={style.pageInfo}>
                총 <span>6</span>개의 공지
            </p>
            <div className={style.paginationWrapper}>
                <Link to='1' aria-label='이전 페이지'>
                    <PaginationArrowIcon />
                </Link>
                <ul className={style.pagination}>
                    <li className={style.active}>
                        <Link to='1'>1</Link>
                    </li>
                    <li>
                        <Link to='2'>2</Link>
                    </li>
                    <li>
                        <Link to='3'>3</Link>
                    </li>
                </ul>
                <Link to='3' aria-label='다음 페이지'>
                    <PaginationArrowIcon style={{ transform: 'rotate(180deg)' }} />
                </Link>
            </div>
        </nav>
    );
};

export default Pagination;
