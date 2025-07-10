# Spring Boot REST API Project

## 프로젝트 개요

이 프로젝트는 Spring Boot를 사용하여 RESTful API를 구축한 예제입니다. 주요 기능은 사용자와 프로젝트 데이터를 관리하고, 페이징 및 정렬 기능을 제공합니다.

## 주요 기능

- **사용자 API**

  - `/users`: 사용자 데이터를 조회합니다.
  - 쿼리 매개변수:
    - `count`: 반환할 데이터 개수 (기본값: 10)
    - `page`: 페이지 번호 (기본값: 1)
    - `sort`: 정렬 기준 필드 (예: `id`, `name`)
    - `order`: 정렬 순서 (`asc` 또는 `desc`)

- **프로젝트 API**

  - `/projects`: 프로젝트 데이터를 조회합니다.
  - 쿼리 매개변수:
    - `count`: 반환할 데이터 개수 (기본값: 10)
    - `page`: 페이지 번호 (기본값: 1)
    - `sort`: 정렬 기준 필드 (`id`, `name`만 가능)
    - `order`: 정렬 순서 (`asc` 또는 `desc`)

- **헬스 체크 API**

  - `/health`: 애플리케이션 상태를 확인합니다.

- **오류 처리 API**
  - `/error`: 정의되지 않은 경로로 접근 시 오류 정보를 반환합니다.

## 설치 및 실행

1. **Java 17 설치**

   - 프로젝트는 Java 17을 필요로 합니다.

2. **의존성 설치**

   ```bash
   ./gradlew build
   ```

3. **애플리케이션 실행**

   ```bash
   ./gradlew bootRun
   ```

4. **API 테스트**
   - 브라우저 또는 Postman을 사용하여 API를 테스트합니다.

## 참고 자료

- [Spring Boot 공식 문서](https://spring.io/projects/spring-boot)
- [Gradle 공식 문서](https://docs.gradle.org)

## 디렉터리 구조

```
src/
├── main/
│   ├── java/
│   │   └── com/example/be_spring_test/
│   │       ├── controller/
│   │       │   ├── UsersController.java
│   │       │   └── ProjectsController.java
│   │       └── BeSpringTestApplication.java
│   ├── resources/
│   │   ├── data/
│   │   │   ├── users.json
│   │   │   └── projects.json
│   │   ├── application.properties
│   │   └── static/
│   └── templates/
└── test/
    ├── java/
    │   └── com/example/be_spring_test/
    │       └── BeSpringTestApplicationTests.java
```
