import React, { useEffect, useState } from 'react';
import * as constant from"../Assets/Constants"
import './Home.css';


function Home_Page() {

    const[vyaktiList,setVyaktiList] = useState([]);
   
    const[selectedVyakti,setSelectedVyakti] = useState({
        vyaktiId : '',
        name : '',
        isLiving : '',
        gender : '',
        maritalStatus : '',
        isFirst : false,
        nearestRelative : '',
        relationship : '',
        isDeletable : true,
        remarks : ''
    });
    const[vyakti,setVyakti] = useState({
        vyaktiId : '',
        name : '',
        isLiving : '',
        gender : '',
        maritalStatus : '',
        isFirst : false,
        nearestRelative : '',
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

    
    
      const HandleSelectedVyakti = (e) => {

        setSelectedVyakti(e.target.value);
        console.log("e.target.options.selectedIndex "+e.target.options.selectedIndex);
        var index = e.target.options.selectedIndex;
        
       for(var i = 0;i<vyaktiList.length;i++)
       {
        if((i+1)===index)
        setVyakti(vyaktiList[i]);
       }
       console.log(vyakti);
    };

    
    const HandleInput = (e,prop) => {

        var value = e.target.value;

        console.log(value);
      
        setVyakti({
            ...vyakti,
            [prop] : value
        });
        
      
      
    }

    const handleSubmit = () => {

       console.log(JSON.stringify(vyakti));           

       var url = constant.addVyakti; 
       fetch(url, {
        method : "Post",
        body : JSON.stringify(vyakti)
       })
       .then((response) => console.log(response))
       .then(()=> getVanshawali())
       .then(() => handleReset);
       
    } 

    const handleDelete = () => {

        var url = constant.removeVyakti+(vyakti.vyaktiId);

        console.log(url);

        fetch(url,{
            method : 'Delete',
        })
        .then((response) => console.log(response))
        .then(() => getVanshawali())
        .then(() => handleReset);
       

    }
    
    const handleReset = () => {

        setSelectedVyakti({ vyaktiId : '',
        name : '',
        isLiving : '',
        gender : '',
        maritalStatus : '',
        isFirst : false,
        nearestRelative : '',
        relationship : '',
        isDeletable : true,
        remarks : ''});

        setVyakti({ vyaktiId : '',
        name : '',
        isLiving : '',
        gender : '',
        maritalStatus : '',
        isFirst : false,
        nearestRelative : '',
        relationship : '',
        isDeletable : true,
        remarks : ''});

    }

const getRelationShipName = (element)=> {

    var values = element.nearestRelative.split(" id - ");


    if(element.relationship === "Father")
    {
        return " पिता - "+values[0];
    }
    else if(element.relationship === "Sibling"){
        return " सहोदर - "+values[0];
    }
    else{
        return "";
    }
}
  
  
    return(
       <div>
<header>Data Entry Page</header>

<div className='dropdownBox'>
    <select id="dropdown"
    value={selectedVyakti}
     onChange={HandleSelectedVyakti}
    >
        <option value=" " disabled selected>व्यक्ति चुनें</option>
        {vyaktiList &&
                        vyaktiList.length > 0 &&
                        vyaktiList.map((gname, key) => {
                            return (
                                <option key={gname.vyaktiId} > {gname.name+ getRelationShipName(gname)}</option>
                            );
                        })}

    </select>
</div>

<div className='DataBox'>
<label htmlFor="name" className='labelText'>व्यक्ति का नाम</label>    
<input name='name' value={vyakti.name} type="text" className="inputData" placeholder='व्यक्ति का नाम' onChange={e=> HandleInput(e,'name')} /><br />

<label htmlFor="living" className='labelText'>जीवित हैं ?</label>   
<select name="living" value={vyakti.isLiving}  className="inputData" placeholder='जीवित हैं ?' onChange={e=> HandleInput(e,'isLiving')} >
<option disabled selected value=''>जीवित हैं ?</option>
    <option value={"Yes"} >हाँ</option>
    <option value={"No"}>नहीं</option>
</select><br />

<label htmlFor="gender" className='labelText'>लिंग</label>   
<select name="gender"  value={vyakti.gender} className="inputData" onChange={e=> HandleInput(e,'gender')} >
<option value='' disabled selected>लिंग चुनें</option>
    <option value={"Male"} >पुरुष</option>
    <option value={"Female"}>स्त्री</option>
</select><br />

<label htmlFor="marital" className='labelText'>वैवाहिक स्थिति</label>   
<select name="marital" value={vyakti.maritalStatus} className="inputData" onChange={e=> HandleInput(e,'maritalStatus')} >
    <option value='' disabled selected>वैवाहिक स्थिति ?</option>
    <option value={"Married"} >विवाहित</option>
    <option value={"UnMarried"}>अविवाहित</option>
</select><br />

<label htmlFor="nearestRelative" className='labelText'>निकटतम संबंधी</label>   
<select name="nearestRelative" value={vyakti.nearestRelative} className="inputData" onChange={e=> HandleInput(e,'nearestRelative')} >
<option value='' disabled selected>व्यक्ति चुनें</option>
        {vyaktiList &&
                        vyaktiList.length > 0 &&
                        vyaktiList.filter((person) => 
                        person.name !== vyakti.name
                        ).map((gname, key) => {
                            return (
                                <option key={gname.vyaktiId} > {gname.name+" id - "+gname.vyaktiId}</option>
                            );
                        })}
</select><br />

<label htmlFor="relationship" className='labelText'>संबंधी,इस व्यक्ति के ?</label>   
<select name="relationship" value={vyakti.relationship} className="inputData" placeholder='जीवित हैं ?' onChange={e=> HandleInput(e,'relationship')} >
<option value='' disabled selected>संबंध चुनें</option>
    <option value={"Father"}>पिता</option>
    <option value={"Sibling"}>भाई-बहन</option>
</select><br />

<label htmlFor="remarks" id="remarks" className='labelText'>कोई अन्य जानकारी</label>   
<textarea name='remarks' value={vyakti.remarks} className="inputData" placeholder='कुछ अन्य जानकारी' onChange={e=> HandleInput(e,'remarks')} /><br />
<div className='buttonSet'>
    <button id="saveBtn" className="bottomBtn" class="btn btn-warning" onClick={handleSubmit}>Save</button>
    <button id="delBtn" className="bottomBtn" class="btn btn-danger" onClick={handleDelete}>Delete</button>
    <button className="bottomBtn" class="btn btn-success" onClick={handleReset}>Reset</button>

</div>

</div>

       </div>
    )
};

export default Home_Page;