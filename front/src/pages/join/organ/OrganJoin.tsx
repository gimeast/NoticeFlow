import style from './OrganJoin.module.scss';
import { Link, useNavigate } from 'react-router';
import SchoolIcon from '@/assets/icons/school.svg?react';
import CompanyIcon from '@/assets/icons/company.svg?react';
import ExerciseIcon from '@/assets/icons/exercise.svg?react';
import EtcIcon from '@/assets/icons/etc.svg?react';
import GoogleIcon from '@/assets/google_white.svg?react';
import Input from '@/components/inputs/Input.tsx';
import Button from '@/components/buttons/Button.tsx';
import OrganTypeItem from '@/components/join/OrganTypeItem.tsx';
import { useActionState, useEffect } from 'react';
import { joinToOrgan } from '@/actions/actions.ts';

const OrganJoin = () => {
    const navigate = useNavigate();
    const [state, formAction, isPending] = useActionState(joinToOrgan, {
        success: undefined,
        message: undefined,
        data: undefined,
    });

    useEffect(() => {
        if (state.success) {
            navigate('/dashboard');
        }
    }, [navigate, state]);

    return (
        <div className={style.wrapper}>
            <div className={style.header}>
                <p>기관 정보 입력</p>
                <Link to='/join'>유형 변경</Link>
            </div>
            <span className={style.type}>기관 유형</span>
            <form action={formAction}>
                <ul>
                    <li>
                        <OrganTypeItem id='school' icon={SchoolIcon} text='학교' value='SCHOOL' />
                    </li>
                    <li>
                        <OrganTypeItem id='company' icon={CompanyIcon} text='회사' value='COMPANY' />
                    </li>
                    <li>
                        <OrganTypeItem id='gym' icon={ExerciseIcon} text='헬스장' value='GYM' />
                    </li>
                    <li>
                        <OrganTypeItem id='etc' icon={EtcIcon} text='기타' value='ETC' />
                    </li>
                </ul>
                <Input
                    type='text'
                    labelText='기관명'
                    id='organizationName'
                    name='organizationName'
                    placeholder='예) 서울고등학교'
                    labelTextSize='md'
                />
                <Input
                    type='text'
                    labelText='담당자명'
                    id='name'
                    name='name'
                    placeholder='이름을 입력하세요'
                    labelTextSize='md'
                />
                <Input
                    type='tel'
                    labelText='연락처'
                    id='contactNumber'
                    name='contactNumber'
                    placeholder='010-1234-5678'
                    labelTextSize='md'
                />
                <Input
                    type='text'
                    labelText='기관 주소'
                    id='address'
                    name='address'
                    placeholder='주소를 입력하세요'
                    labelTextSize='md'
                />
                <Button type='submit' size='full' bgColor='orange' color='white' disabled={isPending}>
                    <GoogleIcon />
                    Google 계정으로 가입하기
                </Button>
            </form>
        </div>
    );
};

export default OrganJoin;
