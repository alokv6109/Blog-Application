//private page of the user explicit only 
//other login, signin etc routes are the public routes, ie once you go on the home page of the website youll see these pages and can acccess them
//but thisa page is little explicit to the user only 

import React from 'react'
import AddPost from '../../components/AddPost';
import Base from '../../components/Base';
import {Container} from 'reactstrap'
import NewFeed from '../../components/NewFeed';
import {useEffect, useState } from 'react'
import { loadPostFromUserId } from '../../services/post-service';
import {toast } from 'react-toastify'
import Post from '../../components/Post';
import { deletePostBypostId } from '../../services/post-service';

const UserDashboard = ()=> {

  const [posts, setPosts] = useState([])


  useEffect(() => {
    loadPostData()
  
    
  }, [])
  //different so that it can be called once you delete aswell , as in refreshing wont be reqd if you call htis func
  function loadPostData(){
    loadPostFromUserId().then(data=>{
      console.log(data);
      setPosts([...data].reverse())
      // toast.success("Your posts have loaded successfully")
    }).catch(error=>{
      console.log(error);
      toast.error("Error while loading the posts")
    })
  }

  //functionality to deletePost
  function deletePost(post){
    deletePostBypostId(post.postId).then(data=>{
      console.log(data);
      toast.success(data.message)
      // loadPostData() //not dping this to save an api call from the server
      let postsAfterDelete =  posts.filter(p=>p.postId != post.postId)
      setPosts([...data])
    }).catch(error=>{
      console.log(error);
    })
  }
  

  return (

    <Base>
    <Container>
    <AddPost/>
    <h1>Post Count: {posts.length}</h1>
    {
      posts.map((post, index)=>{
        return (
          <Post post={post} key={index} deletePost={deletePost}/>
        )
      })
    }
    </Container>
      
      
    
    </Base>

    
  )
}


export default UserDashboard;