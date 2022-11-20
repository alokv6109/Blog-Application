import userContext from "./userContext";
import React from 'react'
import {useState, useEffect}  from 'react'
import { getCurrentUserDetail } from "../auth";
import { isLoggedIn } from "../auth";


export default function UserProvider({children}) {
    const [user, setUser] = useState({
        data:{},
        login:false
    })

    useEffect(() => {
      setUser({
        data:getCurrentUserDetail(),
        login:isLoggedIn()
      })
    
      
    }, [])
    
  return (
    <userContext.Provider  value={{user, setUser}}>
        {children}
    </userContext.Provider>
  )
}

