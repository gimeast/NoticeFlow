import SortIcon from '@/assets/icons/sort.svg?react';
import VisibilityIcon from '@/assets/icons/visibility.svg?react';
import DeleteIcon from '@/assets/icons/delete.svg?react';
import DownloadIcon from '@/assets/icons/download.svg?react';
import PaginationArrowIcon from '@/assets/icons/pagination_arrow.svg?react';
import AddIcon from '@/assets/icons/add.svg?react';
import NoticeIcon from '@/assets/icons/notice.svg?react';
import CalendarIcon from '@/assets/icons/calendar.svg?react';
import PersonIcon from '@/assets/icons/person.svg?react';

import style from './Notices.module.scss';
import { Link, useLoaderData } from 'react-router';
import SearchInput from '@/components/inputs/SearchInput.tsx';
import Button from '@/components/buttons/Button.tsx';

const Notices = () => {
    const { data } = useLoaderData();
    console.log('data:', data);
    return (
        <>
            <section className={style.noticesSection}>
                <div className={style.headerContainer}>
                    <div className={style.headerWrapper}>
                        <h2 className={style.title}>공지 목록</h2>
                        <p className={style.content}>발송한 공지를 확인하고 관리하세요</p>
                    </div>
                    <Button type='button' bgColor='orange' color='white' size='md'>
                        <AddIcon fill='#fff' width={24} height={24} />새 공지 작성
                    </Button>
                </div>
                <div className={style.noticesContainer}>
                    <search className={style.searchContainer}>
                        <ul className={style.filterList}>
                            <li className={`${style.filterItem}`}>
                                <Button type='button' bgColor='orange' color='white' size='md'>
                                    전체
                                </Button>
                            </li>

                            <li className={style.filterItem}>
                                <Button type='button' bgColor='gray' color='black' size='md'>
                                    학사일정
                                </Button>
                            </li>
                            <li className={style.filterItem}>
                                <Button type='button' bgColor='gray' color='black' size='md'>
                                    프로그램
                                </Button>
                            </li>
                            <li className={style.filterItem}>
                                <Button type='button' bgColor='gray' color='black' size='md'>
                                    급식
                                </Button>
                            </li>
                            <li className={style.filterItem}>
                                <Button type='button' bgColor='gray' color='black' size='md'>
                                    교육
                                </Button>
                            </li>
                            <li className={style.filterItem}>
                                <Button type='button' bgColor='gray' color='black' size='md'>
                                    상담
                                </Button>
                            </li>
                        </ul>
                        <div className={style.searchGroup}>
                            <SearchInput
                                id='search'
                                value=''
                                labelText='공지 키워드 검색'
                                onChange={() => console.log('')}
                                placeholder='검색...'
                            />
                            <button aria-label='정렬 버튼'>
                                <SortIcon />
                            </button>
                        </div>
                    </search>
                    <ul className={style.noticeList}>
                        <li className={style.noticeItem}>
                            <Link to='1' className={style.noticeWrapper}>
                                <div className={style.noticeIconWrapper}>
                                    <NoticeIcon fill='#F98C1E' />
                                </div>
                                <div className={style.notice}>
                                    <h3>2024학년도 1학기 학부모 총회 안내</h3>
                                    <p>
                                        학부모님께 알려드립니다. 다가오는 2월 20일(화) 오후 2시에 본교 강당에서 학부모
                                        총회를 개최하고자 합니다...
                                    </p>

                                    <div className={style.noticeInfo}>
                                        <div className={style.noticeDate}>
                                            <CalendarIcon fill='#747474' />
                                            2024-01-15
                                        </div>
                                        <div className={style.noticePeople}>
                                            <PersonIcon fill='#747474' width={16} height={16} />
                                            342명
                                        </div>
                                    </div>
                                </div>
                            </Link>
                            <div className={style.buttonGroup}>
                                <button aria-label='숨기기'>
                                    <VisibilityIcon fill='#F98C1E' />
                                </button>
                                <button aria-label='다운로드'>
                                    <DownloadIcon fill='#747474' />
                                </button>
                                <button aria-label='삭제'>
                                    <DeleteIcon fill='#FF3627' />
                                </button>
                            </div>
                        </li>
                    </ul>
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
                </div>
            </section>
        </>
    );
};

export default Notices;
