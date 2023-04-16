import React, { useEffect, useState } from 'react';
import * as constant from"../Assets/Constants"
import './Home.css';


function Home_Page() {

    const[vyaktiList,setVyaktiList] = useState([]);
   
    const[selectedVyakti,setSelectedVyakti] = useState({
        vyaktiId : '',
        name : '',
        fatherName : '',
        isLiving : '',
        gender : '',
        maritalStatus : '',
        isFirst : false,
        nearestRelative : {},
        relationship : '',
        isDeletable : true,
        remarks : ''
    });
    const[vyakti,setVyakti] = useState({
        vyaktiId : '',
        name : '',
        fatherName : '',
        isLiving : '',
        gender : '',
        maritalStatus : '',
        isFirst : false,
        nearestRelative : {},
        relationship : '',
        isDeletable : true,
        remarks : ''
    });
    
    const getVanshawali = () => {
        try {
            fetch(constant.getVanshawali)
                .then((response) => {
                    // console.log(response);
                    return response.json();
                })
                .then((myJson) => {
                    // console.log(myJson);
                    setVyaktiList(myJson);
                });
        } catch (e) {
            console.log(e);
        }
    };
    useEffect(() => {
        getVanshawali();
    }, []);

    
      const handleSelectedVyakti = (e) => {

        setSelectedVyakti(e.target.value);
        console.log(selectedVyakti);

    };

    
    const handleInput = (e,prop) => {

        setVyakti({
            ...vyakti,
            [prop] : e.target.value
        });

        console.log(vyakti);
       
    }

    const handleSubmit = () => {

        //Setting vyakti id
        setVyakti({
            ...vyakti,
            vyaktiId : vyaktiList.length+1
        });

        console.log(JSON.stringify(vyakti));   
        
        

       var url = constant.addVyakti; 
       fetch(url, {
        method : "Post",
        body : JSON.stringify(vyakti)
       })
       .then((response) => console.log(response));
    } 

   
  
    return(
       <div>
<header>Data Entry Page</header>

<div className='dropdownBox'>
    <select id="dropdown"
    value={selectedVyakti}
     onChange={handleSelectedVyakti}
    >
        <option value=" ">व्यक्ति चुनें</option>
        {vyaktiList &&
                        vyaktiList.length > 0 &&
                        vyaktiList.map((gname, key) => {
                            return (
                                <option key={gname.vyaktiId}> {gname.name+" पिता - "+gname.fatherName}</option>
                            );
                        })}

    </select>
</div>

<div className='DataBox'>
<label htmlFor="name" className='labelText'>व्यक्ति का नाम</label>    
<input name='name' value={vyakti.name} type="text" className="inputData" placeholder='व्यक्ति का नाम' onChange={e=> handleInput(e,'name')} /><br />

<label htmlFor="fatherName" className='labelText'>पिता का नाम</label>   
<input type="text" value={vyakti.fatherName} name='fatherName' placeholder='व्यक्ति के पिता का नाम' className="inputData" onChange={e=> handleInput(e,'fatherName')} /><br />    

<label htmlFor="living" className='labelText'>जीवित हैं ?</label>   
<select name="living" value={vyakti.isLiving}  className="inputData" placeholder='जीवित हैं ?' onChange={e=> handleInput(e,'isLiving')} >
<option disabled selected value=''>जीवित हैं ?</option>
    <option value={true} >हाँ</option>
    <option value={false}>नहीं</option>
</select><br />

<label htmlFor="gender" className='labelText'>लिंग</label>   
<select name="gender"  value={vyakti.gender} className="inputData" onChange={e=> handleInput(e,'gender')} >
<option value='' disabled selected>लिंग चुनें</option>
    <option value={"Male"} >पुरुष</option>
    <option value={"Female"}>स्त्री</option>
</select><br />

<label htmlFor="marital" className='labelText'>वैवाहिक स्थिति</label>   
<select name="marital" value={vyakti.maritalStatus} className="inputData" onChange={e=> handleInput(e,'maritalStatus')} >
    <option value={""}>वैवाहिक स्थिति ?</option>
    <option value={"Married"} >विवाहित</option>
    <option value={"UnMarried"}>अविवाहित</option>
</select><br />

<label htmlFor="nearestRelative" className='labelText'>निकटतम संबंधी</label>   
<select name="nearestRelative" value={vyakti.nearestRelative} className="inputData" onChange={e=> handleInput(e,'nearestRelative')} >
<option value={""} disabled selected>व्यक्ति चुनें</option>
        {vyaktiList &&
                        vyaktiList.length > 0 &&
                        vyaktiList.filter((person) => 
                        person.name === vyakti.name
                        ).map((gname, key) => {
                            return (
                                <option key={gname.vyaktiId}> {gname.name+" पिता - "+gname.fatherName}</option>
                            );
                        })}
</select><br />

<label htmlFor="relationship" className='labelText'>संबंधी,इस व्यक्ति के ?</label>   
<select name="relationship" value={vyakti.relationship} className="inputData" placeholder='जीवित हैं ?' onChange={e=> handleInput(e,'relationship')} >
<option value="" disabled selected>संबंध चुनें</option>
    <option value={"Father"}>पिता</option>
    <option value={"Sibling"}>भाई-बहन</option>
</select><br />

<label htmlFor="remarks" id="remarks" className='labelText'>कोई अन्य जानकारी</label>   
<textarea name='remarks' value={vyakti.remarks} className="inputData" placeholder='कुछ अन्य जानकारी' onChange={e=> handleInput(e,'remarks')} /><br />
<div className='buttonSet'>
    <button id="saveBtn" className="bottomBtn" class="btn btn-warning" onClick={handleSubmit}>Save</button>
    <button id="delBtn" disabled={!vyakti.isDeletable} className="bottomBtn" class="btn btn-danger" onClick={handleSubmit}>Delete</button>
    <button className="bottomBtn" class="btn btn-success" onClick={handleSubmit}>Reset</button>

</div>

</div>

       </div>
    )
};

export default Home_Page;