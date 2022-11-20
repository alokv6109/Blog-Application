import Base from "../components/Base";
import userContext from "../context/userContext";

const Services = ()=>{
    return (
        <userContext.Consumer>
            {
                (object)=>(
                    <Base>
                <h1>this is services page, Welcome: {object.user.login && object.user.data.name}</h1>
            </Base>
                )
            }

        </userContext.Consumer>
        
        
    )

}


export default Services;