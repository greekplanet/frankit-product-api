# 상품 및 옵션 관리 API 설계
## 1. 개요
상품 및 옵션 관리 RESTful API를 개발합니다. 사용자는 로그인 후 JWT 토큰을 통해 인증되며, 상품과 옵션의 CRUD 기능을 사용할 수 있습니다.<br> 페이징, 다양한 옵션 타입(입력/선택), 그리고 최대 3개의 옵션 제한을 포함합니다.
## 2. API 목록

### 2.1 인증 API

| 메서드  |엔드포인트|설명|요청 바디|응답(성공)|응답 코드|
|------|---|---|---|-------------------------------------------------------------------------------------------------------------------|---|
| POST |/api/auth/login|사용자 로그인 및 JWT 발급|{<br> "email": "user@example.com",<br> "password": "pass123"<br>}<br>| <br>{<br> "status": 200,<br> "success": true,<br> "data": {<br> "token": "eyJhbGciOiJIUzI1NiJ9..."<br> }<br>}<br> |200 (성공)<br>400 (잘못된 요청)<br>401 (인증 실패)|

### 2.2 상품 관리 API

| 메서드    |엔드포인트|설명| 요청 바디 / 쿼리 파라미터                                                                                               | 응답(성공)                                                                                                                                                                                                               |응답 코드|
|--------|---|---|---------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---|
| POST   |/api/products|상품 등록| <br>{<br> "name": "노트북",<br> "description": "고성능 노트북",<br> "price": 1500000,<br> "shippingFee": 3000<br>}<br> | <br>{<br>"status": 201,<br>"success": true,<br>"data": {<br>"id": 1,<br>"name": "노트북",<br>"description": "고성능 노트북",<br>"price": 1500000,<br>"shippingFee": 3000,<br>"createdAt": "2025-06-03T18:13:00"<br>}<br>}     |201 (생성)<br>400 (잘못된 요청)<br>401 (미인증)|
| PUT    |/api/products/{id}|상품 수정| <br>{<br>"name": "노트북 Pro",<br>"description": "업그레이드된 노트북",<br>"price": 1800000,<br>"shippingFee": 3500<br>}  | <br>{<br>"status": 200,<br>"success": true,<br>"data": {<br>"id": 1,<br>"name": "노트북 Pro",<br>"description": "업그레이드된 노트북",<br>"price": 1800000,<br>"shippingFee": 3500,<br>"createdAt": "2025-06-03T18:13:00"<br>}<br>} |200 (성공)<br>400 (잘못된 요청)<br>401 (미인증)<br>404 (상품 없음)|
| DELETE |/api/products/{id}|상품 삭제| -                                                                                                             |<br>{<br>"status": 200,<br>"success": true,<br>"data": {<br>"message": "상품이 삭제되었습니다"<br>}<br>}<br>|200 (성공)<br>401 (미인증)<br>404 (상품 없음)|
| GET    |/api/products/{id}|상품 단건 조회| -                                                                                                             |<br>{<br>"status": 200,<br>"success": true,<br>"data": {<br>"id": 1,<br>"name": "노트북",<br>"description": "고성능 노트북",<br>"price": 1500000,<br>"shippingFee": 3000,<br>"createdAt": "2025-06-03T18:13:00",<br>"options": []<br>}<br>}|200 (성공)<br>401 (미인증)<br>404 (상품 없음)|
| GET    |/api/products|상품 목록 조회 (페이징)| ?page=0&size=10&sort=createdAt,desc                                                                           |<br>{<br>"status": 200,<br>"success": true,<br>"data": {<br>"content": [<br>{<br>"id": 1,<br>"name": "노트북",<br>"description": "고성능 노트북",<br>"price": 1500000,<br>"shippingFee": 3000,<br>"createdAt": "2025-06-03T18:13:00"<br>}<br>],<br>"page": {<br>"number": 0,<br>"size": 10,<br>"totalElements": 1,<br>"totalPages": 1<br>}<br>}<br>}<br>|200 (성공)<br>401 (미인증)|

### 2.3 상품 옵션 관리 API

| 메서드    |엔드포인트|설명| 요청 바디                                                                                                         | 응답(성공)                                                                                                                                                                                                               |응답 코드|
|--------|---|---|---------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---|
|POST|/api/products/{productId}/options|옵션 등록|<br>{<br> "name": "색상",<br> "type": "SELECT",<br> "values": ["빨강", "파랑"],<br> "additionalPrice": 1000<br>}<br>|<br>{<br> "status": 201,<br> "success": true,<br> "data": {<br> "id": 1,<br> "name": "색상",<br> "type": "SELECT",<br> "values": ["빨강", "파랑"],<br> "additionalPrice": 1000<br> }<br>}<br>|201 (생성)<br>400 (잘못된 요청)<br>401 (미인증)<br>404 (상품 없음)<br>409 (옵션 3개 초과)|
|PUT|/api/products/{productId}/options/{optionId}|옵션 수정|<br>{<br> "name": "색상",<br> "type": "SELECT",<br> "values": ["빨강", "파랑", "초록"],<br> "additionalPrice": 1500<br>}<br>|<br>{<br> "status": 200,<br> "success": true,<br> "data": {<br> "id": 1,<br> "name": "색상",<br> "type": "SELECT",<br> "values": ["빨강", "파랑", "초록"],<br> "additionalPrice": 1500<br> }<br>}<br>|200 (성공)<br>400 (잘못된 요청)<br>401 (미인증)<br>404 (상품/옵션 없음)|
|DELETE|/api/products/{productId}/options/{optionId}|옵션 삭제|-|<br>{<br> "status": 200,<br> "success": true,<br> "data": {<br> "message": "옵션이 삭제되었습니다"<br> }<br>}<br>|200 (성공)<br>401 (미인증)<br>404 (상품/옵션 없음)|
|GET|/api/products/{productId}/options|옵션 목록 조회|-|<br>{<br> "status": 200,<br> "success": true,<br> "data": [<br> {<br> "id": 1,<br> "name": "색상",<br> "type": "SELECT",<br> "values": ["빨강", "파랑"],<br> "additionalPrice": 1000<br> }<br> ]<br>}<br>|200 (성공)<br>401 (미인증)<br>404 (상품 없음)|

