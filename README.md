# Sip & Savor
<img width="300" alt="sip and savor" src="https://github.com/user-attachments/assets/430303a1-2a88-4a94-8005-f89415f34745" />

위스키 조회 및 리뷰를 위한 웹 사이트


## 1. 개발 목적
나의 시음평을 기록하고 다른 사람과 공유하기 위한 커뮤니티 개발

## 2. 팀원 소개
|팀장|팀원|
|---|---|
|박선재|박한별|
|[🔗깃허브 링크](https://github.com/StandingAsh)|[🔗깃허브 링크](https://github.com/phb0413)|
|회원 기능 구현 및 DB 설계|위스키 및 게시판 기능구현|


## 3. DB 설계
<img width="700" alt="flowchart" src="https://github.com/user-attachments/assets/12cd9f67-e128-476f-9018-f575510330a2" />


## 4. 주요 기능
- 회원 가입, 로그인
  - 회원 가입 시 DB에 유저정보 등록
  - Spring Security 활용 로그인 기능 구현

- 위스키 정보
  - 크롤링한 위스키 정보로 테이블 만들어서 위스키 리스트 구현
  - 검색 기능 및 카테고리 선택으로 필터링 가능
  - 위스키 클릭 시 그 위스키에 대한 상세정보 제공 
 
- 게시판
  - 위스키 상세정보 밑에 그 위스키에 대한 게시글 보이게 구현
  - 로그인 되있을 시 자신이 작성한 게시글만 볼 수 있도록 필터링 구현
  - 게시글 페이징 처리 구현
  - 게시글 클릭 시 게시글에 대한 상세정보와 게시글 수정 및 삭제 기능 구현


### 메인페이지

<img width="800" alt="image" src="https://github.com/user-attachments/assets/c309a8ce-4d4c-4db4-8d9e-4e42e79cbab7" />

### 로그인 및 회원가입

<img width="800" alt="image" src="https://github.com/user-attachments/assets/4edd9f19-a215-4dc6-b9b9-27908deee27d" />

<img width="800" alt="image" src="https://github.com/user-attachments/assets/6f98b4b2-9bb6-4b0c-b0c5-199a9ac4e253" />

### 위스키 검색 및 리뷰 작성

<img width="800" alt="image" src="https://github.com/user-attachments/assets/891e4d96-5ac2-477f-8f63-9e44985132f8" />

<img width="800" alt="image" src="https://github.com/user-attachments/assets/bd353117-9386-4fc4-b9a5-4e0c0af0b376" />

### 마이페이지 - 내 리뷰 조회

<img width="800" alt="image" src="https://github.com/user-attachments/assets/eddd48df-78c8-4dd0-806a-87acd0ec05a1" />
  


## 5. 기술 스택
5.1 Language & Framework

|||
|---|---|
|HTML5|<img width="50" height="50" alt="image" src="https://github.com/user-attachments/assets/525fbc58-d361-4306-8c39-5cf9138f6306" />|
|CSS3|<img width="50" height="50" alt="image" src="https://github.com/user-attachments/assets/1ebc7681-ad96-46a1-a7d7-2e51bf6c7202" />|
|Java|<img width="50" height="50" alt="image" src="https://github.com/user-attachments/assets/5cb2f518-1692-4bdf-84e7-2269c9429907" />|
|Spring Boot|<img width="50" height="50" alt="image" src="https://github.com/user-attachments/assets/b1d7cb31-be64-428a-8788-a0d6b15ded90" />|
|Thymeleaf|<img width="50" height="50" alt="image" src="https://github.com/user-attachments/assets/5c11e59f-2433-467d-9475-17f3598465f0" />|

5.2 Distribution
|||
|---|---|
|ec2|<img width="50" height="50" alt="image" src="https://github.com/user-attachments/assets/7e079a1b-fc84-4534-8777-66038dbb8fae" />|
|RDS|<img width="50" height="50" alt="image" src="https://github.com/user-attachments/assets/aabb7394-b3f8-4750-95c7-f39afd9e19f0" />|

5.3 Database
|||
|---|---|
|MySQL|<img width="50" height="50" alt="image" src="https://github.com/user-attachments/assets/23c1c7b5-c21d-4e24-ad02-5cab86899243" />|

<img width="500" height="500" alt="flowchart" src="https://github.com/user-attachments/assets/e3992616-233b-4858-a10f-a1af1ba0e6f0" />




