package com.example.domain.model

data class CommentWithReplies(
    val comment: Comment,
    val replies: List<Reply>
)