package com.example.domain.usecase.notice

import com.example.domain.model.CommentWithReplies
import com.example.domain.repository.NoticeRepository
import javax.inject.Inject

class RejectNoticeUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) {
    suspend operator fun invoke(noticeRequestId: String): Result<Unit> {
        return noticeRepository.rejectNotice(noticeRequestId)
    }
}