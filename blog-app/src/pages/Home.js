import Base from "../components/Base";
import NewFeed from "../components/NewFeed";
import {Container} from 'reactstrap'

const Home=()=>{
    return(
        
        <Base>
            <Container className="mt-3">
            <NewFeed/>
            </Container>
            
            {/* <div>
            <h1>This is home page, of the Blog Application, Have a good day reading them !!</h1>
            <p>Wlecome to the home page(127.0.0.1:3000)</p>
             */}
        {/* </div> */}
        

        </Base>
        



    )
}

export default Home;