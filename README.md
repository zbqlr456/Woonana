# 🏃Woonana(우나나)
<img src = "https://user-images.githubusercontent.com/63107888/224536657-ee436bf1-d1e9-4bdd-9586-738993c8085a.png" />
> 주변 사람들과 함께 운동하기 위한 서비스 입니다.
> 운동 하게 나와!

## 🔍주요 기능

- 설명 : 주변에서 운동할 사람을 손쉽게 구해 같이 운동할 수 있게 도와주는 서비스
- 주요 기능 :
  - 장소와 시간을 정해서 사람을 모집할 수 있음
  - 채팅을 통해 운동 모임 전에 대화를 할 수 있음
  - 운동이 끝난 후에 평가를 통해 모임에 참여했던 사람들을 평가할 수 있음
  - 운동 기록을 전체기록, 월별기록, 그래프 등으로 제공해서 확인가능

## 📚기술 스택

![기술스택](README.assets/%EA%B8%B0%EC%88%A0%EC%8A%A4%ED%83%9D.png)


## 💽시스템 아키텍처

![서비스흐름도](README.assets/%EC%84%9C%EB%B9%84%EC%8A%A4%ED%9D%90%EB%A6%84%EB%8F%84.png)


## 🔑ERD

![자율플젝ERD](README.assets/ERD%20%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8.png)


## 👨‍💻나의 역할

### 팀원 공통
- 2주간의 서비스 기획을 통한 **회의록, 주제 선정, 세부 기능명세서, API 명세서, 와이어프레임**을 작성하였습니다.
- **ERD 다이어그램**을 설계하였습니다.

### 팀장으로서 프로젝트의 전반적인 진행사항을 리딩
- 매일 아침 데일리스크럼을 하며 프로젝트의 진행과정을 체크하였습니다.
- 각 팀원의 장점을 살려 알맞은 역할을 분배해주었습니다.
- Spring과 Vue.js 설계의 틀을 잡아주었습니다.

### 채팅기능의 DB를 분리하여 개발하였습니다.
- 주요 기능은 Mysql, 채팅기능은 MongoDB로 분리하여 개발하였습니다.
- 메세지브로커인 STOMP를 이용하여 개발하였습니다.
- 주요 기능들과 메세지를 연동시키며 기능별 통합을 주도하였습니다.
