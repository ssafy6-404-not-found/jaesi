# ✨재능시장✨

<p align="left">
<img alt="Java" src ="https://img.shields.io/badge/Java-ED8B00.svg?&style=flat&logo=Java&logoColor=white"/>
<img alt="SpringBoot" src ="https://img.shields.io/badge/Spring Boot-6DB33F.svg?&style=flat&logo=SpringBoot&logoColor=white"/>
<img alt="JPA" src ="https://img.shields.io/badge/JPA-6DB33F.svg?&style=flat&logo=spring&logoColor=white"/>
<img alt="QueryDSL" src ="https://img.shields.io/badge/QueryDSL-6DB33F.svg?&style=flat&logo=spring&logoColor=white"/>
<img alt="MySQL" src ="https://img.shields.io/badge/MySQL-4479A1.svg?&style=flat&logo=MySQL&logoColor=white"/>
<img alt="Redis" src ="https://img.shields.io/badge/Redis-DC382D.svg?&style=flat&logo=Redis&logoColor=white"/>
<img alt="HTML" src ="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white"/>
<img alt="CSS" src ="https://img.shields.io/badge/CSS-239120?&style=flat&logo=css3&logoColor=white"/>
<img alt="JavaScript" src ="https://img.shields.io/badge/JavaScript-323330?style=flat&logo=Javascript&logoColor=F7DF1E"/>
<img alt="VueJS" src ="https://img.shields.io/badge/Vue.js-35495E?style=flat&logo=Vue.js&logoColor=4FC08D"/>
<img alt="VueJS" src ="https://img.shields.io/badge/Vuex-35495E?style=flat&logo=Vue.js&logoColor=4FC08D"/>
<img alt="BootstrapVue" src ="https://img.shields.io/badge/Bootstrap-7952B3?style=flat&logo=Bootstrap&logoColor=white"/>
<img alt="Intellij" src ="https://img.shields.io/badge/IntelliJ-000000?style=flat&logo=IntelliJIdea&logoColor=white"/>
<img alt="vscode" src ="https://img.shields.io/badge/VSCode-007ACC?style=flat&logo=VisualStudioCode&logoColor=white"/>
<img alt="AWS EC2" src ="https://img.shields.io/badge/AWS EC2-FF9900?style=flat&logo=AmazonAWS&logoColor=white"/>
<img alt="Firebase" src ="https://img.shields.io/badge/Firebase-FFCA28?style=flat&logo=Firebase&logoColor=white"/>
<img alt="Docker" src ="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=Docker&logoColor=white"/>
<img alt="KakaoPay" src ="https://img.shields.io/badge/KakaoPay-FFCD00?style=flat&logo=Kakao&logoColor=black"/>
<img alt="Socket.io" src ="https://img.shields.io/badge/Socket.io-010101?style=flat&logo=Socket.io&logoColor=white"/>
<img alt="WebRTC" src ="https://img.shields.io/badge/WebRTC-333333?style=flat&logo=WebRTC&logoColor=white"/>
<img alt="JWT" src ="https://img.shields.io/badge/JWT-000000?style=flat&logo=JsonWebTokens&logoColor=white"/>
</p>

<br>

<img align="center" width="1080" src="https://user-images.githubusercontent.com/47655983/154631859-00b2b3a1-5479-4cab-be41-d60df0dc0454.png">

<br>

## 📑개요

라이브 방송 기능이 탑재된 재능 마켓 플랫폼

