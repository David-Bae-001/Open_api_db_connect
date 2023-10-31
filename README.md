# Open_api_db_connect
Open Api 및 DB 연동 (기상청 예보)

# A. 요구사항 및 적용내용
## 1. 요구 기술 스택
- Java : 11.0.17
- Springboot 2.x : 2.7.17 / Intellij IDEA 2023.2.4
- MySQL + JPA 또는 MyBatis : MySQL(MySQL workbench 8.0 CE) + JPA
- TDD (optinal) : X
- ChatGPT (optional) : 단위 테스트 구현 시

## 2. 구현 내용.
### 1.1. Description : 서버간 연동으로 Open API 를 연동
- 기상청 중기 예보 
- 기상청 단기 예보 
- 기상청 초단기 예보 
- 확인할 수 있는 API 
- 각 구현의 단위 테스트 
- 확인할 수 있는 API 의 통합 테스트 (optional) : X
- Spring Rest Docs로 API 문서 생성 자동화 (optional) : X

### 1.2.  Description
- 각 API 의 데이터를 JPA Entity, 또는 그에 준하는 POJO class로 매핑, MySQL DB에 넣는다 : JPA Entity를 통해 MySQL DB 입력
- DB Layer (Repository Layer) 는 반드시 JPA 또는 MyBatis 를 사용해야 한다 : JPA 사용
  - Repository Layer 에서 기본 JPA 메서드를 제외한, 직접 생성한 Query는 반드시 테스트 코드가 작성되어야 한다.
- Service 레이어에서 repository 로의 위임을 제외한 모든 로직은 테스트 코드가 작성되어야 한다 : HomeController 단위테스트 작성
- API 호출 또는 화면으로 위 구현사항을 확인 할 수 있어야 한다 : 구현
- **공공데이터 포탈에 기재된 예제 코드를 사용하지 않아야 한다.**

 # 3. Chat GPT 사용시.
  - Chat GPT로 결과를 도출한 로그 첨부 : https://chat.openai.com/share/4be3e71c-485d-4930-b333-4e2658a2ab90

B. 구현결과
 1. 기상청 중기 / 단기 / 초단기 예보 단순 확인을 위한 api 연동 완료 : 입력받는 값(위치, 시간)은 임의로 적용(서울, 실행일자 기준 06시)
 2. 확인할 수 있는 API 구현 : 중기예보 완료(단기/초단기 예보 미구현)
  - 확인하고자 하는 지역 사용자 입력(http://localhost:8080/api) > JPA를 통한 api response 값을 DB로 저장(table 생성 및 DB 초기화 후 저장, MySQL)
    > 생성된 테이블에 전체 값을 선택하여 출력(http://localhost:8080/result?loc=108)
 3. 단위테스트 : 확인할 수 있는 API가 구현된 중기예보에 대해 단위테스트 작성
  - Chat GPT 활용 하 작성 : HomeController

C. 결과 및 과정 중 배운 점 / 보완할 점
 1. 기존에 Open Api를 다뤄보지 않아 처음부터 끝까지 부족한 점이 많았음(구글링 다수 활용)
 2. 기존에는 STS를 주로 사용하다보니 springboot의 사용 및 설정에서 시간이 다수 소비되었음
 3. 한번에 제대로 된 것을 만들려다가 오히려 시간이 더 많이 소비되었음
 4. 테스트 분야(단위/통합/테스트코드)도 처음 접하여 시간이 걸렸고 TDD를 통해 테스트를 기반으로 개발하는 것이 오히려 순서에 따라 개발하는데 더 도움이 될 것 같다는 생각이 들었음
 5. JPA는 DB 입력시 시간과 노력을 획기적으로 줄여주는 효과가 있었으나 처음 접하여 배워서 하기에 다소 시간이 필요했음
 6. 확인할 수 있는 API구현은 단기/초단기의 경우 DB에 시 / 구 / 동단위까지 약 3000건을 세부입력하여 해당 X, Y좌표를 얻어서 진행해야 하는 부분이 있었는데 DB 입력까지는 진행하였지만
    개발 소요시간이 빠듯하여 진행하지 못한 부분이 아쉬움
 7. 이번 과정으로 Open Api, springboot, Intellij, jpa, 테스트에 대한 이해도를 높일 수 있었음
