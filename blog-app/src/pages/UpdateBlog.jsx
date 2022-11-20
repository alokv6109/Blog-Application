import React from 'react'
import {useParams, useNavigate} from 'react-router-dom'
import Base from "../components/Base";
import userContext from '../context/userContext';
import {useState, useEffect, useContext, useRef} from 'react'
import {toast } from 'react-toastify'
import { loadPost } from '../services/post-service';
import {Card, CardBody, Form, FormGroup, Label, Input,Container, Button, } from 'reactstrap';
import JoditEditor from 'jodit-react';
import { loadAllCategory } from '../services/category-services';
import { getCurrentUserDetail } from '../auth';
import { loadCategry } from '../services/category-services';
import { updatePost } from '../services/post-service';
import { uploadPostImage } from '../services/post-service';

function UpdateBlog() {
    const editor = useRef(null)
    const {postId} = useParams()
    const object = useContext(userContext)
    const navigate  =useNavigate()
    const [post, setPost] = useState(null)
    const [categories, setCategories] = useState([])
    const [image, setImage] = useState(null)
    

    useEffect(() => {

        //load the category, usage  = so that dropdown can be altered easily
        loadAllCategory().then((data)=>{
            // console.log(data)
            setCategories(data)
          }).catch(error=>{
            console.log(error);
          })

      //load the blog from server 
      loadPost(postId).then(data=>{
        console.log(data);
        setPost({...data, categoryId:data.category.categoryId})  //
        
      }).catch(error=>{
        console.log(error);
        toast.error("Some error while loading Post", {autoClose: 1200})
      })

    }, [])
    // console.log(post);



    useEffect(() => {
        //an extra check becaus the id is a guessable integer value while actualay working we wont gve the id like that remenebvr
      if(post){
        if(post.user.id != object.user.data.id) {
            toast.error("This is not your post", {autoClose:1200})
            navigate("/user/dashboard")
        }
      }
    
      
    }, [post])

    //for handeling the image value changement
    const handleFileChange =(event)=>{
        console.log(event.target.files[0]);
        setImage(event.target.files[0]);
    }


    //for handeling the changes dome on the feild so you'll just call the handle change function
    const handleChange= (event, fieldName)=>{
        setPost({...post, [fieldName]:event.target.value})
    }

    const updatePostSubmit=(event)=>{
        event.preventDefault();
        // console.log(post);
        
        //update post api called 
        updatePost({...post, category:{categoryId:post.categoryId}}, post.postId).then(data=>{
            // console.log(data);

            uploadPostImage(image, data.postId).then(data2=>{
                // console.log(data2);
                toast.success(data2.message)
            }).catch(error=>{
                toast.error("Image upload failed")
                console.log(error);
            })

            toast.success("Post Updated Succesfully")
        }).catch(error=>{
            console.log(error);
            toast.error("post not updated")
        })
        
    }

    const updateHtml =()=>{
        return(
            <div className="wrappper">
            
            <Card className="shadow-sm">
                <CardBody>
                    {/* {JSON.stringify(post)} */}
                    <h3>Update Your Post</h3>
                    <Form onSubmit ={updatePostSubmit}>

                    <FormGroup className="my-3">
                                        <Label for="title">Post title</Label>
                                        <Input type="text" placeholder="Enter here" 
                                        onChange={(event)=>handleChange(event, 'title')}
                                        name = "title"
                                        value={post.title}
                                        // invalid = {error.errors?.response?.data?.name ? true :false}
                                        // for the null safe we use ? because it is not sure that evertime the errors will have dta or not, if no data comes then it will show invalid 
                                        id="title" />
                                        {/* <FormFeedback>
                                            {error.errors?.response?.data?.name}
                                        </FormFeedback> */}

                        </FormGroup>

                        <FormGroup className="my-3">
                                        <Label for="content">Post content</Label>
                                        {/* <Input type="textarea" placeholder="Enter here" 
                                        onChange={(e)=>handleChange(e, 'name')}
                                        // value={data.name}
                                        // invalid = {error.errors?.response?.data?.name ? true :false}
                                        // for the null safe we use ? because it is not sure that evertime the errors will have dta or not, if no data comes then it will show invalid 
                                        id="content"
                                        style ={{height:'200px'}} /> */}

                                        <JoditEditor
                                                    ref={editor}
                                                    value={post.content}
                                                    // config={config}
                                                    tabIndex={1} // tabIndex of textarea
                                                    // onBlur={newContent => setContent(newContent)} // preferred to use only this option to update the content for performance reasons
                                                    onChange={newContent=>setPost({...post, content: newContent})}
                                                />


                                        {/* <FormFeedback>
                                            {error.errors?.response?.data?.name}
                                        </FormFeedback> */}

                        </FormGroup>

                        <FormGroup className="mt-3">
                            <Label for="image">Select Post banner</Label>
                            <Input id="image" type="file" onChange={handleFileChange}/>
                                

                        </FormGroup>

                        <FormGroup className="my-3">
                                        <Label for="category">Post Category</Label>
                                        <Input type="select" placeholder="Enter here" 
                                        name = "categoryId"
                                        value={post.categoryId}
                                        // invalid = {error.errors?.response?.data?.name ? true :false}
                                        // for the null safe we use ? because it is not sure that evertime the errors will have dta or not, if no data comes then it will show invalid 
                                        id="category"
                                        // defaultValue ={0}
                                        onChange={(event)=>handleChange(event, "categoryId")}
                                        >

                                            <option disabled value={0} >-- Select Category --</option>

                                                {
                                                    categories.map((category)=>(
                                                        <option key= {category.categoryId} value ={category.categoryId}>
                                                            {category.categoryName}
                                                        </option>
                                                    ))

                                                }
                                            </Input>
                                        {/* <FormFeedback>
                                            {error.errors?.response?.data?.name}
                                        </FormFeedback> */}

                        </FormGroup>

                        <Container className="text-center">
                                        <Button color="primary" type= "submit">
                                            Update Post
                                        </Button>
                                        <Button color="danger" 
                                        // onClick={resetData} type="reset" 
                                        className="ms-2">
                                            Reset
                                        </Button>
                                    </Container>

                    </Form>

                </CardBody>

            </Card>


        </div>

        )
    }

    
    
    

  return (
    <Base>
    <Container>
    { post && updateHtml()}
    </Container>
       

    </Base>
    
  )
}

export default UpdateBlog