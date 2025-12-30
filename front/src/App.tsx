import { BrowserRouter, Route, Routes } from 'react-router';
import Landing from './pages/Landing.tsx';
import LandingLayout from './layouts/LandingLayout.tsx';
import Login from '@/pages/login/Login.tsx';

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route element={<LandingLayout />}>
                    <Route path='/' element={<Landing />} />
                </Route>
                <Route path='/login' element={<Login />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
