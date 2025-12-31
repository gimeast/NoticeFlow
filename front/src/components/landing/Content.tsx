import BellIcon from '@/assets/icons/bell.svg?react';
import DocsIcon from '@/assets/icons/docs.svg?react';
import MailIcon from '@/assets/icons/mail.svg?react';
import GroupIcon from '@/assets/icons/group.svg?react';
import SchoolIcon from '@/assets/icons/school.svg?react';
import CompanyIcon from '@/assets/icons/company.svg?react';
import ExerciseIcon from '@/assets/icons/exercise.svg?react';
import AcademyIcon from '@/assets/icons/academy.svg?react';

import style from './Content.module.scss';

const Content = () => {
    return (
        <>
            <section className={style.featureSection}>
                <h2>주요 기능</h2>
                <p>공지 관리에 필요한 모든 것</p>
                <ul className={style.featureList}>
                    <li>
                        <div className={style.imgWrapper}>
                            <BellIcon />
                        </div>
                        <h3>단방향 공지</h3>
                        <p>답장 없이 깔끔하게 공지만 전달하세요</p>
                    </li>
                    <li>
                        <div className={style.imgWrapper}>
                            <DocsIcon />
                        </div>
                        <h3>템플릿 시스템</h3>
                        <p>조직별 맞춤 템플릿으로 빠르게 작성</p>
                    </li>
                    <li>
                        <div className={style.imgWrapper}>
                            <MailIcon />
                        </div>
                        <h3>PDF 이메일 전송</h3>
                        <p>작성한 공지를 PDF로 변환해 자동 발송</p>
                    </li>
                    <li>
                        <div className={style.imgWrapper}>
                            <GroupIcon />
                        </div>
                        <h3>회원 관리</h3>
                        <p>UUID 키로 간편하게 회원 연동</p>
                    </li>
                </ul>
            </section>
            <section className={style.organSection}>
                <h2>다양한 조직에서 활용</h2>
                <p>조직 유형별 맞춤 템플릿 제공</p>

                <ul className={style.organList}>
                    <li>
                        <SchoolIcon fill='#FFBF02' />
                        <h3>학교</h3>
                        <p>가정통신문, 학사일정 안내</p>
                    </li>
                    <li>
                        <CompanyIcon />
                        <h3>회사</h3>
                        <p>사내 공지, 정책 안내</p>
                    </li>
                    <li>
                        <ExerciseIcon />
                        <h3>헬스장</h3>
                        <p>시설 이용, 프로그램 안내</p>
                    </li>
                    <li>
                        <AcademyIcon />
                        <h3>학원</h3>
                        <p>수업 일정, 시험 안내</p>
                    </li>
                </ul>
            </section>
            <section className={style.useSection}>
                <h2>이용 방법</h2>
                <p>3단계로 간편하게 시작하세요</p>

                <ul className={style.useList}>
                    <li>
                        <span className={style.number}>01</span>
                        <div>
                            <h3>기관 가입</h3>
                            <p>조직 유형을 선택하고 기관 정보를 등록하세요</p>
                        </div>
                    </li>
                    <li>
                        <span className={style.number}>02</span>
                        <div>
                            <h3>회원 초대</h3>
                            <p>UUID 키를 공유하여 회원들을 초대하세요</p>
                        </div>
                    </li>
                    <li>
                        <span className={style.number}>03</span>
                        <div>
                            <h3>공지 발송</h3>
                            <p>템플릿으로 공지를 작성하고 PDF로 발송하세요</p>
                        </div>
                    </li>
                </ul>
            </section>
        </>
    );
};

export default Content;
