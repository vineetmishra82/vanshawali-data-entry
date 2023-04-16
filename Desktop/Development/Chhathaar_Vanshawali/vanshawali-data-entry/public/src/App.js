import logo from './logo.svg';
import './App.css';
import Login from './Login_Page/Login';
import Home from './Home_Page/Home'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

function App() {
  return (
    <Router>
    <Routes>
          <Route exact path="/home" element={<Home />} />
          <Route exact path="/" element={<Login />} />
        </Routes>
        </Router> 
   
    )
}

export default App;
