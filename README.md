# Hansung Minecraft

헬스케어 훈련 데이터를 Minecraft 서버와 연동하기 위해 제작한 Java 기반 Minecraft Plugin 프로젝트입니다.

외부 헬스케어 서비스에서 관리되는 사용자 데이터를 HTTP로 가져오고,  
훈련 장비에서 발생하는 실시간 호흡 데이터를 MQTT로 수신하여  
Minecraft 안의 NPC, 홀로그램, 블록, 이동 경로 기록 등의 게임 요소와 연결했습니다.

---

## 프로젝트 개요

이 프로젝트는 Minecraft 서버를 단순 게임 공간이 아니라
헬스케어 훈련 데이터를 반영하는 인터랙티브 환경으로 확장하기 위해 제작했습니다.

플레이어가 서버에 접속하면 HTTP 요청을 통해 외부 DB에서 사용자 정보를 가져오고,  
훈련 장비에서 발생하는 실시간 데이터는 MQTT를 통해 수신합니다.

수신된 데이터는 Minecraft 내부 이벤트로 변환되어 다음 기능에 사용됩니다.

- 사용자별 훈련 데이터 로드
- NPC 생성 및 이동
- NPC 말풍선 표시
- 호흡 데이터 기반 홀로그램/블록 상태 변경
- 플레이어 이동 경로 추적
- 종료 시 사용자 데이터 저장

자세한 동작 과정과 이미지 설명은 GitHub Wiki에 정리했습니다.

