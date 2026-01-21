import style from './Pagination.module.scss';
import PaginationArrowIcon from '@/assets/icons/pagination_arrow.svg?react';
import { useEffect, useState } from 'react';

interface PaginationType {
    pageNumber: number;
    totalPages: number;
    totalElements: number;
    blockSize: number;
    handlePageNumber: (pageNumber: number) => void;
}
const Pagination = ({ pageNumber, totalPages, totalElements, blockSize, handlePageNumber }: PaginationType) => {
    const [currentBlock, setCurrentBlock] = useState(Math.floor(pageNumber / blockSize));

    const startPage = currentBlock * blockSize;
    const endPage = Math.min(startPage + blockSize - 1, totalPages - 1);
    const pageNumbers = Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i + 1);

    const handlePrevBlock = () => {
        const newBlock = currentBlock - 1;
        const newStartPageNumber = newBlock * blockSize;
        setCurrentBlock(newBlock);
        handlePageNumber(newStartPageNumber);
    };
    const handleNextBlock = () => {
        const newBlock = currentBlock + 1;
        const newStartPageNumber = newBlock * blockSize;
        setCurrentBlock(newBlock);
        handlePageNumber(newStartPageNumber);
    };

    useEffect(() => {
        setCurrentBlock(Math.floor(pageNumber / blockSize));
    }, [pageNumber, blockSize]);

    return (
        <nav className={style.paginationContainer} aria-label='페이지네이션'>
            <p className={style.pageInfo}>
                총 <span>{totalElements}</span>개의 공지
            </p>
            <div className={style.paginationWrapper}>
                <button aria-label='이전 페이지' onClick={handlePrevBlock} disabled={currentBlock === 0}>
                    <PaginationArrowIcon />
                </button>
                <ul className={style.pagination}>
                    {pageNumbers.map(page => (
                        <li key={page} className={pageNumber + 1 === page ? `${style.active}` : ''}>
                            <button onClick={() => handlePageNumber(page - 1)}>{page}</button>
                        </li>
                    ))}
                </ul>
                <button aria-label='다음 페이지' onClick={handleNextBlock} disabled={endPage >= totalPages - 1}>
                    <PaginationArrowIcon style={{ transform: 'rotate(180deg)' }} />
                </button>
            </div>
        </nav>
    );
};

export default Pagination;
