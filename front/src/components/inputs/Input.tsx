import type { ChangeEvent } from 'react';
import style from './Input.module.scss';

interface InputProps {
    type: 'text' | 'email' | 'password' | 'tel';
    labelText: string;
    id: string;
    name?: string;
    value?: string;
    onChange?: (e: ChangeEvent<HTMLInputElement>) => void;
    placeholder: string;
    labelTextSize?: 'sm' | 'md';
}

const Input = ({ type, labelText, id, name, value, onChange, placeholder, labelTextSize = 'sm' }: InputProps) => {
    let labelTextSizeStyle = null;

    switch (labelTextSize) {
        case 'sm':
            labelTextSizeStyle = style.smLabel;
            break;
        case 'md':
            labelTextSizeStyle = style.mdLabel;
            break;
    }

    return (
        <div className={style.inputWrapper}>
            <label className={labelTextSizeStyle} htmlFor={id}>
                {labelText}
            </label>
            <input type={type} id={id} name={name} value={value} onChange={onChange} placeholder={placeholder} />
        </div>
    );
};

export default Input;
