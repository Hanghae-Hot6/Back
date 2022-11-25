![logo](https://user-images.githubusercontent.com/113868313/203928998-224f3d9f-9ec1-4421-abc9-978c6a4fbed9.png)

## 👉🏻 프로젝트 소개

>  독서가 부족한 현대인들을 위해! 갓생을 살고싶은 현대인들을 위해! <br>
>  새로운 사람들과 즐겁게 얘기하며 독서 할 수 있는 웹 어플리케이션 <br>

### [ODOK 바로가기](http://hot6-front.s3-website.ap-northeast-2.amazonaws.com/ )
### [Front-End Github](https://github.com/Hanghae-Hot6/Front)
### [Back-End Github](https://github.com/Hanghae-Hot6/Back)
### [Project Notion](https://www.notion.so/ckd12394/5-ODOK-5b66f278ac9044609f709d7a89deee7f)
<br>
<hr>
<br>

## 👨‍👩‍👧 팀원 소개

|이름|깃허브 주소|포지션|
|---|---|---|
|서지운|[MildColor의 github](https://github.com/MildColor)|Frontend|
|조재신|[1005jsc의 github](https://github.com/1005jsc)|Frontend|
|국경훈|[kyunghoonkook의 github](https://github.com/kyunghoonkook)|Frontend|
|류창민|[ryucm의 github](https://github.com/ryucm)|Backend|
|조계일|[chokyeil의 github](https://github.com/chokyeil)|Backend|
|박현도|[atto08의 github](https://github.com/atto08)|Backend|
|장승주||DESIGNER|


<br>
<hr>
<br>


## ⚙️ 서비스 아키텍처
![서비스아키텍쳐ver_2](https://user-images.githubusercontent.com/113868313/203928554-51132feb-8af7-4d71-9a89-394840b51b5b.png)


<br>

<br>

## 📅 프로젝트 기간

기간 : 2022년 11월 03일 ~ 2022년 12월 16일(6주)

<br>

<br>

## 🛠 기술 스택

<div align=center> 
<img  src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
<img  src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white">
<img  src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">
<img  src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white">
<img  src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-badge&logo=S&logoColor=white"> 
<img  src="https://img.shields.io/badge/Amazon RDS-527FFF?style=for-the-badge&logo=Amazon RDS&logoColor=white">
<img  src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white">
<img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white">
<img  src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white">
<img src="https://img.shields.io/badge/socket.io-010101?style=for-the-badge&logo=socket.io&logoColor=white">
<img src="https://img.shields.io/badge/Stomp-353535?style=for-the-badge&logoColor=white">
<img src="https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=Axios&logoColor=white">

<br> 

  <br>



  <br>
</div>

<br>

<br> 

## 🗺 API 설계

<details>
<summary>API</summary>
<div markdown="1">   

![image](https://user-images.githubusercontent.com/113868313/203936279-6962e53b-776e-454c-a077-f79c57c022e7.png)
![image](https://user-images.githubusercontent.com/113868313/203936319-c932153e-2c61-4327-b065-deb6e03bdc01.png)
![image](https://user-images.githubusercontent.com/113868313/203936355-5d833550-0cba-4213-8064-bf55cb766fa6.png)
![image](https://user-images.githubusercontent.com/113868313/203936394-9a2d9cee-5677-4344-aa54-6272748dd76b.png)
![image](https://user-images.githubusercontent.com/113868313/203936442-f38ec82a-195c-47c2-b78b-b859d0058216.png)

</div>
</details>


<br>

<br>

## ✒ 와이어 프레임

### [Figma 보러가기](https://www.figma.com/file/PTN0SpVnreH7JxRmfFmA32/%ED%95%AD%ED%95%B499_%EC%98%A4%EB%8F%85?node-id=0%3A1&t=e7SJsG8aMZCz08ou-0)

<br>
<br>

## 🔨 기술 도입 이유 - 라이브러리, 기술적 의사 결정
| 스택| 도입 이유 |버전|
|--|--|--|
| Node.js | 메모리 사용과 관련하여 python, java 보다 효율적이며, jQuery를 통해 원하는 데이터를 추출하기에 용이하기 때문에 선택하였습니다. |^4.4.2|
| Redis | 1) RefreshToken 저장, 2) email 인증 번호 유효 기간 설정  |^5.3.6
| docker-compose | 각 사용자마다 개발환경이 다릅니다. 개별 환경마다 별도의 DB를 설치가 필요 => redis와 postgresql의 별도 설치 없이 동일 환경에서 test가 가능하도록 docker-compose를 구성 |^3.39.2
| CI-CD | JS 내장 기능이 아니기 때문에 import를 해줘야 하는 번거로움과 무거움이 다소 있지만 다음과 같은 편리한 기능 때문에 도입했다. JSON 데이터 자동 변환 / axios interceptor & 간결한 instance 기능 |^1.1.3
| WebSocket, Stomp |채팅 기능을 구현하기 위해서는 단방향적인 http통신을 쓰는 것보다 양방향적으로 통신을 가능하게하는 라이브러리가 필요로 했다. 여러가지 양방향 통신 라이브러리중, Spring 서버의 성격과 가장 잘 맞은 SockJs, Stomp라이브러리를 사용하였다 |^6.1.2
| spring email | 회원가입 시 본인 인증 필요 아이디, 비밀번호 찾기 |^4.4.2|
| OAuth2.0 | 회원 가입의 간편화를 통해 유저들의 사용 편리함 증가 |^4.4.2|
| PostgreSQL | 대용량 데이터 기반 서비스를 구축 하기 위해서. mysql의 버전 업그레이드를 통해 많이 성능이 향상되었지만, 그래도 postgresql이 join과 병렬 쿼리, MVC를 지원 |^4.4.2|
| Amazon S3 | 이미지 저장. CICD의 과정 |^4.4.2|
| Swagger fox | Back-end에서 구현이 끝난 것을 일일이 front-end에 전달하는 과정에서 delay 발생. 개발 도중 추가로 개발한 API를 매번 API명세서에 기입하는 과정이 어려워 swagger를 통해 간접적인 소통 |^4.4.2|


## ✨ 주요 기능
| 페이지             |API 연결, 기능 및 CSS|시연 영상
|-----------------|---|---|
| 로그인 - 류창민       |✅카카오 로그인<br>✅로그인|
| 회원가입 - 류창민      |✅회원가입<br>✅아이디 중복 체크<br>✅닉네임 중복 체크|
| 마이 페이지 - 류창민    |✅로그아웃<br>✅마이페이지 차트<br>|
| 메인페이지 - 국경훈     |✅배너<br>✅카테고리<br>✅인기모임 <br>✅상단TOP 버튼|
| 모임 CRUD - 박현도   |✅|
| 모임 전체 페이지 - 박현도 |✅카테고리 별 sort<br>|
| 모임 상세 페이지 - 박현도 |✅등록된 모임 정보 불러오기<br>|
| 모임 관심 - 박현도     |✅관심 모임 등록<br>|
| 모임 참석/탈퇴 - 박현도  |✅모임 가입하기 탈퇴하기<br>|
| 채팅 - 조계일,류창민    |✅|
| 검색 기능 - 조계일     |✅|

<br>
<br>

## 🎯 트러블 슈팅

<details>
<summary> 1</summary>
<div markdown="1">   
    1

</div>
</details>
<details>
<summary> 2</summary>
<div markdown="1">   
    2

</div>
</details>
<details>
<summary> 3</summary>
<div markdown="1">   
    3

</div>
</details>
<details>
<summary> 4</summary>
<div markdown="1">   
    4

</div>
</details>

<br>
<br>

## 👩‍💻 유저 피드백 및 개선 사항


<br>
<details>
<summary> 1</summary>
<div markdown="1">   
    1

</div>
</details>
<details>
<summary> 2</summary>
<div markdown="1">   
    2

</div>
</details>
<details>
<summary> 3</summary>
<div markdown="1">   
    3

</div>
</details>
<details>
<summary> 4</summary>
<div markdown="1">   
    4

</div>
</details>

<br>

## 👻 추가하고 싶은 기능

<details>
<summary> Front-end</summary>
<div markdown="1">   

    - 반응형 도입 — 모바일ver
    - 보안 강화 — https
    - infinite carousel
    - infinite scroll — 모바일ver
    - 검색, 좋아요등 서버에 부하가 올 수 있는 api call 최적화
    - 이미지 용량 최적화
    - 채팅에서 이미지 전송 기능 추가
    - 후기 기능
    - api instance
    - 과거 채팅 무한 스크롤로 불러오기
</div>
</details>
<details>
<summary> Back-end</summary>
<div markdown="1">   

    - Redis를 연동하여 실시간 채팅 구현 고도화
    - Node.js의 scheduler를 통해 크롤링의 자동화 진행 → AWS Batch - CloudWatch 사용 예정
    - JPQL을 통해 성능 개선
    - postgres를 이용한 full-text search 도입 (데이터가 많아질수록 Like를 사용한 검색은 속도가 느리며, 메모리에 부담을 준다)
    - https 적용
    - ECS를 통한 배포 --> 현재의 배포에는 EC2 서버에 직접 Redis, JDK, Code Deploy를 설치하고 있다. 이것은 docker-compose를 통해 간단 배포로 변경하고 싶습니다.
    - ECR까지의 CI는 완료된 상태이나 ECS에서 Load Balace의 target group의 health check를 통과하지 못 하는 상태입니다.
    - 시간이 남는다면 kafka를 크롤링 서버와 log 수집, 실시간 채팅에 도입한 Data Pipeline을 구축해 보고 싶습니다.
    - Jwt 토큰 만료 예외처리 - JwtFilter 쪽에서
</div>
</details>





<br>
<hr>
<br>