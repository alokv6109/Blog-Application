import axios from 'axios';
import { getToken } from '../auth';
export const BASE_URL="http://192.168.29.40:8080";

export const myAxios = axios.create({
    baseURL : BASE_URL
});


export const privateAxios = axios.create({
    baseURL: BASE_URL
})

privateAxios.interceptors.request.use(config=>{
    const token = getToken()
    // console.log(token);
    if(token){
        config.headers.Authorization =`Bearer ${token}`
        // console.log(config);
    }
    return config;
}, error=>Promise.reject(error))