# Spring Boot GraphQL API

A modern, full-stack API built with **Spring Boot 3.x** and **Java 17**, providing both **REST** and **GraphQL** interfaces for product management. This application demonstrates best practices in API development, including data validation, error handling, and database persistence.

## Context and Overview

This project serves as a comprehensive example of building scalable APIs with Spring Boot. It combines traditional REST endpoints with modern GraphQL queries and mutations, allowing flexible data fetching and manipulation. The application manages a product catalog with features like CRUD operations, status filtering, and search functionality.

### Key Technologies
- **Spring Boot 3.x**: Framework for building production-ready applications
- **Java 17**: Modern Java with pattern matching and records
- **GraphQL**: Flexible query language for APIs
- **Spring Data JPA**: ORM for database operations
- **H2 Database**: In-memory database for development
- **Spring Validation**: Input validation
- **OpenAPI/Swagger**: REST API documentation

## APIs Overview

### REST API
Traditional REST endpoints for product management:
- `GET /api/products` - List all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product (soft delete)
- `GET /api/products/search?name={term}` - Search products

### GraphQL API
Modern GraphQL interface with mutations and queries :
- **Mutations**:
  - `createProduct(input: ProductRequest!)` - Create product
  - `updateProduct(id: ID!, input: ProductRequest!)` - Update product
  - `deleteProduct(id: ID!)` - Soft delete product
- **Queries**:
  - `products(status: ProductStatus!)` - List products filtered by required status
  - `activeProducts` - Get only active products
  - `product(id: ID!)` - Get single product
  - `searchProducts(name: String!)` - Search by name

## Features

- ✅ **Dual API Support**: Both REST and GraphQL endpoints
- ✅ **Data Validation**: Comprehensive input validation
- ✅ **Error Handling**: Custom exceptions with proper HTTP/GraphQL responses
- ✅ **Database Persistence**: JPA with H2 in-memory database
- ✅ **Soft Delete**: Products marked as DELETED instead of removed
- ✅ **Search Functionality**: Case-insensitive name search
- ✅ **Status Management**: ACTIVE, BLOCKED, DELETED product states
- ✅ **API Documentation**: OpenAPI/Swagger for REST endpoints
- ✅ **Uniqueness Validation**: Prevents duplicate product names

## Recommendations for Testing the App

Before using the APIs, follow these detailed steps to set up and test the application properly. This ensures a smooth experience and helps you understand the full functionality.

1. **Clone the Repository**:
   - Download the project from GitHub:
     ```bash
     git clone https://github.com/francisgf/springboot-graphql-api.git
     cd springboot-graphql-api
     ```
   - This gives you the complete source code and configuration.

2. **Ensure Java 17 is Installed and Configured**:
   - Check your Java version:
     ```bash
     java -version
     ```
     - Expected output: Java 17.x.x (e.g., "openjdk version '17.0.8'")
   - If not installed, download from [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or [Adoptium](https://adoptium.net/).
   - Configure your IDE (e.g., IntelliJ IDEA, Eclipse, or VS Code) to use JDK 17 for this project. In IntelliJ: File > Project Structure > Project SDK > Select JDK 17.

3. **Update Dependencies with Maven**:
   - Resolve and download all project dependencies:
     ```bash
     mvn dependency:resolve
     ```
   - This ensures all libraries (Spring Boot, GraphQL, H2, etc.) are available locally.

4. **Compile and Build the Project**:
   - Clean any previous builds and compile the source code:
     ```bash
     mvn clean compile
     ```
   - Build the JAR file for running:
     ```bash
     mvn package
     ```
   - If compilation fails, check for Java version issues or missing dependencies.

5. **Run the Application and Start Testing**:
   - Launch the Spring Boot application:
     ```bash
     mvn spring-boot:run
     ```
   - The app will start on `http://localhost:8080`. Look for "Started SpringbootGraphqlApiApplication" in the console.
   - **Important**: Begin by creating a product to have data for queries. Use GraphQL (via GraphiQL at `http://localhost:8080/graphiql`) or REST API to add products first, then test queries and other operations.

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Running the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Accessing APIs
- **GraphQL Playground**: `http://localhost:8080/graphiql`
- **REST API Docs**: `http://localhost:8080/swagger-ui.html`
- **H2 Console**: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)

### Sample GraphQL Mutations and Queries 

#### Mutations

**Create Product**
```graphql
mutation {
  createProduct(input: {
    name: "New Product"
    description: "Product description"
    price: 29.99
    stock: 100
    status: ACTIVE
  }) {
    id
    name
    description
    price
    stock
    status
  }
}
```

**Update Product**
```graphql
mutation {
  updateProduct(id: "1", input: {
    name: "Updated Product"
    description: "Updated description"
    price: 39.99
    stock: 50
    status: BLOCKED
  }) {
    id
    name
    description
    price
    stock
    status
  }
}
```

**Delete Product**
```graphql
mutation {
  deleteProduct(id: "1")
}
```

#### Queries

**Get All Products** (Note: `status` is now required)
```graphql
query {
  products(status: ACTIVE) {
    id
    name
    description
    price
    stock
    status
    createdAt
    updatedAt
  }
}
```

**Get Products by Status** (Example with BLOCKED status)
```graphql
query {
  products(status: BLOCKED) {
    id
    name
    description
    price
    stock
    status
  }
}
```

**Get Active Products**
```graphql
query {
  activeProducts {
    id
    name
    description
    price
    stock
    status
  }
}
```

**Get Single Product**
```graphql
query {
  product(id: "1") {
    id
    name
    description
    price
    stock
    status
    createdAt
    updatedAt
  }
}
```

**Search Products**
```graphql
query {
  searchProducts(name: "Product") {
    id
    name
    description
    price
    stock
    status
  }
}
```

### Sample REST Request
```bash
curl -X GET http://localhost:8080/api/products
```

## Project Structure
```
src/
├── main/java/com/giuliosmtech/products/
│   ├── controller/          # REST and GraphQL controllers
│   ├── service/             # Business logic
│   ├── repository/          # Data access layer
│   ├── entity/              # JPA entities
│   ├── dto/                 # Data transfer objects
│   ├── enums/               # Product status enum
│   ├── exceptions/          # Custom exceptions and handlers
│   └── config/              # OpenAPI configuration
├── main/resources/
│   ├── graphql/schema.graphqls  # GraphQL schema
│   └── application.properties   # App configuration
└── test/                      # Unit tests
```

## Development Notes

- **GraphQL Schema**: Defined in `src/main/resources/graphql/schema.graphqls`
- **Database**: H2 in-memory, data persists during runtime
- **Validation**: Uses Bean Validation annotations
- **Error Handling**: Separate handlers for REST and GraphQL
- **Soft Delete**: Products are marked DELETED, not physically removed

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is licensed under the MIT License.
