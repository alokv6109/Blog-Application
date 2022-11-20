import React from 'react'
import {Card, CardBody,CardText, Button } from 'reactstrap'
import {Link} from 'react-router-dom'
import {useState, useEffect , useContext } from 'react'
import { getCurrentUserDetail , isLoggedIn } from '../auth';
import { deletePostBypostId } from '../services/post-service';
import {toast} from 'react-toastify'
import userContext from '../context/userContext';

export default function Post({post ={postId:-1,title :"This is default post title", content :"Content of the defauly post"}, deletePost}) {

  const userContextData = useContext(userContext)

  const [user, setUser] = useState(null)
  const [login, setLogin] = useState(null)
  
  useEffect(() => {
    setUser(getCurrentUserDetail())
    setLogin(isLoggedIn())
  }, [])

  // const handleDelete=()=>{
  //   deletePostBypostId(post.postId).then(data=>{
  //     console.log(data);
  //     toast.success(data.message)
  //   }).catch(error=>{
  //     toast.error("Error while deleting")
  //     console.log(error);
  //   })

  // }
  
  return (
    <Card className="border-0 shadow-sm mt-3">
        <CardBody>
            <h1>{post.title}</h1>
            <CardText dangerouslySetInnerHTML={{__html:post.content.substring(0,30)+"...."}}>
                
            </CardText>
            <div>
                <Link className="btn btn-secondary" to={"/posts/"+post.postId}>Read More</Link>
                {/* userContextdata has 2 things: user and setUser,  user has further 2 things : data, login, and data has firther 2 thjings: token and user, the user has 3 things name, email and id */}

                {
                  
                  userContextData.user.login && ( user && user.id=== post.user.id ? <Button color="danger" className="ms-2" onClick={(event)=>deletePost(post)}>Delete</Button>:'')
                }
                {userContextData.user.login && ( user && user.id=== post.user.id ? <Button color="warning" className="ms-2" tag={Link} to={`/user/update-blog/${post.postId}`}>Update</Button>:'')}
            </div>
        </CardBody>

    </Card>
  )
}
