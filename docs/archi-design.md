# 시스템 아키텍처 설계
확장 가능한 코드 구성을 위해 DDD(Domain-Driven Design)와 클린 아키텍처를 적용합니다.
```
com.frankit.productapi
├── application
│   ├── dto
│   │   ├── request
│   │   │   ├── LoginRequest.java
│   │   │   ├── ProductRequest.java
│   │   │   └── OptionRequest.java
│   │   └── response
│   │       ├── LoginResponse.java
│   │       ├── ProductResponse.java
│   │       └── OptionResponse.java
│   └── controller
│       ├── AuthController.java
│       ├── ProductController.java
│       └── OptionController.java
├── domain
│   ├── user
│   │   ├── User.java
│   │   ├── UserRepository.java
│   │   └── UserService.java
│   ├── product
│   │   ├── Product.java
│   │   ├── ProductRepository.java
│   │   └── ProductService.java
│   └── option
│       ├── Option.java
│       ├── OptionRepository.java
│       └── OptionService.java
├── infrastructure
│   ├── config
│   │   ├── JwtConfig.java
│   │   └── SecurityConfig.java
│   └── persistence
│       ├── UserRepositoryImpl.java    
│       ├── ProductRepositoryImpl.java 
│       ├── OptionRepositoryImpl.java  
│       ├── JpaUserRepository.java     
│       ├── JpaProductRepository.java  
│       └── JpaOptionRepository.java   
└── ProductApiApplication.java
```