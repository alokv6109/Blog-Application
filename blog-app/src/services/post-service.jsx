import { myAxios } from "./helper";
import { privateAxios } from "./helper";
// import { private } from "./helper"



export const createPost =(postData)=>{
    // console.log(postData);
    return privateAxios.post(`/api/user/${postData.userId}/category/${postData.categoryId}/posts`, postData).then(response =>response.data)
}


export const loadAllPost = (pageNumber, pageSize)=>{
    return myAxios.get(`/api/posts?pageNumber=${pageNumber}&pageSize=${pageSize}`).then(response=>response.data)
}

//load the single post from the postId

export const loadPost = (postId)=>{
    return myAxios.get(`/api/posts/${postId}`).then((response)=>{return response.data})
}


//for creating comment on a post
export const createComment =(comment, postId)=>{
    return privateAxios.post(`/api/posts/${postId}/comments`, comment).then(response=>response.data)
}
