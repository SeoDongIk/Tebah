package com.example.domain.usecase.notice

import com.example.domain.model.CommentWithReplies
import com.example.domain.repository.NoticeRepository
import javax.inject.Inject

class RequestNoticeUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) {
    suspend operator fun invoke(postId: String, targetChannelId: String, requesterUserId: String): Result<Unit> {
        return noticeRepository.requestNotice(postId, targetChannelId, requesterUserId)
    }
}