# 🧶 Knitching Backend - Architecture & Directory Structure

본 프로젝트는 서비스의 확장성과 도메인 중심 개발을 위해 **기능 기반 패키지 구조(Package by Feature)**를 채택하고 있습니다. 각 패키지는 독립적인 비즈니스 책임을 가지며, 프로젝트 공통 요소는 `global` 패키지에서 관리합니다.

---

## 📂 전체 디렉토리 아키텍처

```text
src/main/java/com/knitching/app
├── global                  # 공통/인프라 설정 및 유틸리티
└── domain                  # 비즈니스 도메인 (기능별 패키지)
    ├── user                # 학생/회원 관리
    ├── instructor          # 강사 관리
    ├── pattern             # 손뜨개 도안 관리
    ├── video               # 기술 코드 및 영상 연동
    ├── curriculum          # 커리큘럼 생성 및 커스텀
    ├── enrollment          # 수강 및 진도율 마스터
    └── matching            # 1:1 강사 매칭 프로세스

```

---

## 🛠️ 패키지별 세부 구현 기능

### 1. `global` (Global/Infra)

> **모든 도메인에서 공통으로 재사용하는 인프라, 설정, 유틸리티를 모아둔 패키지입니다.**

* **`config`**: Security(JWT 필터 및 인가 설정), DB(MySQL 연결 및 JPA 설정), 웹 리소스 매핑(로컬 내부 파일 웹 URL 매핑 Config) 등을 구현합니다.
* **`common`**: 전역에서 사용하는 공통 응답 포맷(`ApiResponse<T>`), 페이징 공통 처리, BaseEntity(생성일/수정일 자동화)를 관리합니다.
* **`error`**: `GlobalExceptionHandler`를 통해 서버 내 발생하는 예외를 한곳에서 잡고, 클라이언트에게 일관된 에러 코드(`ErrorCode`)와 메시지를 반환합니다.
* **`util`**: JWT 토큰 생성 및 파싱, 파일 업로드 헬퍼 등 순수 유틸리티 클래스를 포함합니다.

---

### 2. `domain.user` (User Domain)

> **서비스를 이용하는 일반 학생(강습생)의 계정 및 보유 기술을 관리합니다.**

* **주요 엔티티**: `User`, `UserSkill`
* **구현 기능**:
* 학생 자체 회원가입 및 로그인 (Authentication/Authorization)
* 내 정보 조회 및 마이페이지 관리
* 학생의 뜨개질 숙련도 및 보유 기술 리스트(`UserSkill`) 등록/수정 (AI 커리큘럼 추천의 개인화 기반 데이터)



### 3. `domain.instructor` (Instructor Domain)

> **매칭 및 커리큘럼 제작을 담당하는 전문 강사 프로필을 관리합니다.**

* **주요 엔티티**: `Instructor`
* **구현 기능**:
* 강사 프로필 관리 (온/오프라인 가능 여부, 활동 지역, 전문 분야)
* 강사별 최대 수강생 제한 수 및 현재 수강 중인 인원 실시간 카운팅 로직
* 강사 전환 신청 및 승인 프로세스



### 4. `domain.pattern` (Pattern Domain)

> **플랫폼의 핵심 리소스인 손뜨개 도안(PDF) 데이터를 관리합니다.**

* **주요 엔티티**: `Pattern`
* **구현 기능**:
* 강사의 새 도안 파일 업로드 및 서버 내부 로컬 디렉토리 저장 처리
* 도안 파일명 중복 방지를 위한 UUID 변환 로직
* 도안 상세 정보 및 썸네일 이미지 매핑, 조회 기능



### 5. `domain.video` (Video Domain)

> **유튜브 API 연동 및 세부 뜨개질 기술 코드를 매핑합니다.**

* **주요 엔티티**: `Video`
* **구현 기능**:
* **TechCode 계층 구조 구현**: `[도구]-[난이도]-[분류]-[순번]` (예: `KNT-1-CST-01` -> 대바늘 기초 코잡기) 파싱 및 매핑
* 기술별 매칭되는 유튜브 영상 키(`VIDEO_KEY`) 관리 및 스트리밍 URL 제공
* 커리큘럼 생성을 위해 필요한 개별 기술 영상 검색 및 리스트 조회



### 6. `domain.curriculum` (Curriculum Domain)

> **정형화된 마스터 커리큘럼과 사용자를 위한 AI 커스텀 커리큘럼을 빌드합니다.**

* **주요 엔티티**: `Curriculum`, `CurriculumItem`
* **구현 기능**:
* 특정 도안(`Pattern`)을 완성하기 위해 필요한 세부 기술 영상들을 순서대로 엮은 `CurriculumItem` 생성
* 강사가 생성하는 공식 커리큘럼 및 학생의 보유 숙련도에 맞춰 필터링되는 **AI 커스텀 커리큘럼 생성 API**
* 커리큘럼 상세 정보 및 단계별 학습 목차 조회



### 7. `domain.enrollment` (Enrollment Domain)

> **학생의 수강 신청 마스터 및 개인 학습 진도율을 트래킹합니다.**

* **주요 엔티티**: `Enrollment`, `UserProgress`
* **구현 기능**:
* 특정 커리큘럼에 대한 수강 신청 및 수강 상태 변경 (ACTIVE, COMPLETED 등)
* 커리큘럼에 속한 개별 영상(`CurriculumItem`)의 시청 완료 여부(`IS_COMPLETED`) 및 완료 일시 기록
* 개별 영상 완료 상태를 기반으로 한 **수강생별 실시간 전체 진도율(%) 계산 및 대시보드 조회**



### 8. `domain.matching` (Matching Domain)

> **학생과 강사 간의 1:1 오프라인/온라인 강습 매칭 프로세스를 조율합니다.**

* **주요 엔티티**: `MatchingRequest`
* **구현 기능**:
* 학생이 특정 강사에게 원하는 도안을 바탕으로 1:1 학습 매칭 요청 기능
* 강사 시점에서의 매칭 요청 리스트 조회, 수락(`ACCEPTED`) 및 거절(`REJECTED`) 처리
* 매칭 완료 시 수강 데이터(`Enrollment`) 자동 연동 및 생성



---

## 💡 도메인 하위 공통 계층 규칙

각 도메인 패키지 내부에는 아래의 4가지 계층을 기본적으로 분리하여 구현합니다.

1. **`controller`**: 클라이언트의 HTTP 요청을 받고 응답하는 웹 계층 (`@RestController`)
2. **`service`**: 핵심 비즈니스 로직 및 트랜잭션을 처리하는 계층 (`@Service`)
3. **`repository`**: 데이터베이스 가독성을 극대화하기 위해 엔티티와 같은 위치 혹은 하위에 두는 Spring Data JPA 계층 (`@Repository`)
4. **`dto`**: 요청(`RequestDto`)과 응답(`ResponseDto`) 데이터 전송 객체 레이어 (엔티티가 외부로 노출되는 것을 철저히 방지)

