//private page of the user explicit only 
//other login, signin etc routes are the public routes, ie once you go on the home page of the website youll see these pages and can acccess them
//but thisa page is little explicit to the user only 

import React from 'react'
import AddPost from '../../components/AddPost';
import Base from '../../components/Base';
import {Container} from 'reactstrap'
import NewFeed from '../../components/NewFeed';

const UserDashboard = ()=> {
  return (

    <Base>
    <Container>
    <AddPost/>
    {/* <NewFeed/> */}

    </Container>
      
      
    
    </Base>

    
  )
}


export default UserDashboard;