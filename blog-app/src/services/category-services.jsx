import { myAxios } from "./helper";

export const loadAllCategory =()=>{
    return myAxios.get('/api/category/').then(response=>{return response.data})
}