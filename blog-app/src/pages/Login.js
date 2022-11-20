import Base from "../components/Base";
import { Card, Container, CardHeader, CardBody, Form, FormGroup, Label, Input, Button, Row, Col } from 'reactstrap'
import {useState, useEffect, useContext} from 'react'
import {toast} from 'react-toastify'
import { signIn } from "../services/user-service";
import { doLogin } from "../auth";
import {useNavigate} from 'react-router-dom'
import userContext from "../context/userContext";

const Login=()=>{
    //hook for nbavigating from one to different page
    const navigate = useNavigate()

    const userContextData = useContext(userContext)

    const [loginDetail, setLoginDetail] = useState({
        username:'',
        password:''
    })


    const handleChange = (event, field)=>{
        let actualValue = event.target.value
        setLoginDetail({
            ...loginDetail,
            [field]:actualValue
        })
    }

    const handleReset = ()=>{
        setLoginDetail({
            username :"",
            password : ""
        })
    }

    const handleFormSubmit =(event)=>{
        event.preventDefault();
        // console.log(loginDetail)
        //validation from the front end
        if(loginDetail.username.trim()=='' || loginDetail.password.trim()=='' ){
            toast.error("Username or Password is required");
        }

        //handle the submit to server to generate token
        signIn(loginDetail).then((data)=>{
            // console.log("user logged in");
            // console.log(jwtTokenData);
            //save the data to local Storage
            doLogin(data, ()=>{
                // console.log("login details are saved to localstorage")
                userContextData.setUser({
                    data: data.user,
                    login:true
                })
            })

            toast.success("Logged In!", {autoClose:1000})
            //user dashboard redirected 
            navigate("/user/dashboard")

        }).catch(error=>{
            console.log(error)
            if(error?.response?.status==400 || error?.response?.status==401){
                toast.error(error.response.data.message)
            }else{
                toast.error(error.message)
            }
            
        })
    }


    return(
    <Base>
        
        <Container>
                <Row className="mt-4">
                    <Col sm={{ size: 6, offset: 3 }}>

                        <Card  outline="true">
                            <CardHeader>
                                <h3>Login here!</h3>
                            </CardHeader>
                            <CardBody>
                                <Form  onSubmit = {handleFormSubmit}>
                                    
                                    <FormGroup>
                                        <Label for="email">Enter email</Label>
                                        <Input type="email" placeholder="Enter here" id="email" 
                                        value = {loginDetail.username}
                                        onChange={(e)=>handleChange(e, "username")}/>
                                    </FormGroup>
                                    <FormGroup>
                                        <Label for="password">Enter password</Label>
                                        <Input type="password" placeholder="Enter here" id="password"
                                        value = {loginDetail.password} 
                                        onChange={(e)=>handleChange(e, "password")}/>
                                    </FormGroup>
                                    
                                    <Container className="text-center">
                                        <Button color="dark">
                                            Login
                                        </Button>
                                        <Button color="secondary" type="reset" className="ms-2" onClick = {handleReset}>
                                            Reset
                                        </Button>
                                    </Container>
                                </Form>
                            </CardBody>
                        </Card>
                    </Col>

                </Row>
            </Container>


        </Base>
        

    )
}

export default Login;