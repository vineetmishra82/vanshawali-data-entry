import React, { useState } from "react";
import ReactDOM from "react-dom";
import axios from "axios";
import * as constant from"../Assets/Constants"
import { useNavigate } from 'react-router-dom'

// Bootstrap CSS
import "bootstrap/dist/css/bootstrap.min.css";
// Bootstrap Bundle JS
import "bootstrap/dist/js/bootstrap.bundle.min";

import './Login.css'



function Login(){
  let navigate = useNavigate();

  const[userName,setUserName] = useState("");
  const[password,setPassword] = useState("");
  const[result,setResult] = useState(false);

const handleSubmit = (e) => {

  const url = constant.adminLogin+"userId="+userName+"&password="+password;  

  console.log(url);
  axios.get(url)
      .then((response)=>{
        console.log(response);
        setResult(response);
      })
      .then(()=>{
        if(result)
      {
        navigate('/home')
      }
      else{
        alert("Invalid ID or password")
      }

      });
  
      

}
    

    return(
        <div className="loginBox">
        
          <div className="input-container">
            <label className="labelText">Username </label>
            <input id='userName' className="labelText" type="text" 
            onChange={(e) => setUserName(e.target.value)} />
              </div>
          <div className="input-container">
            <label className="labelText">Password </label>
            <input  id='passBox' className="labelText1" type="password" onChange={(e) => setPassword(e.target.value)} />
                     </div>
          <div className="button-container" >
            <input className="labelText" class="btn btn-success" type="submit" onClick={handleSubmit} />
          </div>
        
      </div>
    )
}



export default Login;