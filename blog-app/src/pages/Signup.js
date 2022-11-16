import Base from "../components/Base";
import {useState, useEffect} from 'react'
import { Card, Container, CardHeader, CardBody, Form, FormGroup, Label, Input, Button, Row, Col, FormFeedback } from 'reactstrap'
import { signUp } from "../services/user-service";
import {toast} from 'react-toastify'
const Signup = () => {
    //data binding has to be done 2 ways
    //1 way is when you write data in the form it gets changed and udated in the variabvle
    //2nd way is if the variables gets changes then the value has to change => for this to iplement there is a value tag
    //data mei change karna mtlb reset dabana, object se reset data ho rha hai aur screen pe dikh rha hai k reset ho gya as in nothig is found there

    const [data, setData] = useState({
        name:'',
        email:'',
        password:'',
        about:''
    })

    const [error, setError] = useState({
        errors:{}, isError:false
    })

    //for the handeling the prinitng as and when you get the data
    // //useEffect hpook
    // useEffect(() => {
    //   console.log(data);
    // }, [data])
    

    //handle the changes
    const handleChange=(event, property)=>{
        // console.log(""name changes);
        // console.log(event.target.value);
        //dynamicaaly handeled various props ie name, email, pass
        setData({...data, [property]:event.target.value})  //whatever you write there will bebtaken in here 
        // console.log();
    }

    //for resetting the data
    const resetData=()=>{
        setData({
            name:'',
            email:'',
            password:'',
            about:''
        })
    }

    //submtting thr form 
    const submitform=(event)=>{
        event.preventDefault() //isse submit homjayeaga aur poage reload ho jayega ye basic property hoti hai but ye karna nhiu hai 
        //that is why prevent Dafault is the e
        
        console.log(data);
        //data validate 

        // if(error.isError){
        //     toast.error("Form data is Invalid, Please correct the information provided.");
        //     setError({...error, isError:false})
        //     return;
        //     //return the control flow if the error is there for the form feilds and the submittingw will be avoided
        // }


        //call server api for sending the data
        //axios is used for the server ko call karna 
        signUp(data).then((resp)=>{
            console.log(resp);
            console.log("succes log");
            toast.success("User is registered succesfully ! " + resp.id);
            setData({
                name:'',
                email:'',
                password:'',
                about:''
            })

        }).catch((error)=>{
            console.log(error);
            console.log("error log");
            //handeling error in proper way
            //how to access the error ie errorObject.response.data.name/email/pasword
            //for the use state thing
            //error.errors.response.data.name/email/pasword
            setError({
                errors:error,
                isError: true
            })

        })

        //react-toastify for messgae display
    }


    return (
        <Base>
            <Container>
                <Row className="mt-4">
                    {/* {JSON.stringify(data)} */}
                    <Col sm={{ size: 6, offset: 3 }}>

                        <Card  outline="true">
                            <CardHeader>
                                <h3>Fill Infomation to Register</h3>
                            </CardHeader>
                            <CardBody>
                                <Form onSubmit={submitform}>
                                    {/* name field */}
                                    <FormGroup>
                                        <Label for="name">Enter name</Label>
                                        <Input type="text" placeholder="Enter here" 
                                        onChange={(e)=>handleChange(e, 'name')}
                                        value={data.name}
                                        invalid = {error.errors?.response?.data?.name ? true :false}
                                        // for the null safe we use ? because it is not sure that evertime the errors will have dta or not, if no data comes then it will show invalid 
                                        id="name" />
                                        <FormFeedback>
                                            {error.errors?.response?.data?.name}
                                        </FormFeedback>

                                    </FormGroup>

                                    <FormGroup>
                                        <Label for="email">Enter email</Label>
                                        <Input type="email" placeholder="Enter here" 
                                        onChange={(e)=>handleChange(e, 'email')}
                                        value={data.email}
                                        id="email"
                                        invalid = {error.errors?.response?.data?.email ? true :false} />
                                        <FormFeedback>
                                            {error.errors?.response?.data?.email}
                                        </FormFeedback>
                                    </FormGroup>

                                    <FormGroup>
                                        <Label for="password">Enter password</Label>
                                        <Input type="password" placeholder="Enter here"
                                        onChange={(e)=>handleChange(e, 'password')} 
                                        value={data.password}
                                        id="password" 
                                        invalid = {error.errors?.response?.data?.password ? true :false}/>
                                        <FormFeedback>
                                            {error.errors?.response?.data?.password}
                                        </FormFeedback>
                                    </FormGroup>

                                    <FormGroup>
                                        <Label for="about" >Enter About</Label>
                                        <Input type="textarea" style={{height:"200px"}}placeholder="Enter here" 
                                        onChange={(e)=>handleChange(e, 'about')}
                                        value={data.about}
                                        id="about" 
                                        invalid = {error.errors?.response?.data?.about ? true :false}/>
                                        <FormFeedback>
                                            {error.errors?.response?.data?.about}
                                        </FormFeedback>
                                    </FormGroup>


                                    <Container className="text-center">
                                        <Button color="dark">
                                            Register
                                        </Button>
                                        <Button color="secondary" onClick={resetData} type="reset" className="ms-2">
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

export default Signup;