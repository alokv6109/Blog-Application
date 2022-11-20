import { myAxios } from "./helper";

export const loadAllCategory =()=>{
    return myAxios.get('/api/category/').then(response=>{return response.data})
}

export const loadCategry =(categoryId)=>{
    return myAxios.get(`/api/category/${categoryId}`).then(resp=>resp.data)
}