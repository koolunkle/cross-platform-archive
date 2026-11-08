# Getting Started

> Compose Multiplatform 공식 시작 템플릿 구조를 따라 하나의 Kotlin UI 코드로 Android, Desktop, Web 앱을 만드는 첫 프로젝트입니다.

## 학습 내용

- **멀티 모듈 구조**: 공통 UI와 로직을 담는 `shared` 모듈과 플랫폼별 진입점 모듈(`androidApp`, `desktopApp`, `webApp`)의 분리
- **expect / actual**: 공통 코드에서 `expect fun getPlatform()`을 선언하고, 플랫폼마다 `actual`로 구현
- **공통 UI**: Compose Multiplatform의 Material 3 컴포넌트와 상태(`remember`, `mutableStateOf`), 애니메이션(`AnimatedVisibility`)
- **공통 리소스**: `composeResources`에 둔 벡터 이미지를 생성된 `Res` 클래스로 모든 플랫폼에서 사용
- **공통 테스트**: `commonTest`에 작성한 테스트를 JVM(Desktop) 타깃에서 실행

## 프로젝트 구조

```text
getting-started/
 ├── shared/                      # 공통 모듈 (Kotlin Multiplatform 라이브러리)
 │    └── src/
 │         ├── commonMain/        # 공통 UI(App), Greeting, Platform 선언, 리소스
 │         ├── commonTest/        # 공통 테스트
 │         ├── androidMain/       # Android용 actual 구현
 │         ├── desktopMain/       # Desktop(JVM)용 actual 구현
 │         └── wasmJsMain/        # Web(Wasm)용 actual 구현
 ├── androidApp/                  # Android 진입점 (MainActivity)
 ├── desktopApp/                  # Desktop 진입점 (main 함수, Window)
 └── webApp/                      # Web 진입점 (ComposeViewport, index.html)
```

## 개발 환경

| 항목 | 버전 |
| --- | --- |
| Kotlin | 2.3.20 |
| Compose Multiplatform | 1.10.1 |
| Android Gradle Plugin | 9.2.1 |
| Gradle | 9.6.0 |
| JDK | 17 |
| Android | compileSdk 36, minSdk 24 |

## 실행 방법

```bash
# Desktop
./gradlew :desktopApp:run

# Android (연결된 기기 또는 에뮬레이터에 설치)
./gradlew :androidApp:installDebug

# Web (개발 서버 실행 후 브라우저에서 확인)
./gradlew :webApp:wasmJsBrowserDevelopmentRun

# 공통 테스트
./gradlew :shared:desktopTest
```

iOS 타깃은 Mac 환경에서만 빌드할 수 있어 이 프로젝트에는 포함하지 않았습니다.

## 참고 자료

- [Get started with Compose Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-getting-started.html)
- [JetBrains/compose-multiplatform](https://github.com/JetBrains/compose-multiplatform): 빌드 구성은 저장소 예제의 모듈 구조를 참고했습니다.
