import React from 'react'
// import {useContext } from 'react'
import Base from '../../components/Base';
import userContext from '../../context/userContext'
// import {Container , Row, Col} from 'reactstrap'
import {useEffect, useState , useContext} from 'react'
import { loadPostFromUserId } from '../../services/post-service';
import { deletePostBypostId } from '../../services/post-service';
import {toast } from 'react-toastify'
import Post from '../../components/Post';
import  { useParams } from 'react-router-dom'
import { getUser } from '../../services/user-service';

import { Card, Container, CardHeader, CardBody, Form, FormGroup, Label, Input, Button, Row, Col, Table } from 'reactstrap'
import { BASE_URL } from '../../services/helper';
import ViewUserProfile from '../../components/ViewUserProfile';

const  ProfileInfo= ()=> {
  const {userId}  =useParams()
  const object = useContext(userContext)
  const [user, setUser] = useState(null)
  useEffect(() => {

    //load the user Info from the server
    getUser(userId).then(data=>{
      console.log(data);
      setUser({...data})
    }).catch(error=>{
      console.log(error);
      toast.error(error.response.data.message)
    })
  }, [])

  const userView=()=>{
    return(
      <Row>
        <Col md={{size:7, offset:3}}>
          <ViewUserProfile user={user}/>
        </Col>
      </Row>
    )
  }
  
  return (
    <Base>
    {
      user ? userView() : "Loading User Data..."
    }
    
    </Base>
    
  )
}


export default ProfileInfo;
