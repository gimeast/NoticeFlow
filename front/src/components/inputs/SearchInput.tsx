import style from './SearchInput.module.scss';
import type { ChangeEvent } from 'react';
import SearchIcon from '@/assets/icons/search.svg?react';

interface InputProps {
    id: string;
    label: string;
    value: string;
    onChange: (e: ChangeEvent<HTMLInputElement>) => void;
    placeholder: string;
}

const SearchInput = ({ id, label, value, onChange, placeholder }: InputProps) => {
    return (
        <div className={style.wrapper}>
            <SearchIcon className={style.searchIcon} />
            <label className='sr-only' htmlFor={id}>
                {label}
            </label>
            <input
                className={`${style.input} ${style.inputWithIcon}`}
                id={id}
                value={value}
                onChange={onChange}
                placeholder={placeholder}
            />
        </div>
    );
};

export default SearchInput;
