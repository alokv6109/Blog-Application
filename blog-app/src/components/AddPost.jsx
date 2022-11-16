import {Card, CardBody, Form, FormGroup, Label, Input,Container, Button, } from 'reactstrap';
import { loadAllCategory } from '../services/category-services';
import {useState, useEffect, useRef} from 'react'
import JoditEditor from 'jodit-react';
import {toast} from 'react-toastify'
import { createPost as doCreatePost } from '../services/post-service';
import { getCurrentUserDetail } from '../auth';
const AddPost = ()=>{

    const editor = useRef(null)
    const [content, setContent] = useState('')
    const [categories, setCategories] = useState([])
    const [post, setPost] = useState({
                title:"",
                content:"",
                categoryId:""
    })
    const [user, setUser] = useState(undefined)    
    
    useEffect(() => {
        setUser(getCurrentUserDetail)
      loadAllCategory().then((data)=>{
        // console.log(data)
        setCategories(data)
      }).catch(error=>{
        console.log(error);
      })
    
    }, [])

    const fieldChanged = (event)=>{
        // console.log(event);
        setPost({...post, [event.target.name]:event.target.value})
    }

    const contentFieldChanged = (data)=>{
        setPost({...post, 'content':data})
    }


    //cretae postr function
    const createPost = (event)=>{
        event.preventDefault();
        // console.log(post);
        if(post.title.trim()===''){
            toast.error("post title is required")
            return;
        }

        if(post.content.trim()===''){
            toast.error("post content is required")
            return;
        }

        if(post.categoryId===''){
            toast.error("select a category")
            return;

        }

        //submit the form using the service
        post["userId"] = user.id
        doCreatePost(post).then(data=>{
            toast.success("post created");
            // console.log(data);
            setPost({
                title:"",
                content:"",
                categoryId:""
            })
        }).catch((error)=>{
            toast.error("Post not created due to some error")
            console.log(error);
        })

    }
    

    return(
        <div className="wrappper">
            
            <Card className="shadow-sm">
                <CardBody>
                    {/* {JSON.stringify(post)} */}
                    <h3>What's on your mind....</h3>
                    <Form onSubmit ={createPost}>

                    <FormGroup className="my-3">
                                        <Label for="title">Post title</Label>
                                        <Input type="text" placeholder="Enter here" 
                                        onChange={fieldChanged}
                                        name = "title"
                                        // value={data.name}
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
                                        // onChange={(e)=>handleChange(e, 'name')}
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
                                                    onChange={contentFieldChanged}
                                                />


                                        {/* <FormFeedback>
                                            {error.errors?.response?.data?.name}
                                        </FormFeedback> */}

                        </FormGroup>

                        <FormGroup className="my-3">
                                        <Label for="category">Post Category</Label>
                                        <Input type="select" placeholder="Enter here" 
                                        name = "categoryId"
                                        onChange={fieldChanged}
                                        // value={data.name}
                                        // invalid = {error.errors?.response?.data?.name ? true :false}
                                        // for the null safe we use ? because it is not sure that evertime the errors will have dta or not, if no data comes then it will show invalid 
                                        id="category"
                                        defaultValue ={0}>

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
                                            Create Post
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



export default AddPost;