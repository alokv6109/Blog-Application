import Base from "../components/Base";
import userContext from "../context/userContext";


const About=()=>{
    return(
        //object is basocally {user, setUsr}
        <userContext.Consumer>
        {
            (object)=>(
            <Base>
                <h1>This is About page, nothing Fancy</h1>
                <p>Wlecome to the About page</p>
                <h1>Welcome {object.user.login && object.user.data.name}</h1>
            </Base>
            )
        }
    </userContext.Consumer>
        


    )
}

export default About;