import React from 'react'
import {useContext } from 'react'
import Base from '../../components/Base';
import userContext from '../../context/userContext'
import {Container} from 'reactstrap'
import {useEffect, useState } from 'react'
import { loadPostFromUserId } from '../../services/post-service';
import { deletePostBypostId } from '../../services/post-service';
import {toast } from 'react-toastify'
import Post from '../../components/Post';

const  ProfileInfo= ()=> {
  

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
      setPosts([...postsAfterDelete])
    }).catch(error=>{
      console.log(error);
    })
  }

  return (
    <Base>
    <Container>
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


export default ProfileInfo;




// import React from 'react'
// import Base from '../../components/Base';

// const  ProfileInfo= ()=> {
//   return (
//     <Base>
//     <div>
//       <h1>userInfo</h1>
//       <p>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Libero impedit dolorem maxime animi possimus quisquam temporibus quos aut nobis voluptate nihil illum praesentium reiciendis, eius sint atque voluptates consectetur sit!</p>
//     </div>
    
//     </Base>
    
//   )
// }


// export default ProfileInfo;
