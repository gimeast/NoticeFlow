import style from "./SearchInput.module.scss";
import type { ChangeEvent } from "react";
import searchIcon from "../../assets/icons/search.svg";

interface InputProps {
  id: string;
  label: string;
  value: string;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
  placeholder: string;
  hasIcon?: boolean;
}

const SearchInput = ({
  id,
  label,
  value,
  onChange,
  placeholder,
  hasIcon,
}: InputProps) => {
  return (
    <div className={style.wrapper}>
      {hasIcon && <img src={searchIcon} alt="" />}
      <label className="sr-only" htmlFor={id}>
        {label}
      </label>
      <input
        className={`${style.input} ${hasIcon && style.inputWithIcon}`}
        id={id}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
      />
    </div>
  );
};

export default SearchInput;
