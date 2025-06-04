# 상품 및 옵션 관리 API 시퀀스 다이어그램

## 1. 로그인 API (POST /api/auth/login)
- UserService가 UserRepository를 통해 사용자 조회 후 비밀번호 검증, JWT 생성.
```mermaid
sequenceDiagram
    actor Client
    participant Controller as AuthController
    participant Service as UserService
    participant Repository as UserRepository
    participant DB as Database

    Client->>Controller: POST /api/auth/login {email, password}
    Controller->>Service: login(LoginRequest)
    Service->>Repository: findByEmail(email)
    Repository->>DB: SELECT * FROM user WHERE email=?
    DB-->>Repository: User
    Repository-->>Service: User
    Service->>Service: validatePassword(password)
    Service->>Service: generateJwtToken()
    Service-->>Controller: LoginResponse(token)
    Controller-->>Client: 200 {status, success, data: {token}}
```

## 2. 상품 등록 API (POST /api/products)
- ProductService가 DTO를 엔티티로 변환, ProductRepository로 DB 저장.
```mermaid
sequenceDiagram
    actor Client
    participant Controller as ProductController
    participant Service as ProductService
    participant Repository as ProductRepository
    participant DB as Database

    Client->>Controller: POST /api/products {name, description, price, shippingFee}
    Controller->>Service: createProduct(ProductRequest)
    Service->>Service: convertToEntity(request)
    Service->>Repository: save(Product)
    Repository->>DB: INSERT INTO product (...)
    DB-->>Repository: Product
    Repository-->>Service: Product
    Service-->>Controller: ProductResponse
    Controller-->>Client: 201 {status, success, data: Product}
```

## 3. 옵션 등록 API (POST /api/products/{productId}/options)
- OptionService가 ProductService를 통해 상품 조회, 옵션 제한(3개) 확인, OptionRepository로 저장.
```mermaid
sequenceDiagram
    actor Client
    participant Controller as OptionController
    participant OptionService as OptionService
    participant ProductService as ProductService
    participant ProductRepository as ProductRepository
    participant OptionRepository as OptionRepository
    participant DB as Database

    Client->>Controller: POST /api/products/{productId}/options {name, type, values, additionalPrice}
    Controller->>OptionService: createOption(productId, OptionRequest)
    OptionService->>ProductService: findProduct(productId)
    ProductService->>ProductRepository: findById(productId)
    ProductRepository->>DB: SELECT * FROM product WHERE id=?
    DB-->>ProductRepository: Product
    ProductRepository-->>ProductService: Product
    ProductService-->>OptionService: Product
    OptionService->>OptionService: validateOptionLimit(Product)
    OptionService->>OptionService: convertToEntity(request)
    OptionService->>OptionRepository: save(Option)
    OptionRepository->>DB: INSERT INTO option (...)
    DB-->>OptionRepository: Option
    OptionRepository-->>OptionService: Option
    OptionService-->>Controller: OptionResponse
    Controller-->>Client: 201 {status, success, data: Option}
```

