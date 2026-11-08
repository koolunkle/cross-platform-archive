# Nav Cupcake

> JetBrains의 Compose Multiplatform 예제 `nav_cupcake`를 따라 만든 컵케이크 주문 앱입니다. 하나의 공통 코드로 Android, Desktop, Web에서 화면 이동(Navigation)과 상태 관리(ViewModel)를 구현합니다.

## 학습 내용

- **Navigation Compose (멀티플랫폼)**: `NavHost`와 문자열 경로로 화면 4개(수량 → 맛 → 픽업 날짜 → 요약) 연결, 뒤로 가기와 `popBackStack`으로 첫 화면 복귀
- **ViewModel과 StateFlow**: 여러 화면이 하나의 `OrderViewModel`을 공유하며 주문 상태 유지
- **단방향 데이터 흐름**: 각 화면은 상태를 직접 바꾸지 않고, 콜백으로 이벤트만 상위(NavHost)에 전달
- **공통 리소스**: 문자열, 복수형(plurals), 벡터 이미지를 `composeResources`로 관리
- **kotlinx-datetime**: 플랫폼에 관계없이 오늘부터 4일간의 픽업 날짜 계산
- **테스트**: ViewModel 단위 테스트(`commonTest`)와 Desktop에서 실행하는 Compose UI 테스트(`desktopTest`)

## 원본 예제와 다른 점

| 항목 | 원본 | 이 프로젝트 |
| --- | --- | --- |
| 주문 요약의 Total | 가격 자리에 수량이 들어감 | 가격으로 수정 |
| 가격 표시 | Double 기본 형식 (`12.0€`) | 소수점 둘째 자리 (`$12.00`) |
| 수량 표시 | 복수형 처리가 TODO로 남아 있음 | plurals 리소스로 `1 cupcake` / `6 cupcakes` 구분 |
| 주문 전송 | 미구현 (TODO) | 공통 코드의 다이얼로그로 주문 요약 표시 |
| 시간 의존성 | `Clock.System`을 직접 사용 | `Clock`과 `TimeZone`을 주입받아 테스트에서 날짜 고정 |
| 테스트 | 없음 | ViewModel 단위 테스트 5개, UI 테스트 2개 |
| 대상 플랫폼 | Android, iOS, Desktop, Web | Android, Desktop, Web (iOS는 Mac 환경이 필요해 제외) |

## 프로젝트 구조

```text
nav-cupcake/
 ├── shared/
 │    └── src/
 │         ├── commonMain/
 │         │    ├── kotlin/com/koolunkle/cupcake/
 │         │    │    ├── App.kt, CupcakeApp.kt   # 루트 UI, 화면 정의, NavHost
 │         │    │    ├── data/                   # 주문 상태, 선택 옵션
 │         │    │    └── ui/                     # ViewModel, 화면, 테마, 공통 컴포넌트
 │         │    └── composeResources/            # 문자열, 이미지
 │         ├── commonTest/                       # ViewModel 단위 테스트
 │         └── desktopTest/                      # Compose UI 테스트
 ├── androidApp/                                 # Android 진입점
 ├── desktopApp/                                 # Desktop 진입점
 └── webApp/                                     # Web(Wasm) 진입점
```

## 실행 방법

```bash
# Desktop
./gradlew :desktopApp:run

# Android (연결된 기기 또는 에뮬레이터에 설치)
./gradlew :androidApp:installDebug

# Web (개발 서버 실행 후 브라우저에서 확인)
./gradlew :webApp:wasmJsBrowserDevelopmentRun

# 테스트 (단위 테스트 + UI 테스트)
./gradlew :shared:desktopTest
```

개발 환경은 [getting-started](../getting-started)와 같습니다(Kotlin 2.3.20, Compose Multiplatform 1.10.1, AGP 9.2.1, Gradle 9.6.0, JDK 17).

UI 문자열은 영문을 사용합니다. Web(Wasm) 기본 폰트에는 한글 글리프가 없어서, 한글을 쓰려면 폰트를 리소스로 따로 추가해야 합니다.

## 출처

- [JetBrains/compose-multiplatform - examples/nav_cupcake](https://github.com/JetBrains/compose-multiplatform/tree/master/examples/nav_cupcake)
- 원본 예제는 Android 코드랩 [Navigate between screens with Compose](https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation)를 Compose Multiplatform으로 옮긴 것이며, Apache License 2.0을 따릅니다.
