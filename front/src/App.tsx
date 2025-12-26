import { BrowserRouter, Route, Routes } from "react-router";
import Landing from "./pages/Landing.tsx";
import LandingLayout from "./layouts/LandingLayout.tsx";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<LandingLayout />}>
          <Route path="/" element={<Landing />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
