package com.example.domain.model

enum class PostType {
    CHANNEL, // 기존 방식
    CHURCH,  // 특정 교회에 소속된 일반 게시글 (전체보기 탭 등)
    GLOBAL   // 범교회적, 모든 사용자에게 공개되는 글
}