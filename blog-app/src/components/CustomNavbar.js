import {useState, useEffect, useContext} from 'react';
import {NavLink as ReactLink} from 'react-router-dom'
import {isLoggedIn, getCurrentUserDetail} from '../auth/index'
import {
    Collapse,
    Navbar,
    NavbarToggler,
    NavbarBrand,
    Nav,
    NavItem,
    NavLink,
    UncontrolledDropdown,
    DropdownToggle,
    DropdownMenu,
    DropdownItem,
    NavbarText,
  } from 'reactstrap';
import { doLogout } from '../auth';
import {useNavigate} from 'react-router-dom'
import userContext from '../context/userContext';


const CustomNavbar = ()=>{
  let navigate = useNavigate();

  const userContextData = useContext(userContext)
    
    const [isOpen, setIsOpen] = useState(false)

    //there is a thing that you can show the name of the person in place of login if the person is logged in 
    const [login, setLogin] = useState(false)  
    const [user, setUser] = useState(undefined)

    useEffect(() => {
      
      setLogin(isLoggedIn())
      setUser(getCurrentUserDetail())
    
      
    }, [login])


    const logout= ()=>{
      doLogout(()=>{
        //logge pout and then called the callback function ie once the task is done do someother task
        setLogin(false)
        userContextData.setUser({
          data:null,
          login:false
        })
        navigate("/")
      })
    }
    


    return (
        <div>
        <Navbar 
            color= "dark"
            dark
            expand ="md"
            fixed =""
            className="px-5"
            >
          <NavbarBrand tag ={ReactLink} to="/">BlogApp</NavbarBrand>
          <NavbarToggler onClick ={()=>setIsOpen(!isOpen)} />
          <Collapse isOpen={isOpen}  navbar>
            <Nav className="me-auto" navbar>
              

              <NavItem>
                <NavLink tag ={ReactLink} to ="/">
                    Feeds
                </NavLink>
              </NavItem>

              <NavItem>
                <NavLink tag ={ReactLink} to ="/about">
                    About
                </NavLink>
              </NavItem>
              <NavItem>
                <NavLink tag ={ReactLink} to ="/services">
                    Services
                </NavLink>
              </NavItem>


              
              <UncontrolledDropdown nav inNavbar>
                <DropdownToggle nav caret>
                  More Links
                </DropdownToggle>
                <DropdownMenu right>
                  {/* <DropdownItem tag={ReactLink} to="/services">Services</DropdownItem> */}
                  <DropdownItem>Contact Us</DropdownItem>
                  <DropdownItem divider />
                  <DropdownItem tog={ReactLink} to="www.youtube.com">Youtube</DropdownItem>
                  <DropdownItem >Email</DropdownItem>
                </DropdownMenu>
              </UncontrolledDropdown>
            </Nav>

            <Nav navbar>

              {
                login && (
                  <>
                    <NavItem>
                    <NavLink tag ={ReactLink} to ={`/user/profile-info/${user.id}`}>
                      Profile
                    </NavLink>
                    </NavItem>
                    <NavItem>
                    <NavLink tag ={ReactLink} to ="/user/dashboard"  >
                      {user.name}
                    </NavLink>
                    </NavItem>
                    <NavItem>
                    <NavLink onClick={logout} >
                      Logout
                    </NavLink>
                    </NavItem>

                    
                    </>
                )
              }

{
                !login && (
                  <>
                    <NavItem>
                <NavLink tag ={ReactLink} to ="/login">
                  Login
                </NavLink>
              </NavItem>
              <NavItem>
              <NavLink tag ={ReactLink} to ="/signup">
                  SignUp
                </NavLink>
              </NavItem>
                    </>
                )
              }


              
            </Nav>

            
          </Collapse>
        </Navbar>
      </div>

    )
}

export default CustomNavbar;