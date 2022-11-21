import React from 'react'
import {useEffect, useState} from 'react'
import {Row, Col, Pagination, PaginationItem, PaginationLink, Container} from 'reactstrap'
import Post from './Post'
import {toast } from 'react-toastify'
import InfiniteScroll from 'react-infinite-scroll-component'
import { deletePostBypostId } from '../services/post-service'
import { loadPostFromCategory } from '../services/post-service'


function CategoryWiseFeed({categoryId}) {
    const [postContent, setPostContent] = useState({content:[], totalPages:'', totalElements:'', pageSize:'', lastpage:false, pageNumber:'' })
    const [currentPage, setCurrentPage] = useState(0)
    
    useEffect(() => {
      changePage(currentPage)
    
    }, [currentPage])

    const changePage =(pageNumber=0, pageSize=5)=>{
        if(pageNumber >postContent.pageNumber && postContent.lastpage){
            return;
        }
        if(pageNumber < postContent.pageNumber && postContent.pageNumber==0 ){
            return;
        }
        
        loadPostFromCategory(pageNumber, pageSize, categoryId).then(data=>{
            setPostContent({
                content:[...postContent.content, ...data.content],
                totalPages:data.totalPages,
                totalElements: data.totalElements,
                pageSize: data.pageSize,
                lastpage: data.lastpage,
                pageNumber: data.pageNumber
            })
            console.log(data);
            window.scroll(0,0)
        }).catch(error=>{
            console.log(error);
            toast.error("Error occured in loading the Post")
        })
    }
    


    const changePageInfinite=()=>{
        console.log("page is changed");
        setCurrentPage(currentPage+1)
    }

      //functionality to deletePost
  function deletePost(post){
    deletePostBypostId(post.postId).then(data=>{
      console.log(data);
      toast.success(data.message)
      let newPostContentAfterDelete = postContent.content.filter(p=>p.postId != post.postId)
      setPostContent({...postContent, content:newPostContentAfterDelete})
    }).catch(error=>{
      console.log(error);
    })
  }




  return (
    <div className="container-fluid">
        <Row>
            <Col md={{size:12}}>

                <h1>Blog Count {postContent?.totalElements}</h1>
                
                <InfiniteScroll  dataLength ={postContent.content.length}
                next ={changePageInfinite}  hasMore={!postContent.lastpage}
                loader={<h4>Loading...</h4>}
                endMessage={
                    <p style={{ textAlign: 'center' }}>
                    <b>You're All Caught Up</b>
                    </p>
                }>
                {
                    postContent?.content.map((post)=>(
                        <Post post = {post} key={post.postId} deletePost={deletePost}/>
                    ))
                }
                </InfiniteScroll>


    {/* <div>CategoryWiseFeed will displayed for the category id : {categoryId}
        <h1>Blog Count: {posts.length}</h1>
                    {
                        posts && posts.map((post, index)=>{
                            return(
                                <Post key={index} post={post} deletePost={deletePost}/>
                            )
                        })
                    } */}
                    </Col>
                    </Row>
    
    
    </div>
  )
}

export default CategoryWiseFeed