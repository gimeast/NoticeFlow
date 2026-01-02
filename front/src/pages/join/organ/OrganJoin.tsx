import style from './OrganJoin.module.scss';
import { Link } from 'react-router';
import SchoolIcon from '@/assets/icons/school.svg?react';
import CompanyIcon from '@/assets/icons/company.svg?react';
import ExerciseIcon from '@/assets/icons/exercise.svg?react';
import EtcIcon from '@/assets/icons/etc.svg?react';
import GoogleIcon from '@/assets/google_white.svg?react';
import Input from '@/components/inputs/Input.tsx';
import Button from '@/components/buttons/Button.tsx';
import OrganTypeItem from '@/components/join/OrganTypeItem.tsx';

const OrganJoin = () => {
    return (
        <div className={style.wrapper}>
            <div className={style.header}>
                <p>기관 정보 입력</p>
                <Link to='/join'>유형 변경</Link>
            </div>
            <span className={style.type}>기관 유형</span>
            <form>
                <ul>
                    <li>
                        <OrganTypeItem id='school' icon={SchoolIcon} text='학교' />
                    </li>
                    <li>
                        <OrganTypeItem id='company' icon={CompanyIcon} text='회사' />
                    </li>
                    <li>
                        <OrganTypeItem id='health' icon={ExerciseIcon} text='헬스장' />
                    </li>
                    <li>
                        <OrganTypeItem id='etc' icon={EtcIcon} text='기타' />
                    </li>
                </ul>
                <Input
                    type='text'
                    labelText='기관명'
                    id='organ'
                    value=''
                    onChange={() => console.log()}
                    placeholder='예) 서울고등학교'
                    labelTextSize='md'
                />
                <Input
                    type='text'
                    labelText='담당자명'
                    id='name'
                    value=''
                    onChange={() => console.log()}
                    placeholder='이름을 입력하세요'
                    labelTextSize='md'
                />
                <Input
                    type='tel'
                    labelText='연락처'
                    id='mobile'
                    value=''
                    onChange={() => console.log()}
                    placeholder='010-1234-5678'
                    labelTextSize='md'
                />
                <Input
                    type='text'
                    labelText='기관 주소'
                    id='address'
                    value=''
                    onChange={() => console.log()}
                    placeholder='주소를 입력하세요'
                    labelTextSize='md'
                />
                <Button type='submit' size='full' bgColor='orange' color='white'>
                    <GoogleIcon />
                    Google 계정으로 가입하기
                </Button>
            </form>
        </div>
    );
};

export default OrganJoin;
