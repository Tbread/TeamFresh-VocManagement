# Pageonce

2022년 1월 일 기준

http://

에서 테스트 가능하며

해당 프로젝트 소스이용시 application.properties 에서db 경로와 db 사용자이름,암호를 입력해야 합니다.

## DB설계

### User

- id (pk,long)
- username (아이디, string)
- password (암호, string)
- companyId (Company fk,long)
- driverId (Driver fk,long)
- createdAt (가입일, datetime)
- modifiedAt (최근 회원정보 수정일, datetime)

### Company

- id (pk)
- companyName (회사이름, string)
- contactEmail (연락이메일, string)
- contactNum (연락처, string)
- penalty (패널티금액, long) - 회사타입이 GOODS(고객사) 일 경우에만 기입됨
- type (회사타입, enum) // GOODS,DELIVERY
- createdAt (등록일, datetime)
- modifiedAt (최근 회사정보 수정일, datetime)

### Driver

- id (pk)
- penalty (패널티금액, long)
- companyId (Company fk, long)

### Voc

- id (pk)
- responsibility (귀책타입, enum) // DRIVER,SELLER
- details (귀책 상세내역, string)
- companyId (Company fk, long)
- driverId (Driver fk, long)
- compensationId (Compensation fk, long)
- checked (기사의 패널티 확인여부, boolean)
- objection (기사의 패널티 이의제기 여부, boolean)
- conclude (종결여부, boolean) - 현재는 기사가 패널티를 수락하거나, 고객사에 패널티가 즉시 적용된후 종결
- createdAt (voc 작성일, datetime)
- modifiedAt (최근 voc 수정일, datetime)

### Compensation(배상정보)

- id (pk)
- amount (배상 금액, long)

### Penalty

- id (pk)
- amount (패널티 금액, long)
- objection (이의제기 여부, boolean)
- driverId (Driver fk, long)
- compensationId (Compensation pk, long)
- createdAt (패널티 작성일, datetime)
- modifiedAt (최근 패널티 수정일, datetime)

#### 클래스 다이어그램 및 스키마 캡쳐본이 첨부되어있습니다.

## API

### 모든 정보는 json타입으로 송/수신하며 아래와 같이 서술합니다.

### [정보설명 (데이터타입) - json 키값명/필수여부]

#### UserController ( /user )

1. 회원가입 ( /register )
    - PostMapping
    - 요구 정보
        1. 아이디 (string) - username / 필수
        2. 암호 (string) - rawPassword / 필수

    - 응답
        1. 스테이터스 코드 (int) - code
        2. 메세지 (string) - message
        3. 가입성공, 또는 중복 아이디 일시 아이디 (string) - username


2. 로그인 ( /login )
    - PostMapping
    - 요구 정보
        1. 이메일 (string) - email / 필수
        2. 암호 (string) - password / 필수
    - 응답
        1. 스테이터스 코드 (int) - code
        2. 메세지 (string) - message
        3. 로그인 성공시 로그인한 아이디 (string) - username
        4. JWT 토큰 (string) - token

#### CompanyController ( /company )

1. 회사 추가 ( /add-company )
    - PostMapping
    - 요구 헤더
        1. JWT 토큰 (string) - Authorization / 필수
    - 요구 정보
        1. 회사이름 (string) - companyName / 필수
        2. 회사타입 (string) - type (GOODS or DELIVERY) / 필수
        3. 회사연락이메일 (string) - contactEmail / 필수 , 이메일형식
        4. 회사연락처 (string) - contatctNum / 필수 , 정규식 [0-9]{2,3}-[0-9]{3,4}-[0-9]{3,4}

    - 응답
        1. 스테이터스 코드 (int) - code
        2. 메세지 (string) - message
        3. 작성된 회사 객체 (성공시) - company

#### DriverController ( /driver )

