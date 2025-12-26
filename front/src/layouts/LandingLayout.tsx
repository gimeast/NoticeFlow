import { Link, Outlet } from "react-router";
import logo from "../assets/icons/logo.svg";
import style from "./LandingLayout.module.scss";
import Button from "../components/buttons/Button.tsx";

const LandingLayout = () => {
  return (
    <>
      <header>
        <h1>
          <Link to="/">
            <img src={logo} alt="NoticeFlow 로고" />
          </Link>
        </h1>
        <div className={style.headerButtonGroup}>
          <button className={style.loginButton} type="button">
            로그인
          </button>
          <Button type="button" size="sm" bgColor="orange" color="white">
            시작하기
          </Button>
        </div>
      </header>
      <main>
        <Outlet />
      </main>
      <footer>
        <img src={logo} alt="NoticeFlow 로고" />
        <small>&copy; 2025 NoticeFlow. All rights reserved.</small>
      </footer>
    </>
  );
};

export default LandingLayout;
