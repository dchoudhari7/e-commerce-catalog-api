# 🛍️ E-Commerce Product Catalog API


## **Overview**
This project is a **Spring Boot REST API** that powers an **E-Commerce Product Catalog**.  
It provides endpoints to **manage categories, products, and orders**, allowing users to view products, place orders, and track purchases.

### **📌 Features**
- 📂 **Category Management** – Create and list product categories.
- 🛒 **Product Catalog** – View, search, and paginate through products.
- 📦 **Order Processing** – Place and retrieve customer orders.
- 🔑 **JWT Authentication** – Secure endpoints with **token-based authentication**.
- 🏦 **Database Integration** – Uses **PostgreSQL** for persistent data storage.
- 📄 **Swagger API Documentation** – Easily explore and test the API.

---

## **📁 Entity Relationships**
1. **Categories** – A product belongs to a category.  
   _One-to-Many_ → **One Category** 🡒 **Many Products**
2. **Products** – Items available for purchase.  
   _Supports filtering & pagination._
3. **Orders** – Contains **one or more products** purchased by a customer.  
   _Many-to-Many_ → **One Order** 🡒 **Many Products**  
   _(Join Table: `Order_Items`)_

---

## **🛠️ Tech Stack**
| Technology       | Description |
|-----------------|------------|
| **Java 17** | Backend programming language |
| **Spring Boot 3.4.1** | Framework for API development |
| **Spring Security & JWT** | Authentication & Authorization |
| **Hibernate & JPA** | ORM for database operations |
| **PostgreSQL / MySQL** | Database for data persistence |
| **Maven** | Build and dependency management |
| **Swagger** | API Documentation |

---

## **🚀 Getting Started**
### **1. Prerequisites**
Ensure you have:
- **Java 17** installed
- **Maven** installed
- **PostgreSQL/MySQL** running

### **2. Clone the Repository**
```sh
git clone https://github.com/your-username/e-commerce-product-catalog-api.git
cd e-commerce-product-catalog-api
```

### **3. Configure Database**
* `spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db`
* `spring.datasource.username=your_db_user`
* `spring.datasource.password=your_db_password`

### **4. Run the Application**
* mvn spring-boot:run
* The API will be available at: `http://localhost:8080`

### **5. Authentication**
* The API is secured with JWT authentication.
* Login to get a token and include it in the Authorization header:
* Authorization: Bearer YOUR_JWT_TOKEN

### **6. API Endpoints**
* Swagger UI - `http://localhost:8091/swagger-ui/index.html#/`

[//]: # (* Swagger UI: http://localhost:8091/swagger-ui/index.html)

[//]: # (* OpenAPI JSON: http://localhost:8091/v3/api-docs)

[//]: # (Command to generate jwt-token - openssl rand -base64 32)
