import './App.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from './Login/Login'
import Home from "./Home/Home"


function App() {
  return (
    <div>
     <Router>
     <Routes>
           <Route exact path="/home" element={<Home />} />
           <Route exact path="/" element={<Login />} />
         </Routes>
         </Router> 
    </div>
   )
}

export default App;
