import React from 'react'
import {useEffect, useState} from 'react'
import { loadAllPost } from '../services/post-service'
import {Row, Col, Pagination, PaginationItem, PaginationLink, Container} from 'reactstrap'
import Post from './Post'
import {toast } from 'react-toastify'
import InfiniteScroll from 'react-infinite-scroll-component'
import { deletePostBypostId } from '../services/post-service'

const NewFeed=()=>{

    const [postContent, setPostContent] = useState({content:[], totalPages:'', totalElements:'', pageSize:'', lastpage:false, pageNumber:'' })
    const [currentPage, setCurrentPage] = useState(0)  
    useEffect(() => {
        //load all the posts from the server
        changePage(currentPage)
        
    
      
    }, [currentPage])   //whenever the current page vale changes then use Effect will be called

    

    const changePage =(pageNumber=0, pageSize=5)=>{
        if(pageNumber >postContent.pageNumber && postContent.lastpage){
            return;
        }
        if(pageNumber < postContent.pageNumber && postContent.pageNumber==0 ){
            return;
        }
        
        loadAllPost(pageNumber, pageSize).then(data=>{
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
                
                


                {/* //code base for the single single paginated oost */}
                 {/* <Container className ='mt-3'>
                <Pagination  size='lg'>
                    <PaginationItem onClick={()=>changePage(postContent.pageNumber-1)} disabled={postContent.pageNumber==0}>
                        <PaginationLink> Previous</PaginationLink> 
                    </PaginationItem>
                    {/* </Pagination> */}
                    
                    {/* {
                        [...Array(postContent.totalPages)].map((item, index)=>(
                        //  <Pagination>
                        <PaginationItem onClick={()=>changePage(index)} active ={index==postContent.pageNumber} key={index}>
                            <PaginationLink >
                                {index+1}
                            </PaginationLink>
                        </PaginationItem>
                        
                            ))
                    }

                    
                    {/* <Pagination> */}
                        {/* <PaginationItem onClick ={()=>changePage(postContent.pageNumber+1)} disabled={postContent.lastpage}>
                        <PaginationLink>
                            Next</PaginationLink>
                        </PaginationItem> */}
                    {/* <PaginationItem> */}
                 {/* </Pagination> */}


                {/* </Container >  */}
                  
*


                

            
            </Col>
        </Row>
    </div>
  )
}


export default NewFeed;

