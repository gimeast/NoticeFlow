import type { ComponentType, SVGProps } from 'react';

interface OrganTypeItemProps {
    id: string;
    icon: ComponentType<SVGProps<SVGSVGElement>>;
    text: string;
}

const OrganTypeItem = ({ id, icon: Icon, text }: OrganTypeItemProps) => {
    return (
        <>
            <input className='sr-only' type='radio' name='organType' id={id} value={id} />
            <label htmlFor={id}>
                <Icon width={28} height={28} fill='#747474' />
                <span>{text}</span>
            </label>
        </>
    );
};

export default OrganTypeItem;
