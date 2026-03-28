 # REST API Documentation

  Base URL: `http://localhost:8080`

  세션 기반 인증을 사용합니다.
  로그인 성공 후 발급되는 세션(`JSESSIONID`)을 통해 인증 상태를 유지합니다.

  ## 공통 응답 형식

  ### 에러 응답
  ```json
  {
    "status": 400,
    "message": "에러 메시지"
  }
```
  ---

  # Member API

  ## 1. 회원가입

  ### Request

  - Method: POST
  - URI: /members/signup

  ### Request Body
```json
  {
    "loginId": "newuser",
    "password": "Password1!",
    "passwordConfirm": "Password1!",
    "name": "홍길동"
  }
```
  ### Response

  - Status: 201 Created
```json
  {
    "loginId": "ghkdtjdgus",
    "memberId": 4,
    "name": "황성현",
    "role": "ROLE_USER"
  }
```
  ### 예외

  - 409 Conflict : 이미 사용 중인 아이디
  - 400 Bad Request : 비밀번호 확인 불일치
  - 400 Bad Request : 유효성 검증 실패
  - 400 Bad Request : 요청 본문 형식 오류

---

  ## 2. 로그인

  ### Request

  - Method: POST
  - URI: /members/login

  ### Request Body
```json
  {
    "loginId": "ghkd5370",
    "password": "tjdgus1005!"
  }
```
  ### Response

  - Status: 200 OK
```json
  {
    "loginId": "ghkd5370",
    "memberId": 1,
    "role": "ROLE_USER"
  }
```
  ### 예외

  - 401 Unauthorized : 아이디 또는 비밀번호 불일치
  - 400 Bad Request : 유효성 검증 실패
  - 400 Bad Request : 요청 본문 형식 오류

---

  ## 3. 로그아웃

  ### Request

  - Method: POST
  - URI: /members/logout

  ### Response

  - Status: 200 OK

  로그아웃은 성공 시 별도의 응답 본문 없이 처리됩니다.

---

  # Content API

  ## 1. 콘텐츠 생성

  ### Request

  - Method: POST
  - URI: /contents
  - 인증 필요: O

  ### Request Body
```json
  {
    "title": "테스트 글",
    "description": "테스트 내용"
  }
```
  ### Response

  - Status: 201 Created
```json
  {
    "contentId": 1,
    "createdBy": "ghkd5370",
    "createdDate": "2026-03-28T19:11:40.0992811",
    "description": "테스트 내용",
    "lastModifiedBy": null,
    "lastModifiedDate": null,
    "title": "테스트 글",
    "viewCount": 0
  }
```
  ### 예외

  - 401 Unauthorized : 로그인 필요
  - 400 Bad Request : 제목 누락 또는 유효성 검증 실패
  - 400 Bad Request : 요청 본문 형식 오류

---

  ## 2. 콘텐츠 목록 조회

  ### Request

  - Method: GET
  - URI: /contents?page=0&size=10&sort=contentId,desc
  - 인증 필요: X

  ### Response

  - Status: 200 OK
```json
  {
    "content": [
      {
        "contentId": 1,
        "title": "첫 번째 콘텐츠",
        "description": "콘텐츠 내용입니다.",
        "viewCount": 0,
        "createdDate": "2026-03-29T12:00:00",
        "createdBy": "ghkd5370",
        "lastModifiedDate": null,
        "lastModifiedBy": null
      }
    ],
    "pageable": {},
    "totalElements": 1,
    "totalPages": 1,
    "size": 10,
    "number": 0
  }
```
---

  ## 3. 콘텐츠 상세 조회

  ### Request

  - Method: GET
  - URI: /contents/{contentId}
  - 인증 필요: X

  ### Response

  - Status: 200 OK
```json
  {
    "contentId": 1,
    "title": "첫 번째 콘텐츠",
    "description": "콘텐츠 내용입니다.",
    "viewCount": 1,
    "createdDate": "2026-03-29T12:00:00",
    "createdBy": "ghkd5370",
    "lastModifiedDate": null,
    "lastModifiedBy": null
  }
```
  ### 조회수 정책

  - 비로그인 사용자 조회 시 조회수 증가
  - 로그인한 다른 사용자 조회 시 조회수 증가
  - 작성자 본인 조회 시 조회수 증가하지 않음

  ### 예외

  - 404 Not Found : 존재하지 않는 콘텐츠
  - 410 Gone : 삭제된 콘텐츠

---

  ## 4. 콘텐츠 수정

  ### Request

  - Method: PATCH
  - URI: /contents/{contentId}
  - 인증 필요: O

  ### Request Body
```json
  {
    "title": "수정된 제목",
    "description": "수정된 내용"
  }
```
  ### Response

  - Status: 200 OK
```json
  {
    "contentId": 1,
    "title": "수정된 제목",
    "description": "수정된 내용",
    "viewCount": 3,
    "createdDate": "2026-03-29T12:00:00",
    "createdBy": "ghkd5370",
    "lastModifiedDate": "2026-03-29T13:10:00",
    "lastModifiedBy": "ghkd5370"
  }
```
  ### 예외

  - 401 Unauthorized : 로그인 필요
  - 403 Forbidden : 작성자 또는 관리자 권한 없음
  - 404 Not Found : 존재하지 않는 콘텐츠
  - 410 Gone : 삭제된 콘텐츠
  - 400 Bad Request : 유효성 검증 실패
  - 400 Bad Request : 요청 본문 형식 오류

---

  ## 5. 콘텐츠 삭제

  ### Request

  - Method: DELETE
  - URI: /contents/{contentId}
  - 인증 필요: O

  ### Response

  - Status: 200 OK
```json
  {
    "contentId": 1,
    "message": "콘텐츠가 삭제되었습니다."
  }
```
  ### 예외

  - 401 Unauthorized : 로그인 필요
  - 403 Forbidden : 작성자 또는 관리자 권한 없음
  - 404 Not Found : 존재하지 않는 콘텐츠
  - 410 Gone : 이미 삭제된 콘텐츠

---

  # 권한 정책

  ## USER

  - 콘텐츠 생성 가능
  - 본인이 작성한 콘텐츠만 수정/삭제 가능

  ## ADMIN

  - 모든 콘텐츠 수정/삭제 가능

---

  # 테스트 계정

  ## 일반 사용자
```json
  {
    "loginId": "ghkd5370",
    "password": "tjdgus1005!"
  }
```
  ## 관리자
```json
  {
    "loginId": "admin",
    "password": "admin123"
  }
```
  ## 일반 사용자 2
```json
  {
    "loginId": "ghkdghkd",
    "password": "tjdgus1005!"
  }
```
