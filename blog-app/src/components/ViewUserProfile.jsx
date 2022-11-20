import React from 'react'
import { Card, Container, CardFooter,CardHeader, CardBody, Form, FormGroup, Label, Input, Button, Row, Col, Table } from 'reactstrap'
import { BASE_URL } from '../services/helper'
import {useEffect, useState , useContext} from 'react'
import { getCurrentUserDetail } from '../auth'
import { isLoggedIn } from '../auth'


function ViewUserProfile({ user}) {
    const [currentUser, setCurrentUser] = useState(null)
    const [login, setLogin] = useState(false)
    useEffect(() => {
      setCurrentUser(getCurrentUserDetail())
        setLogin(isLoggedIn())
    }, [])
    
  return (
    <Card>
            <CardBody>
              <h3 className="text-uppercase">User Information</h3>
              <Container className="text-center ">
                <img style={{maxWidth:"250px"}}src={BASE_URL+'/api/posts/image/'+"profilepicture.png"} alt="" className="img-fluid"></img>
              </Container>
              <Table responsive striped hover bordered={true} className="mt-4 text-center">
                <tbody>
                  <tr>
                    <td>
                      BLOGAPP ID
                    </td>
                    <td>
                      sfdsfs-ss323df{user && user.id}fdsf43543d-dss
                    </td>
                  </tr>

                </tbody>
                <tbody>
                  <tr>
                    <td>
                      USER NAME
                    </td>
                    <td>
                      {user && user.name}
                    </td>
                  </tr>

                </tbody>
                <tbody>
                  <tr>
                    <td>
                      EMAIL ID
                    </td>
                    <td>
                      {user && user.email}
                    </td>
                  </tr>

                </tbody>
                <tbody>
                  <tr>
                    <td>
                      ABOUT
                    </td>
                    <td>
                        {user && user.about}
                    </td>
                  </tr>

                </tbody>
              </Table>
            </CardBody>
            {
                currentUser ? (currentUser.id== user.id) ?
                (<CardFooter className="text-center">
                <Button color="warning">Update Profile</Button>
            </CardFooter>) : '' : ""
            }
            
          </Card>
  )
}

export default ViewUserProfile