1. 운송기사 가입 ( /join-driver/{companyId} )
    - PostMapping
    - 요구 헤더
        1. JWT 토큰 (string) - Authorization / 필수
    - 요구 정보
        1. 기사로 가입하려는 회사의 id (long) - pathvariable / 필수

    - 응답
        1. 스테이터스 코드 (int) - code
        2. 메세지 (string) - message
        3. 기사로 가입된 회사 이름 (성공시) - companyName

#### VocController ( /voc )

1. 신규 voc 추가 ( /new-voc )
    - PostMapping
    - 요구 정보
        1. voc 상세 내용 (string) - details / 필수
        2. 배상요구 금액 (long) - compensationAmount / 배상 필요시
        3. 귀책 회사 id (long) - companyId / 고객사 귀책일시
        4. 귀책 운송기사 id (long) - driverId / 운송기사 귀책일시

    - 응답
        1. 스테이터스 코드 (int) - code
        2. 메세지 (string) - message
        3. 작성된 voc 객체 (성공시) - voc

2. voc 목록 조회 ( /view )
    - GetMapping

    - 응답
        1. 스테이터스 코드 (int) - code
        2. 메세지 (string) - message
        3. SimpleVoc 객체 리스트 (성공시) - vocList

#### CompensationController ( /compensation )

1. 배상정보 목록 조회 ( /view )
    - GetMapping

    - 응답
        1. 스테이터스 코드 (int) - code
        2. 메세지 (string) - message
        3. Compensation 객체 리스트 (성공시) - compensationList

#### PenaltyController ( /penalty )

1. 패널티 발급 ( /handling/{compensationId} )
    - PostMapping
    - 요구 정보
        1. 패널티를 발급시킬 배상정보의 id (long) - pathvariable / 필수

    - 응답
        1. 스테이터스 코드 (int) - code
        2. 메세지 (string) - message
        3. 작성된 Penalty 객체 (성공시) - penalty

    - 비고
        - 요구사항에 따로 고객사측의 패널티 관리가 없기에 고객사의 패널티는 발급 즉시 적용되어 Company 객체에 반영 및 voc 종결처리함


2. 자신에게 발급된 패널티 목록 조회 ( /view ) - 기사 전용
    - GetMapping
    - 요구 헤더
        1. JWT 토큰 (string) - Authorization / 필수

    - 응답
        1. 스테이터스 코드 (int) - code
        2. 메세지 (string) - message
        3. Penalty 객체 리스트 (성공시) - penaltyList
    - 비고
        - User가 기사로 가입한 경우에만 자신의 패널티를 조회가능
        - 요청성공시 voc 객체의 check (기사확인여부)가 수정됨


3. 패널티 이의제기 신청 ( /objection/{penaltyId} ) - 기사 전용
    - PostMapping
    - 요구 헤더
        1. JWT 토큰 (string) - Authorization / 필수
    - 요구 정보
        1. 이의를 제기할 패널티의 id (long) - pathvariable / 필수

    - 응답
        1. 스테이터스 코드 (int) - code
        2. 메세지 (string) - message
    - 비고
        - User가 기사로 가입한 경우에만 자신의 패널티를 이의 제기 가능
        - 요청성공시 voc 객체의 objection (이의제기여부)가 수정됨


4. 패널티 수용 ( /admit/{penaltyId} ) - 기사 전용
    - PostMapping
    - 요구 헤더
        1. JWT 토큰 (string) - Authorization / 필수
    - 요구 정보
        1. 수용할 패널티의 id (long) - pathvariable / 필수

    - 응답
        1. 스테이터스 코드 (int) - code
        2. 메세지 (string) - message
        3. 총 누적된 패널티 금액 (long) - penaltyTotal
    - 비고
        - User가 기사로 가입한 경우에만 자신의 패널티를 수용가능
        - 요청성공시 voc 객체의 conclude (종결여부)가 수정됨
        - 요청성공시 driver 객체의 penalty에 금액이 추가됨

