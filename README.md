# 상품 및 옵션관리 API
## 지원자: 김화진

### 프로젝트 개요
프랜킷 백엔드 개발자 사전과제: Spring Boot 기반 상품 및 옵션관리 API.

### 기술 스택
- Java 23
- Spring Boot 3.5.0
- Gradle
- JPA
- H2 Database (개발용)

### 설계 문서
- [API 설계 문서](./docs/api-reference.md)
- [시스템 아키텍처 설계](./docs/archi-design.md)
- [ERD 설계](./docs/erd.md)
- [시퀀스 다이어그램 작성](./docs/sequence-diagram.md)

### Swagger API
- [Swagger API 문서](./docs/swagger-api.md)
- Mock 모드 실행 <br> 
  `./gradlew bootRun --args='--spring.profiles.active=mock'`

### 빌드 및 실행
1. JDK 23 설치
2. `./gradlew build`
3. `./gradlew bootRun`
