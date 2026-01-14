import * as React from 'react';
import style from './StatusCard.module.scss';

interface StatusCardProps {
    icon: React.ReactNode;
    badgeText?: string;
    wrapperBgColor: 'yellow-100' | 'beige-200';
    count: number;
    content: string;
}

const StatusCard = ({ icon, badgeText, wrapperBgColor, count, content }: StatusCardProps) => {
    let bgColor = null;

    switch (wrapperBgColor) {
        case 'yellow-100':
            bgColor = style.yellow;
            break;
        case 'beige-200':
            bgColor = style.beige;
            break;
    }
    return (
        <article className={style.article}>
            <div className={style.header}>
                <div className={`${style.iconWrapper} ${bgColor}`}>{icon}</div>
                {badgeText && <span className={`${style.badgeWrapper} ${bgColor}`}>{badgeText}</span>}
            </div>
            <strong className={style.count}>{count}</strong>
            <p className={style.content}>{content}</p>
        </article>
    );
};

export default StatusCard;
