import SortIcon from '@/assets/icons/sort.svg?react';
import VisibilityIcon from '@/assets/icons/visibility.svg?react';
import DeleteIcon from '@/assets/icons/delete.svg?react';
import DownloadIcon from '@/assets/icons/download.svg?react';
import PaginationArrowIcon from '@/assets/icons/pagination_arrow.svg?react';

import { useLoaderData } from 'react-router';
import SearchInput from '@/components/inputs/SearchInput.tsx';

const Notices = () => {
    const { data } = useLoaderData();
    console.log('data:', data);
    return (
        <>
            <h2>공지 목록</h2>
            <p>발송한 공지를 확인하고 관리하세요</p>
            <section>
                <search>
                    <ul>
                        <li>전체</li>
                    </ul>
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
                </search>
                <ul>
                    <li>
                        <div>
                            <h3>2024학년도 1학기 학부모 총회 안내</h3>
                            <span>발송완료</span>
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
                        <p>
                            학부모님께 알려드립니다. 다가오는 2월 20일(화) 오후 2시에 본교 강당에서 학부모 총회를
                            개최하고자 합니다...
                        </p>
                    </li>
                </ul>
                <nav aria-label='페이지네이션'>
                    <p>
                        총 <span>6</span>개의 공지
                    </p>
                    <button aria-label='이전 페이지'>
                        <PaginationArrowIcon />
                    </button>
                    <ul>
                        <li>
                            <button>1</button>
                        </li>
                        <li>
                            <button>2</button>
                        </li>
                        <li>
                            <button>3</button>
                        </li>
                    </ul>
                    <button aria-label='다음 페이지'>
                        <PaginationArrowIcon style={{ transform: 'rotate(180deg)' }} />
                    </button>
                </nav>
            </section>
        </>
    );
};

export default Notices;
