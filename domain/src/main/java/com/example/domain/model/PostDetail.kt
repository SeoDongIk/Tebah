package com.example.domain.model

data class PostDetail(
    val post: Post,
    val comments: List<CommentWithReplies>
)