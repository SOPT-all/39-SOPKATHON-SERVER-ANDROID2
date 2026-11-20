# 39-SOPKATHON-SERVER-ANDROID2

> PLAY SOPT 39기 솝커톤 안드로이드 서버 2조 🍋

## 👥 Contributors

| 김현준 | 이승희 | 박이수 |
|:---:|:---:|:---:|
| <img height="200" alt="김현준" src="https://github.com/user-attachments/assets/ffb8bc0d-6b8b-4027-a337-e5c1378226a8" /> | <img height="200" alt="이승희" src="https://github.com/user-attachments/assets/7eb25c1d-01e8-417d-be0a-036db1384128" /> | <img height="200" alt="박이수" src="https://github.com/user-attachments/assets/3bc319c0-f7e3-42fb-a84c-5998f9c9ed3e" /> |
| LEAD Developer | Developer | Developer |

## 🛠️ Tech Stack

| 구분 | 기술 |
| --- | --- |
| Language | Java 21 |
| Framework | Spring Boot 4.1.2 |
| Build | Gradle |
| Web | Spring Web MVC |
| ORM | Spring Data JPA, Hibernate |
| Database | MySQL |
| Validation | Spring Boot Validation |
| API Documentation | Springdoc OpenAPI, Swagger UI |
| Utility | Lombok, Spring Boot DevTools |
| Deployment | Docker, Eclipse Temurin 21 JRE |

## 📁 Project Structure

```text
src/main
├── java/org/sopt
│   ├── common
│   │   ├── code          # 성공·실패 응답 코드
│   │   ├── config        # JPA Auditing, Swagger 설정
│   │   ├── entity        # 공통 엔티티
│   │   ├── exception     # 예외 및 전역 예외 처리
│   │   └── response      # 공통 API 응답
│   └── Application.java
└── resources
    ├── application.yml
    └── application-prod.yaml
```

## 📦 Common API Response

API 응답은 다음과 같은 공통 형식을 사용합니다.

```json
{
  "status": 200,
  "code": "SUCCESS",
  "message": "요청이 성공적으로 처리되었습니다.",
  "data": {}
}
```

예외가 발생하면 전역 예외 처리기를 통해 HTTP 상태 코드와 에러 코드, 메시지를 포함한 응답을 반환합니다.


## 🌱 Git Convention

### Issue

이슈 제목은 `[type] 작업 내용` 형식을 사용합니다.

- 작업 내용을 간결하게 작성합니다.
- 이슈 제목에는 이슈 번호를 포함하지 않습니다.

```text
[docs] README 작성
[feat] 어떠한 API 구현
[fix] 어떠한 문제 해결
```

### Branch

브랜치 이름은 `type/#이슈번호` 형식을 사용합니다.

```text
feat/#12
fix/#21
chore/#3
```

### Commit

커밋 메시지는 `[type/#이슈번호] 제목` 형식을 사용합니다.

- type은 영어 소문자로 작성합니다.
- 제목은 한국어 50자 이내로 작성합니다.
- 제목은 명사형으로 끝내며 마침표는 생략합니다.
- 하나의 커밋에는 하나의 논리적 변경만 담습니다.

```text
[feat/#12] 위대한 API 구현 및 무언가 추가
[fix/#21] 엄청난 실수 삭제
[docs/#3] 도움이 될 수도 있는 문서 작성
```

### Commit Tags

| 태그 | 용도 |
|:---:|---|
| `feat` | 새 기능 추가 |
| `fix` | 버그 수정 |
| `refactor` | 동작을 유지하면서 코드 구조 개선 |
| `style` | 포맷, 공백 등 코드 형식 수정 |
| `docs` | 문서 수정 |
| `test` | 테스트 코드 추가·수정 |
| `chore` | 설정, 의존성 등 기타 작업 |
| `ci` | CI/CD 설정 변경 |
| `rename` | 파일·폴더 이름 변경 또는 이동 |
| `remove` | 파일 삭제 |
| `hotfix` | 배포 환경 긴급 수정 |
| `init` | 프로젝트 초기 설정 |


### 🔥 🔥 🔥 
