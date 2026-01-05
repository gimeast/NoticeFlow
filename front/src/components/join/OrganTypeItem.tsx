import type { ComponentType, SVGProps } from 'react';

interface OrganTypeItemProps {
    id: string;
    value: string;
    icon: ComponentType<SVGProps<SVGSVGElement>>;
    text: string;
}

const OrganTypeItem = ({ id, value, icon: Icon, text }: OrganTypeItemProps) => {
    return (
        <>
            <input className='sr-only' type='radio' name='type' id={id} value={value} />
            <label htmlFor={id}>
                <Icon width={28} height={28} fill='#747474' />
                <span>{text}</span>
            </label>
        </>
    );
};

export default OrganTypeItem;
