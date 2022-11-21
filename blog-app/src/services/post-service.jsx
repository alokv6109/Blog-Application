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

//upload postImge banner
export const uploadPostImage=(image, postId)=>{
    //image is sent as the form data
    let formData = new FormData();
    formData.append("image", image) //"image" this is the same as bakcend's image requrest param of the api
    //now axios will be called
    return privateAxios.post(`/api/posts/image/upload/${postId}`, formData,{
        headers:{
            "Content-Type" :"multipart/form-data"
        }
    })
    .then((response)=>response.data)
}

//function for loading post with category pagination not implemented 
export const loadPostFromCategory =(categoryId)=>{
    return myAxios.get(`/api/category/${categoryId}/posts`).then(response=>{
        
        return response.data;
    })
}

//for the pagination impolemented
// export const loadPostFromCategory= (pageNumber, pageSize, categoryId)=>{
//     return myAxios.get(`/api/category/${categoryId}/posts?pageNumber=${pageNumber}&pageSize=${pageSize}`).then(response=>response.data)
// }


//post by the user email with pagination implemented
// export const loadPostFromUserId = (pageNumber, pageSize, token)=>{
//     return privateAxios.get(`/api/user/posts?pageNumer=${pageNumber}&pageSize=${pageSize}`).then(res=>res.data)
// }

//post by user w/o paginaiton impkemeted
export const loadPostFromUserId = (token)=>{
    return privateAxios.get(`/api/users/posts`).then(res=>res.data)
}


//delete post by postId
export const deletePostBypostId =(postId)=>{    
    return privateAxios.delete(`/api/posts/${postId}`).then(response=>response.data)
}

//update the post 
export const updatePost =(post, postId)=>{
    // console.log(post);
    return privateAxios.put(`/api/posts/${postId}`, post).then(response=>response.data)
}