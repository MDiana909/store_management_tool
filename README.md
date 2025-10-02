# Store Management Tool
Store Management Application, with order, product, and user management, built using Spring Boot.

## Use Cases
There are three roles defined to be used with this Store Management Tools, each having different capabilities:
  - Admin - responsible for managing other users (admin, manager, staff)
    - Search a user by username
    - Retreieve a list containing all users
    - Create a new user
    - Update the role of a user
    - Delete a user
  - Manager - responsible for managing products
    - Search for a product based on the name
    - Retrieve a list containing all products
    - Add a new product
    - Update the price, name, or stock of a product
    - Delete a product
  - Staff - responsible for managing orders
    - Retrieve a list containing all orders
    - Creating a new order

## Overview
  Database: H2 + JPA <br>
  Testing: Junit + Mockito <br>
  Logging: Slf4J + Aspects + Interceptors
   
## Entities & Entity Relationships
  There are five entities defined:
  - AuditLog
  - Order
  - OrderItem
  - Product
  - User

  The following diagram displays the database schema and relationships: <br><br>
  <img width="1406" height="804" alt="image" src="https://github.com/user-attachments/assets/4451febe-80f8-4aa6-9dd1-28bf3a82cb0c" />

## Endpoints
### Orders
|    API Name     | Method | Path          | Description                 |
| --------------- | ------ | ------------- | --------------------------- |
|  Get All Orders | GET    | /api/orders   | Get a list with all orders  |
|  Create Order   | POST   | /api/orders   | Create a new order          |

### Products
|    API Name       | Method | Path                  | Description                               |
| ----------------- | ------ | --------------------- | ----------------------------------------- |
|  Get All Products | GET    | /api/products         | Get a list with all products              |
|  Search By Name   | GET    | /api/products/search  | Search products based on name             |
|  Add Product      | POST   | /api/products         | Add a new product                         |
|  Update Product   | PATCH  | /api/products/{id}    | Update a product's name, price, or stock  |
|  Delete Product   | DELETE | /api/products/{id}    | Delete a product                          |

### Users
|    API Name         | Method | Path                   | Description                     |
| ------------------- | ------ | ---------------------- | ------------------------------- |
|  Get All Users      | GET    | /api/users             | Get a list with all users       |
|  Search By Username | GET    | /api/users/{username}  | Search users based on username  |
|  Add User           | POST   | /api/users             | Add a new user                  |
|  Update User Role   | PATCH  | /api/users/{id}        | Update a users's role           |
|  Delete User        | DELETE | /api/users/{id}        | Delete a user                   |

## Authentication & Role Based Access Control

Basic HTTP Authentication was set up and configured to authenticate users.
Some users (with username, passwrod, and role) are defined using the CommandLineRunner at startup.
The authentication of users is performed using username and password.
<br><br>
Users must authenticate to use and access the protected endpoints:
  - Admin role for users management
  - Manager role for product management
  - Staff role for order manager

## Error Handling
The following Exceptions have been created for error handling:
  - NotEnoughProductsException - thrown when order could not be created due to insufficient product stock
  - OrderNotFoundException - thrown when order could not be found
  - ProductNotFoundException - thrown when product could not be found
  - UserAlreadyExistsException - thrown when an user with the provided username already exists
  - UserNotFoundException - thrown when user could not be found

## Logging
The application execution flow is logged with AOP, using the AuditLogAspect when user or order operations are registered.<br><br>
Useful information from the Service Layer is also logged using Slf4j.<br><br>
Logging Interceptors have been defined to log incoming requests and outgoing responses to/from user, order, and product endpoints.

## Java 17+ Features
Java Records have been used as DTOs for database and API calls. <br><br>
DTO Records have been created for request data, as well as response data. The DTOs are used to limit the ammount of information required as input from the user when creating/updating a resource and also hide information that is not relevant, such as the id, when sending a response back to the client.
<br><br>
The pros of using Java Records as DTOs are the fact that Records are immutable by default, preventing unintended modifications, and they enhance readability.

## Postman Payloads
### Add Product
```
{
    "name": "Burger",
    "price": 12,
    "description": "Cheese Burger",
    "stock": 30,
    "category": "FOOD"
}
```
### Create Order (products need to be created before)
```
{
    "orderItems": [
        {
            "productId": 1,
            "quantity": 10
        },
        {
            "productId": 2,
            "quantity": 2
        },
        {
            "productId": 3,
            "quantity": 5
        }
    ]
}
```
### Create User
```
{
    "username": "manager1",
    "password": "manager_password",
    "role": "MANAGER"
}
```
### Update Product
```
{
    "price": 15
}
```
```
{
    "name": "Cheese Burger",
    "price": 17,
    "stock": 20
}
```


