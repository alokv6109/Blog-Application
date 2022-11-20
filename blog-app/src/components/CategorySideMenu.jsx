import React from 'react'
import { ListGroup, ListGroupItem } from 'reactstrap'
import {useState, useEffect } from 'react'
import { loadAllCategory } from '../services/category-services'
import {toast } from 'react-toastify'
import {Link } from 'react-router-dom'

export default function CategorySideMenu() {
    const [categories, setCategories] = useState([])
    useEffect(() => {
        loadAllCategory().then((data)=>{
            console.log(data);
            setCategories([...data])
        }).catch(error=>{
            console.log(error);
            toast.error("Failed to load categories")
        })
    
      
    }, [])
    

  return (
    <div>
        <ListGroup>
            <ListGroupItem tag={Link} to="/" action={true} className="border-0">
                All Blogs
            </ListGroupItem>
            {
                categories && categories.map((category, index)=>{
                    return(
                        <ListGroupItem tag={Link} to={"/categories/" + category.categoryId} action={true} className="border-0 mt-1" key={index}>
                {category.categoryName}
            </ListGroupItem>
                    )
                })


            }
            
            
        </ListGroup>
    </div>
  )
}
