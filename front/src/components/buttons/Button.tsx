import * as React from 'react';
import style from './Button.module.scss';

interface ButtonProps {
    children: React.ReactNode;
    type: 'button' | 'submit';
    size?: 'sm' | 'md' | 'full';
    color?: 'white' | 'gray' | 'black';
    bgColor?: 'white' | 'orange' | 'gray';
    border?: 1 | 2;
    borderColor?: 'gray-200' | 'gray-300';
    disabled?: boolean;
    onClick?: () => void;
}

const Button = ({
    children,
    type,
    size,
    color = 'black',
    bgColor = 'white',
    border,
    borderColor,
    onClick,
    disabled,
}: ButtonProps) => {
    let sizeStyle = null;
    let colorStyle = null;
    let bgStyle = null;
    let borderStyle = null;

    switch (size) {
        case 'sm':
            sizeStyle = style.smSize;
            break;
        case 'md':
            sizeStyle = style.mdSize;
            break;
        case 'full':
            sizeStyle = style.fullSize;
    }

    switch (color) {
        case 'white':
            colorStyle = style.colorWhite;
            break;
        case 'gray':
            colorStyle = style.colorGray;
            break;
        case 'black':
            colorStyle = style.colorBlack;
    }

    switch (bgColor) {
        case 'white':
            bgStyle = style.bgWhite;
            break;
        case 'orange':
            bgStyle = style.bgOrange;
            break;
        case 'gray':
            colorStyle = style.bgGray;
            break;
    }

    if (border === 1) {
        borderStyle = style.smBorder;
    } else if (border === 2) {
        borderStyle = style.mdBorder;
    }

    switch (borderColor) {
        case 'gray-200':
            borderStyle += ' ' + style.borderBlack;
            break;
        case 'gray-300':
            borderStyle += ' ' + style.borderGray;
            break;
    }

    return (
        <button
            className={`${style.base} ${sizeStyle} ${colorStyle} ${bgStyle} ${borderStyle}`}
            type={type}
            onClick={onClick}
            disabled={disabled}
        >
            {children}
        </button>
    );
};

export default Button;
