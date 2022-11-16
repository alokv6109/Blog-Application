//all the user service or user related things and calling from the server 
import { myAxios } from "./helper";


export const signUp =(user)=>{
    return myAxios.post("/api/auth/register", user)
    .then((response)=>response.data)
};


export const signIn  =(loginDetail)=>{
    return myAxios.post('/api/auth/login', loginDetail)
    .then((response)=>response.data)
}