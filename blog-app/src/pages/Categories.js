import React from 'react'
import Base from "../components/Base";
import {useEffect, useState } from 'react'
import {useParams } from 'react-router-dom'
import {Container} from 'reactstrap'
import CategorySideMenu from "../components/CategorySideMenu";
import {Row, Col } from 'reactstrap'
import {toast } from 'react-toastify'
import Post from '../components/Post';
import { loadPostFromCategory } from '../services/post-service';
import { deletePostBypostId } from '../services/post-service';
import CategoryWiseFeed from '../components/CategoryWiseFeed';


function Categories() {
    
    const [posts, setPosts] = useState([])
    const {categoryId}  = useParams()

    

    useEffect(() => {
        console.log(categoryId);
        
        loadPostFromCategory(categoryId)
        .then(data=>{
            setPosts([...data])
            toast.success("Posts loaded successfully")
        }).catch(error=>{
            toast.error("Error occured while loading Post")
            console.log(error);
        })
    
      
    }, [categoryId])

    // useEffect(() => {
    // //   getCategoryId()
    // const getCategoryId= ()=>{
    //     return categoryId
    // }
    
      
    // }, [categoryId])

    // useEffect(() => {
    //  console.log(categoryId);
    //     callFeed(categoryId)
    // }, [categoryId])
    
   

    // const callFeed=(categoryId)=>{
    //     return (
    //         <CategoryWiseFeed categoryId={categoryId}/>
    //     )
        

    // }
    

     //functionality to deletePost
  function deletePost(post){
    deletePostBypostId(post.postId).then(data=>{
      console.log(data);
      toast.success(data.message)
      let newPostContentAfterDelete = posts.filter(p=>p.postId != post.postId)
      setPosts([...newPostContentAfterDelete])
    }).catch(error=>{
      console.log(error);
    })
  }

    
    
    
  return (
    <Base>
    <Container className="mt-3">
            {/* <NewFeed/> */}
            <Row>
                <Col md={2} className="pt-3">
                    <CategorySideMenu/>
                </Col>

                <Col md={10}>
                    {/* {
                        <h1>category id is : {categoryId}</h1>
                    }
                <CategoryWiseFeed categoryId={categoryId}/> */}
                    
                    <h1>Blog Count: {posts.length}</h1>
                    {
                        posts && posts.map((post, index)=>{
                            return(
                                <Post key={index} post={post} deletePost={deletePost}/>
                            )
                        })
                    }
                    
                </Col>
            </Row>
    </Container>

    </Base>
  )
}

export default Categories