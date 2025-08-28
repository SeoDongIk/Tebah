# Tebah

안드로이드용 교회 커뮤니티 애플리케이션입니다. `app`, `domain`, `data`, `presentation` 네 개의 모듈로 구성되어 있으며, 클린 아키텍처를 기반으로 합니다.

## 📦 모듈 구조
- **app** – 실제 안드로이드 실행 모듈
- **domain** – 순수 Kotlin 로직과 use case
- **data** – 원격/로컬 데이터 소스와 레포지토리 구현
- **presentation** – UI와 ViewModel

## 🔧 빌드 방법
필수 환경:
- JDK 17 이상
- Android Studio Giraffe 이상

명령어 예시:
```bash
./gradlew clean build            # 전체 빌드 및 테스트
./gradlew :data:test             # data 모듈 단위 테스트
```

## 🤝 기여 가이드
1. 이슈를 먼저 등록해 주세요.
2. 기능/버그별로 작은 단위의 PR을 만들어 주세요.
3. `./gradlew test` 로 모든 테스트가 통과하는지 확인해 주세요.

문의나 제안은 이슈 트래커를 이용해 주세요.
