package com.example.domain.usecase.notice

import com.example.domain.model.CommentWithReplies
import com.example.domain.repository.NoticeRepository
import javax.inject.Inject

class DistributeNoticeUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) {
    suspend operator fun invoke(noticeId: String): Result<Unit> {
        return noticeRepository.distributeNotice(noticeId)
    }
}