[삼성청년SW아카데미](https://www.ssafy.com/) 6기 2학기 공통 프로젝트

<br>

### 📆프로젝트 기간
2022.01.10 ~ 2022.02.18 (6주)

<br>

## 🕹시연영상

[![logo](https://user-images.githubusercontent.com/42627507/154907628-a7fc06ff-e8a8-4632-ad0a-c91cb8531aa9.png)](https://youtu.be/3HQ3o0S2VZc) <strong> 👈👈 클릭 ! </strong>

<br>

---

## ✨주요 기능

- webRTC를 통한 전문가 라이브 방송
- 오픈 마켓 주문 ~ 결제 처리
- 전문가 서비스, 주문, 리뷰 관리
- 전문가와 채팅을 통한 실시간 문의

<br>

## 🔧기술스택

<img alt="기술스택" src="https://user-images.githubusercontent.com/47655983/154678897-7919688b-49d0-4e78-b1ad-27b1513bb058.png" width=1080px>

<br>

## 📌기술 특이점

<img align = "right" src="https://www.gstatic.com/devrel-devsite/prod/v870be6fb6841f3532cd3aec5bc0b3146031642f2794ae8ba7f51ebf843a655f9/firebase/images/touchicon-180.png" width="150">

### Firebase

실시간 채팅 구현과 이미지 파일을 저장하기 위한 미디어 서버로 Google Firebase를 활용했습니다. 새로고침 없이 사용자 경험을 극대화하는 SPA의 특성을 살리기 위해 특정 채팅방에 들어가면 Firebase Database와 연동하여 해당 채팅방의 유저는 서로 실시간 소통이 가능하도록 하였습니다. 

또한, 서비스 등록에 필요한 대표 이미지와 전문가 라이브 방송 썸네일을 위한 이미지 파일은 Firebase Storage를 활용했습니다. 미디어 서버를 분리하여, 해당 이미지을 저장 후 url 주소를 받아, backend에는 해당 url을 저장하는 식으로 DB를 관리했습니다.

<br>
<br>

<img align = "right" src="https://avatars.githubusercontent.com/u/22744124?s=200&v=4" width="150">

### WebRTC (Openvidu)

Openvidu로만 할 수 있는 기능 뿐만이 아니라 백엔드를 함께 이용한 개발로 여러 기능을 구현했습니다. 전문가가 생성한 방송에 들어가면 해당 전문가가 진행중인 라이브를 볼 수 있도록 하였습니다. 전문가가 방송을 종료하게 되면, 시청자들이 해당 세션에 더이상 접근할 수 없도록 하여 라이브 방송에서 나가지도록 하였습니다. 

채팅시스템을 연동하여 해당 세션에 접속한 사용자들이 서로 소통할 수 있도록 하였습니다. 또한, 방송을 진행 중일 경우 썸네일과 실시간 시청자 수를 서버로 전송하여, 라이브 방송에 대한 정보를 방송 목록 수준에서 사용자들이 알기 쉽도록 하였습니다.

<br>
<br>

<img align = "right" src="https://t1.kakaocdn.net/kakaocorp/kakaocorp/admin/5e5fc297017800001.png" width="130">

### KakaoPay

주문 정보를 KakaoPay와 연동하여 결제를 진행하는 기능을 구현했습니다. 테스트베드로 구현하여 실제 결제는 이루어지지 않지만, 

주문을 생성하고, KakaoPay API를 통해 결제 성공 여부를 확인, 처리 결과를 사용자가 알 수 있도록 하였습니다.

<br>
<br>
<br>

---

## 협업 Tools

## 💠Jira
협업 및 일정, 업무 관리를 위해 Jira를 이용하였습니다. 매주 월요일 오전 회의에서 한 주동안 진행되어야 할 주 단위 계획을 짜고, 진행할 이슈들을 스프린트를 만들어 등록했습니다. 스프린트는 일주일 단위로 진행하였습니다.

⚡Epic : backend, frontend, webrtc로 나누어 구성하였습니다.

🔰Story : 마이페이지에서 프로필 변경 버튼을 통해 프로필을 변경 한다와 같이 자세하게 작성하였습니다.

✅Sub-Task : 부작업을 나누어 세세하게 업무 관리를 진행하였습니다.

<br>

## 📓Notion
모두가 봐야할 공지, 함께 공부해야 할 링크 등을 모아 관리했습니다. 그리고 항상 모든 회의 및 피드백은 기록으로 남겨두어서 잘 반영할 수 있도록 하였습니다. 컨벤션 규칙, 브랜치 전략 등도 노션에 기록하여 모두가 항시 확인할 수 있도록 관리했습니다.

저희 팀의 컨벤션 및 프로젝트 설계 내용을 보고 싶다면? --> [노션 링크](https://extreme-creek-e0c.notion.site/eee25867415941a4a26f5e1f6a90f70e)

<br>

---

## 😎팀원소개
| **[박찬혁](https://github.com/acardiav)** | **[황정준](https://github.com/DeerGum)** | **[최재진](https://github.com/svstar94)** | **[김은송](https://github.com/SSongsing)** | **[윤홍림](https://github.com/honglim99)** |
| :-----------------: | :-----------------: | :-----------------: | :-----------------: | :-----------------: |
| <img src="https://github.com/acardiav.png" width=150px, height=150px> | <img src="https://github.com/DeerGum.png" width=150px, height=150px > | <img src="https://github.com/svstar94.png" width=150px, height=150px> | <img src="https://github.com/SSongsing.png"  width=150px, height=150px> | <img src="https://github.com/honglim99.png"  width=150px, height=150px> |
| 팀장 & BackEnd | BackEnd | FrontEnd | FrontEnd | FrontEnd |
| 요구사항 작성 <br> API 명세 작성 <br> 서비스, 주문, 리뷰, 북마크 API 개발 <br> 프로젝트 자료 정리 및 문서화 | BackEnd 개발환경 구축 및 배포<br> DB설계 및 Docker로 DB 환경 구축<br> 카카오페이 백엔드 연동 <br> 유저, 라이브방송 API 개발 <br> Redis 연동 | 화면설계 <br> Firebase 연동 <br> WebRTC Openvidu 연동 <br> SocketJS 세션채팅 연동 <br> KakaoPay 프론트 연동 <br> 홈, 회원가입, 라이브 방, <br> 채팅, 서비스 생성 화면 개발 | 요구사항 작성 <br> 화면설계 <br> 마이페이지(회원정보, 주문, 리뷰, <br> 찜, 서비스 등록), 로그인, <br> 라이브 방속 목록 화면 개발 | 요구사항 작성 <br> 화면설계 <br> 상품판매페이지, 상품상세페이지, <br> 구매페이지 화면 개발 |

<br>