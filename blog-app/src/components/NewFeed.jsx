import React from 'react'
import {useEffect, useState} from 'react'
import { loadAllPost } from '../services/post-service'
import {Row, Col, Pagination, PaginationItem, PaginationLink, Container} from 'reactstrap'
import Post from './Post'
import {toast } from 'react-toastify'

const NewFeed=()=>{

    const [postContent, setPostContent] = useState({content:[], totalPages:'', totalElements:'', pageSize:'', lastpage:false, pageNumber:'' })

    useEffect(() => {
        //load all the posts from the server
        changePage(0)
        
    
      
    }, [])


    const changePage =(pageNumber=0, pageSize=5)=>{
        if(pageNumber >postContent.pageNumber && postContent.lastpage){
            return;
        }
        if(pageNumber < postContent.pageNumber && postContent.pageNumber==0 ){
            return;
        }
        
        loadAllPost(pageNumber, pageSize).then(data=>{
            setPostContent(data)
            // console.log(data);
            window.scroll(0,0)
        }).catch(error=>{
            console.log(error);
            toast.error("Error occured in loading the Post")
        })
    }
    
    


  return (
    <div className="container-fluid">
        <Row>
            <Col md={{size:10, offset:1}}>

                <h1>Blog Count {postContent?.totalElements}</h1>
                {
                    postContent?.content.map((post)=>(
                        <Post post = {post} key={post.postId}/>
                    ))
                }

                <Container className ='mt-3'>
                <Pagination  size='lg'>
                    <PaginationItem onClick={()=>changePage(postContent.pageNumber-1)} disabled={postContent.pageNumber==0}>
                        <PaginationLink> Previous</PaginationLink> 
                    </PaginationItem>
                    {/* </Pagination> */}
                    
                    {
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
                        <PaginationItem onClick ={()=>changePage(postContent.pageNumber+1)} disabled={postContent.lastpage}>
                        <PaginationLink>
                            Next</PaginationLink>
                        </PaginationItem>
                    {/* <PaginationItem> */}
                </Pagination>


                </Container >
                



                

            
            </Col>
        </Row>
    </div>
  )
}


export default NewFeed;

