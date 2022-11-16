import React from 'react'
import {Outlet, Navigate} from 'react-router-dom'
import { isLoggedIn } from '../auth'
const PrivateRoute=()=> {

    // let loggedIn = false;
    if(isLoggedIn()){
        return <Outlet/>
    }
    else{
        return <Navigate to={"/login"}></Navigate>
    }

  
}


export default PrivateRoute;