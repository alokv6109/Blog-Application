import React from 'react'
import Base from "../components/Base";
import {useParams, Link} from 'react-router-dom'
import {Container, Row, Col, Card, CardBody, CardText, Input, Button } from 'reactstrap'
import {useEffect, useState} from 'react'
import {toast} from 'react-toastify'
import { loadPost } from '../services/post-service';
import { BASE_URL } from '../services/helper';
import { createComment } from '../services/post-service';
import { getCurrentUserDetail } from '../auth';
import { isLoggedIn } from '../auth';

export default function PostPage() {
    const {postId} = useParams()
    const [post, setPost] = useState(null)
    const [comment, setComment] = useState({
        content:""
        // user: getCurrentUserDetail()

    })  
    const [user, setUser] = useState(undefined) 
    useEffect(() => {
      //set current user details
      setUser(getCurrentUserDetail)
      //load the post from the postId
      loadPost(postId).then((data)=>{
        // console.log(data);
        setPost(data)
      }).catch(error => {
        console.log(error);
        toast.error("Error occured while loading the post!");        
      })
    
      
    }, [])

    const printDate =(numbers)=>{
      return new Date(numbers).toLocaleString()
    }


    const SubmitPost =()=>{
      if(!isLoggedIn()){
        toast.error("Login first!")
        return;
      }

      if(comment.content.trim()===""){
        toast.error("Please write something first!")
        return;
      }
      //submit the post
      comment["user"]= user
      createComment(comment, post.postId).then(data=>{
        // console.log(data); //comment dto object
        toast.success("Comment posted")
        setPost({
          ...post,
          comments:[...post.comments, data]
        })
        // console.log(post);
        setComment({
          content:""
        })
      }).catch(error=>{
        console.log(error);
        toast.error("Error while posting the Comment!")
      })
    }

    
    
    

  return (
    <Base>
    
      <Container className ="mt-4">
        <Link to="/">Home</Link> /{post && (<Link to="">{post.title}</Link>)}
          <Row>
            <Col md={{size:12}}>
              <Card className="mt-3">
                
                  {
                    (post) && (
                      <CardBody>

                      <CardText>
                    Posted By <b>{post?.user.name}</b> on <b>{printDate(post.addedDate)}</b>
                  </CardText>


                  <CardText className="mt-3">
                    <h3>{post.title}</h3>
                  </CardText>


                  <CardText>
                    <span className=" mt-1 text-muted">{post.category.categoryName}</span>
                  </CardText>

                  
                  <div className="image-container mt-3 container text-center shadow" style={{width:"75%"}}>
                    <img className="img-fluid" src={BASE_URL+'/api/posts/image/'+post.imageName} alt=""/>
                  </div>


                  <CardText className="mt-3" dangerouslySetInnerHTML={{__html:post.content}}>

                  </CardText>
                  </CardBody>

                    )

                  }



                  
                {/* </CardBody> */}
              </Card>
            
            </Col>
          </Row >

          <Row className="my-4">
            <Col md={{size:9, offset:1}}>
              <h3>Comments ({post ? post.comments.length :0})</h3>
              {
                  post && post.comments.map((c, index)=>(
                    <Card className="mt-1 border-0" key={index}>
                      <CardBody>
                        <CardText>{c.user.name} said : {c.content}</CardText>
                      </CardBody>
                    </Card>
                  ))
              }

                    
                    <Card className="mt-1 border-0" >
                      <CardBody>
                       <Input type="textarea" placeholder="Enter comments here" onChange={(event)=>setComment({content:event.target.value})}  value={comment.content}/>
                       <Button onClick={SubmitPost} className= "mt-1" color="primary">Submit</Button>

                      </CardBody>
                    </Card>
                  
                  
            </Col>
          </Row>
        
      </Container>
    
    </Base>
    
  )
}
