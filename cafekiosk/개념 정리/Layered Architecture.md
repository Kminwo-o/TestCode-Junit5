# Layered Architecture

## 정의
- 사용자에게 요청이 왔을 때, 역할을 분리하여 로직을 설계
- 주로 Spring Mvc 패턴에서 자주 쓰는 방법
- 사용자 -> Presentation Layer -> Business Layer -> Persistence Layer -> DB
    - Presentation Layer
      - 외부 세계의 요청을 가장 먼저 받는 계층
      - 파라미터에 대한 @Validation을 검증이 주안점
      - 사용자가 데이터를 전달하기 위해 화면에 정보를 표시하는 것이 주 관심사
      - View, Controller
    - Business Layer
      - 비즈니스 로직을 수행하는데에 관심을 둔다.
      - 데이터를 출력하는 방법이나 어디서 가져오는지에 대한 내용은 필요없음
      - Service, Domain
    - Persistence Layer
      - 데이터의 출처와 데이터를 가져오고 다루는 것
      - DAO, Repository
    - Database Layer
      - DB 계층

## 목적
- 관심사의 분리
- 각 역할에 따라 책임을 나누고 유지보수성을 높이는 것이 목적
- ! 싱크홀 안티패턴 주의
  - 특정 레이어가 아무 동작, 로직의 수행 없이 들어온 요청을 그대로 다시 하위 레이어로 보내는 것.
  - ex) 조회 요청 시, service 단에서 검증 로직 없이 repo에서 가져오기만 한다면 불필요한 리소스 낭비. 

### 통합 테스트
- 여러 모듈이 협력하는 기능을 통합적으로 검증하는 테스트
- 일반적으로 작은 범위의 단위 테스트만으로는 기능 전체의 신뢰성을 보장할 수 없음
- 풍부한 단위 테스트 & 큰 기능 단위를 검증하는 통합 테스트