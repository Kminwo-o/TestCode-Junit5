## BDD (Behavior Driven Development)
- TDD에서 파생된 개발 방법
- 함수 단위의 테스트에 집중하기보다, 시나리오에 기반한 테스트케이스 자체에 집중하여 테스트한다.
- 개발자가 아닌 사람이 봐도 이해할 수 있을 정도의 추상화 수준을 권장한다.

### given / when / then
- given : 시나리오 진행에 필요한 모든 준비 과정 (객체, 값, 상태 등)
- when : 시나리오 행동 진행
- then : 시나리오 진행에 대한 결과 명시, 검증
- 주석으로 given, when, then을 명시한다. 이에 맞춰 DisplayName을 작성하면 더 용이하다.

> IntelliJ의 경우 설정에서 Live Templates -> Java 에서 code style을 given / when / then 으로, DisplayName 등을 미리 단축으로 부를 수 있도록 설정하면 편하다.

