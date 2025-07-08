# 🙏 Teba 앱 - 신앙 기반 커뮤니티 플랫폼

> **신앙인의 고민, 함께 나누는 공간**

테바는 교회 공동체 안에서 신앙적인 고민을 안전하게 나누고 소통할 수 있는 **신앙 기반 커뮤니티 앱**입니다.  
학교에는 에브리타임이 있고, 직장에는 블라인드가 있지만, 신앙인은 서로의 고민을 공유하고 지지할 수 있는 공간이 부족했습니다.

이 앱은 **신앙의 정체성을 지키면서 소통할 수 있는 안전한 공간을 제공**하는 것을 목표로 합니다.

---

## 🧩 문제 인식

- 📌 **신앙인의 정체성을 드러내고 소통할 장이 없다**
- 📌 기존 SNS는 익명성과 비난이 섞여 있어 신뢰하기 어렵다
- 📌 교회 내 커뮤니티는 폐쇄적이거나 기술적인 접근성이 낮다

---

## 💡 해결 방법

- ✅ **믿을 수 있는 구성원만 참여**할 수 있는 인증 기반 커뮤니티
- ✅ 정중한 말투, 신앙적 배려를 전제로 한 대화 디자인
- ✅ **기록 중심의 구조 (기도, 일기, 나눔)** 를 통해 깊이 있는 소통 유도

---

## 🔍 주요 기능

- 📜 익명 기반 고민 나눔 게시판
- 🙏 기도 제목 공유 & 기록
- 🗣️ 자유 나눔 공간 (공개/비공개 선택)
- 📨 교회별 가입 인증 기능 (예정)

---

## 🛠️ 사용 기술 스택

| 영역 | 기술 |
|------|------|
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | MVVM + Clean Architecture |
| Dependency Injection | Hilt |
| Local DB | Room |
| Backend | Firebase (Auth, Firestore, Storage 등) |
| Analytics / Crash | Firebase Analytics / Crashlytics |

---

## 📁 프로젝트 구조

```
teba-app/
├── data/ # Repository, DTO, Firebase 관련 소스
├── domain/ # UseCase, Entity 등 도메인 계층
├── presentation/ # Compose UI, ViewModel, State 등 프레젠테이션 계층
├── di/ # Hilt 모듈 정의
└── util/ # 공통 유틸 클래스들
```

---

## ▶️ 실행 방법

1. 이 저장소를 클론합니다.
   ```bash
   git clone https://github.com/your-username/teba-app.git
2. Android Studio에서 teba-app 프로젝트를 엽니다.
3. Firebase 설정 파일 (google-services.json)은 개인 키이므로 직접 추가해야 합니다.
4. 빌드 후 실행합니다. (API 28 이상 권장)

---

## 🗺️ 향후 계획

* 🔐 교회 인증 시스템 도입 (QR or 관리자 승인)
* 🧠 GPT 기반 묵상/기도 추천 기능 실험
* 📱 알림 기능 (예: 기도 요청 도착 시)

---

## 👤 개발자

* DongIk Seo (서동익)
* Android Developer / Faith-tech Enthusiast
* GitHub 프로필

---

> “기술은 사람을 이어주는 도구입니다. 신앙은 그 관계의 방향을 정합니다.”
— 테바 앱 개발자 주석 중
