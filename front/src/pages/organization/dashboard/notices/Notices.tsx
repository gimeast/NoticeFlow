import SortIcon from '@/assets/icons/sort.svg?react';
import VisibilityIcon from '@/assets/icons/visibility.svg?react';
import DeleteIcon from '@/assets/icons/delete.svg?react';
import DownloadIcon from '@/assets/icons/download.svg?react';
import AddIcon from '@/assets/icons/add.svg?react';
import NoticeIcon from '@/assets/icons/notice.svg?react';
import CalendarIcon from '@/assets/icons/calendar.svg?react';
import PersonIcon from '@/assets/icons/person.svg?react';

import style from './Notices.module.scss';
import { Link, useLoaderData } from 'react-router';
import SearchInput from '@/components/inputs/SearchInput.tsx';
import Button from '@/components/buttons/Button.tsx';
import type { NoticesType } from '@/types/noticeTypes.ts';
import Pagination from '@/components/dashboard/Pagination.tsx';
import type { CategoriesType } from '@/types/categoryTypes.ts';
import { useEffect, useState } from 'react';
import { getNotices } from '@/api/notices/noticesApi.ts';

const Notices = () => {
    const { notices, categories } = useLoaderData();

    const [currentCategory, setCurrentCategory] = useState(0);
    const [noticeList, setNoticeList] = useState(notices);

    const handleCategory = (id: number) => {
        setCurrentCategory(id);
    };

    useEffect(() => {
        getNotices(0, 4, true, currentCategory).then(res => setNoticeList(res.data.content));
    }, [currentCategory]);

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
                                <Button
                                    type='button'
                                    bgColor={`${currentCategory ? 'gray' : 'orange'}`}
                                    color={`${currentCategory ? 'black' : 'white'}`}
                                    size='md'
                                    onClick={() => handleCategory(0)}
                                >
                                    전체
                                </Button>
                            </li>
                            {categories.map((item: CategoriesType) => (
                                <li key={item.id} className={`${style.filterItem}`}>
                                    <Button
                                        type='button'
                                        bgColor={`${currentCategory === item.id ? 'orange' : 'gray'}`}
                                        color={`${currentCategory === item.id ? 'white' : 'black'}`}
                                        size='md'
                                        onClick={() => handleCategory(item.id)}
                                    >
                                        {item.name}
                                    </Button>
                                </li>
                            ))}
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
                        {noticeList.map((item: NoticesType) => (
                            <li key={item.id} className={style.noticeItem}>
                                <Link to='1' className={style.noticeWrapper}>
                                    <div className={style.noticeIconWrapper}>
                                        <NoticeIcon fill='#F98C1E' />
                                    </div>
                                    <div className={style.notice}>
                                        <h3>{item.title}</h3>
                                        <p className='ellipsis'>{item.content}</p>

                                        <div className={style.noticeInfo}>
                                            <div className={style.noticeDate}>
                                                <CalendarIcon fill='#747474' />
                                                {item.createdAt}
                                            </div>
                                            <div className={style.noticePeople}>
                                                <PersonIcon fill='#747474' width={16} height={16} />
                                                {item.personnel}
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
                        ))}
                    </ul>
                    <Pagination />
                </div>
            </section>
        </>
    );
};

export default Notices;