- [프로젝트 Wiki](https://github.com/k-chan-l/Hansung_Minecraft/wiki)
- [전체 구조 및 동작 순서](https://github.com/k-chan-l/Hansung_Minecraft/wiki#hansung-minecraft)

---

## 시스템 구조

<img src="https://user-images.githubusercontent.com/71301248/156567528-e16a68d7-cfb2-4793-8bfe-3d6ce20b147d.png">

---

## 동작 흐름

전체 동작 흐름은 Wiki의 순서도를 기준으로 구성했습니다.

```text
1. Player Join
   ↓
2. HTTP GET 요청
   - 플레이어 닉네임 기반으로 외부 DB에서 사용자 데이터 조회
   ↓
3. JSON Parsing
   - 최신 사용자 데이터를 JSON Object로 변환
   ↓
4. Bukkit Custom Event 호출
   - JsonObjectParsingEvent
   ↓
5. NPC / MQTT / Path Tracking 기능으로 전달
   ↓
6. MQTT Subscribe
   - 사용자별 Subject 구독
   ↓
7. 훈련 장비 데이터 수신
   - inhale / exhale 값 추출
   ↓
8. lungrowValueUpdateEvent 호출
   ↓
9. Hologram / Block / NPC 상태 갱신
   ↓
10. Player Quit
   ↓
11. 이동 경로 및 훈련 데이터 정리
   ↓
12. HTTP POST 요청으로 외부 DB에 저장
```

자세한 이미지 기반 설명은 아래 Wiki에서 확인할 수 있습니다.

- [Wiki - 구조도](https://github.com/k-chan-l/Hansung_Minecraft/wiki#%EA%B5%AC%EC%A1%B0%EB%8F%84)
- [Wiki - 동작 순서도](https://github.com/k-chan-l/Hansung_Minecraft/wiki#%EB%8F%99%EC%9E%91-%EC%88%9C%EC%84%9C%EB%8F%84)

---

## 주요 플러그인

이 레포지토리는 여러 Minecraft Plugin을 기능별로 분리한 구조입니다.

| Plugin | 역할 |
|---|---|
| `httpconnection` | HTTP GET/POST를 통해 외부 DB와 통신 |
| `MqttLungrow` | MQTT Broker 연결, 사용자별 Topic 구독, 훈련 데이터 수신 |
| `MqttHolo` | MQTT로 받은 inhale/exhale 값을 홀로그램 및 블록 상태로 시각화 |
| `NPCPlugin` | NPC 생성 및 제어 |
| `Npcchatbubble` | NPC 대사를 말풍선 형태로 표시 |
| `checkpath` | 플레이어 이동 경로 저장 |
| `letterBuild` | 입력한 문자를 Minecraft 블록으로 생성 |

---

## 핵심 기능

### 1. HTTP 기반 사용자 데이터 연동

`httpconnection` 플러그인은 플레이어 접속 시 외부 DB와 통신하여  
해당 플레이어의 사용자 데이터를 가져옵니다.

주요 역할은 다음과 같습니다.

- 플레이어 접속 이벤트 처리
- HTTP GET 요청
- JSON String 수신
- 최신 사용자 데이터 추출
- JSON Object 변환
- 종료 시 HTTP POST 요청으로 데이터 저장

```text
Player Join
    ↓
HTTP GET
    ↓
DynamoDB User Data
    ↓
JSON Parsing
    ↓
Custom Event
```

---

### 2. MQTT 기반 실시간 훈련 데이터 수신

`MqttLungrow` 플러그인은 MQTT Broker에 연결하고,  
사용자 데이터에서 추출한 Subject를 구독하여 실시간 훈련 데이터를 수신합니다.

수신 데이터에서 `inhale`, `exhale` 값을 추출한 뒤  
Minecraft 내부 Custom Event로 변환합니다.

```text
Training Device
    ↓
MQTT Publish
    ↓
MQTT Broker
    ↓
MqttLungrow Subscribe
    ↓
inhale / exhale parsing
    ↓
lungrowValueUpdateEvent
```

---

### 3. HolographicDisplays 기반 시각화

`MqttHolo` 플러그인은 `lungrowValueUpdateEvent`를 수신하여  
훈련 데이터를 Minecraft 월드 내부에 시각적으로 표현합니다.

주요 기능은 다음과 같습니다.

- inhale / exhale 값 표시
- 호흡 단계 변화 감지
- 단계별 블록 상태 갱신
- 조건 충족 시 버튼 이벤트 활성화
- 플레이어 종료 시 홀로그램 정리

이 기능은 `HolographicDisplays` 플러그인을 의존성으로 사용합니다.

---

### 4. NPC 생성 및 대사 출력

사용자 데이터에서 NPC 목적지 정보를 가져와 NPC를 생성하고 이동시킵니다.

NPC 이동 중 플레이어와 일정 거리 이상 멀어지면  
NPC 대사 이벤트를 호출하여 말풍선을 출력합니다.

```text
JSON Object
    ↓
NPC Destination Parsing
    ↓
NPC Spawn
    ↓
NPC Move
    ↓
NpcSpeakEvent
    ↓
Chat Bubble / Hologram
```

---

### 5. 플레이어 경로 저장

`checkpath` 플러그인은 플레이어의 이동 경로를 추적하고 저장합니다.

플레이어가 종료되면 지금까지 기록된 경로를 JSON Object에 추가하고,  
`httpconnection` 플러그인을 통해 외부 DB로 저장합니다.

---

### 6. 블록 문자 생성

`letterBuild` 플러그인은 명령어를 통해 문자를 Minecraft 블록으로 생성하는 기능입니다.

```text
/letter build <X> <Y> <Z> <letter> <MainBlockType> <BackgroundBlockType>
```

예를 들어 특정 좌표를 기준으로 5x5 형태의 블록 문자를 생성할 수 있습니다.

---

## 기술 스택

### Server / Plugin

- Java
- Bukkit / Spigot API
- Maven
- Minecraft Plugin

### Communication

- HTTP GET / POST
- MQTT
- Eclipse Paho MQTT Client

### External Service

- DynamoDB 연동 구조
- JSON 기반 사용자 데이터 처리

### Minecraft Extension

- HolographicDisplays
- NPC 제어
- Bukkit Custom Event

---

## 프로젝트 구조

```text
Hansung_Minecraft
├── httpconnection
│   └── HTTP GET/POST 기반 외부 DB 연동 플러그인
│
├── MqttLungrow
│   └── MQTT Broker 연결 및 훈련 데이터 수신 플러그인
│
├── MqttHolo
│   └── 수신한 호흡 데이터를 홀로그램/블록으로 시각화하는 플러그인
│
├── NPCPlugin
│   └── NPC 생성 및 제어 플러그인
│
├── Npcchatbubble
│   └── NPC 대사 표시 플러그인
│
├── checkpath
│   └── 플레이어 이동 경로 저장 플러그인
│
└── letterBuild
    └── 입력 문자를 블록으로 생성하는 보조 플러그인
```

---
## Architecture Design

프로젝트 구조상 MQTT_Lungrow Plugin이
실시간 장비 데이터의 중심 처리 역할을 담당하도록 구성했습니다.

초기에는 각 Plugin이 직접 MQTT 데이터를 처리하는 방식도 고려했지만,
기능 간 의존성이 증가하고 유지보수가 복잡해질 수 있다고 판단했습니다.

따라서 MQTT_Lungrow가 중앙에서 데이터를 수신하고,
각 기능 Plugin이 필요한 이벤트만 처리하도록 역할을 분리했습니다.

이를 통해 기능별 독립성을 유지하면서도
실시간 데이터 흐름을 통합적으로 관리할 수 있도록 구성했습니다.

---

## 설치 방법

각 플러그인 프로젝트를 빌드한 뒤 생성된 `.jar` 파일을  
Minecraft 서버의 `plugins` 폴더에 넣습니다.

```text
Minecraft Server
└── plugins
    ├── httpconnection.jar
    ├── MqttLungrow.jar
    ├── MqttHolo.jar
    ├── NPCPlugin.jar
    ├── Npcchatbubble.jar
    ├── checkpath.jar
    └── letterBuild.jar
```

이후 서버를 실행하면 각 플러그인이 로드됩니다.

---

## 관련 문서

상세한 동작 이미지와 순서도는 Wiki에 정리되어 있습니다.

- [Hansung Minecraft Wiki](https://github.com/k-chan-l/Hansung_Minecraft/wiki)
- [구조도](https://github.com/k-chan-l/Hansung_Minecraft/wiki#%EA%B5%AC%EC%A1%B0%EB%8F%84)
- [동작 순서도](https://github.com/k-chan-l/Hansung_Minecraft/wiki#%EB%8F%99%EC%9E%91-%EC%88%9C%EC%84%9C%EB%8F%84)

---

## 개선 가능 사항

- MQTT / HTTP 접속 정보를 `config.yml`로 분리
- 플러그인 간 의존성 구조 정리
- 공통 이벤트 모델 분리
- JSON 파싱 로직 공통화
- 비동기 HTTP 요청 처리 개선
- 플레이어별 세션 관리 구조 개선

---

## 배운 점

- Java 기반 Minecraft Plugin 개발
- Bukkit Event 기반 플러그인 간 통신 구조
- HTTP API와 외부 DB 연동
- MQTT 기반 실시간 데이터 수신
- HolographicDisplays를 활용한 게임 내 데이터 시각화
- NPC와 사용자 데이터 연동
- 게임 서버를 외부 헬스케어 데이터와 연결하는 시스템 설계
