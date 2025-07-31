
# POC - WebFlux + MongoDB 기반 Reactive User Management API

결과 : WebFlux를 통한 MongoDB 연동 및 시행이 가능합니다 

### 시행 착오 
- 제가 docker-compose.yml에서 poc_mongo-data 볼륨에서 설정을 잘못했어서, 볼륨을 삭제한 후  docker pull mongo-express로
  Docker Hub에서 새로운 이미지를 받고 컴포즈를 재실행하니 문제는 해결되었습니다.

  하지만, 확인을 해보니 현재 저의 docker-compose.yml에 정의된 mongo-express 버전은 문제가 없는 것으로 보입니다만,
  제가 테스팅을 하다가 버전이 꼬여서 문제가 되었던 것 같습니다.  


## 프로젝트 개요

이 프로젝트는 Spring WebFlux와 MongoDB Reactive Driver를 활용한 비동기 논블로킹 REST API 기반의 사용자 관리 시스템을 구현한 POC(Proof of Concept)입니다.  
리액티브 프로그래밍을 적용해 고성능, 확장성 있는 웹 애플리케이션의 기본 구조를 탐색하고자 합니다.

---

## 주요 기능
우선은 테스트 용 CRUD로 유저를 생성, 조회, 수정, 삭제 기능을 테스팅 해봤습니다.  
정상적으로 작성이 됩니다.
데이터 베이스에 접근하고자 할때, 다음과 같이 진행하면 되겠습니다.

<img width="796" height="649" alt="image" src="https://github.com/user-attachments/assets/ec7c04c1-dff9-41be-a0e8-2d1e558bde0a" />



- 사용자(User) CRUD API 제공
    - 전체 사용자 조회 (`GET /users`)
    - 신규 사용자 생성 (`POST /users`)
    - 기존 사용자 수정 (`PUT /users/{id}`)
    - 사용자 삭제 (`DELETE /users/{id}`)
- MongoDB를 비동기 방식으로 연동하여 높은 처리량과 낮은 지연시간 달성
- Docker Compose로 MongoDB 및 Mongo Express 관리 및 시각화 지원

---

## 기술 스택

- Java 21
- Spring Boot 3.4.0
- Spring WebFlux
- Spring Data MongoDB Reactive
- Lombok
- Docker, Docker Compose (MongoDB, Mongo Express)

---



---

## 환경 설정

### MongoDB 연결 정보 (`src/main/resources/application.properties`)

```properties
spring.application.name=poc
spring.data.mongodb.uri=mongodb://root:1234@localhost:27017/admin
spring.data.mongodb.database=admin
````

### Docker Compose 설정 (`docker-compose.yml`)

```yaml
services:
  mongo:
    image: mongo:7.0
    container_name: mongo
    restart: always
    ports:
      - "27017:27017"
    environment:
      MONGO_INITDB_ROOT_USERNAME: root
      MONGO_INITDB_ROOT_PASSWORD: 1234
    volumes:
      - mongo-data:/data/db

  mongo-express:
    image: mongo-express:1.0.2-20
    container_name: mongo-express
    restart: always
    ports:
      - "8081:8081"
    environment:
      ME_CONFIG_MONGODB_ADMINUSERNAME: root
      ME_CONFIG_MONGODB_ADMINPASSWORD: 1234
      ME_CONFIG_MONGODB_SERVER: mongo

volumes:
  mongo-data:
```

---

## 빌드 및 실행 방법

### 1. Docker Compose로 MongoDB 및 Mongo Express 실행

```bash
docker-compose up -d
```

Mongo Express 웹 UI는 [http://localhost:8081](http://localhost:8081) 에서 접근 가능하다 하지만,
아직은 잘 작동이 안합니다!  
제가 조금 더 연구해 보겠습니다.
> 성열
 
---

### 2. Spring Boot 애플리케이션 빌드 및 실행

```bash
./gradlew clean build
java -jar build/libs/poc-0.0.1-SNAPSHOT.jar
```

애플리케이션 기본 포트: `8080`

---

## API 사용 예시

PostMan을 기준으로 설명을 드리겠습니다.

### 1. 유저 생성
GET http://localhost:8080/users

#### 응답
```
[
  {
    "id": "xxxx",
    "name": "홍길동",
    "age": 30
  }
]

```

---- 

### 2. 유저 생성
POST http://localhost:8080/users

#### 요청 Body
```
{
  "name": "Alice",
  "age": 25
}

```
-----
### 3. 유저 수정
PUT http://localhost:8080/users/{id}

#### 요청 Body
```
{
  "name": "Alice Updated",
  "age": 27
}

```
------

### 4. 유저 삭제
DELETE http://localhost:8080/users/{id}

#### 요청 & 반환값 없음

