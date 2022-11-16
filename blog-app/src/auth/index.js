//isLoggedIn for the ecistence of the token in the local storage etc
export const isLoggedIn = ()=>{
    let data = localStorage.getItem("data")
    if(data==null){
        return false;
    }
    else {
        return true;
    }
}




//doLogin  = set the data to the local storage for the login to happen
export const doLogin = (data, next)=>{ //next is basically the callback function
    localStorage.setItem("data", JSON.stringify(data));
    next();
}

//getCurrentUser for getting which user is logged in 
export const getCurrentUserDetail = ()=>{
    if(isLoggedIn()){
        return JSON.parse(localStorage.getItem("data")).user
    }
    else {
        return undefined
    }
}


//doLogout = remove the dataa fom the local stporage

export const doLogout= (next)=>{
    localStorage.removeItem("data")
    next();
}


//for getting the token 
export const getToken = ()=>{
    if(isLoggedIn()){
        return JSON.parse(localStorage.getItem("data")).token
    }
    else{
        return null;
    }
}
