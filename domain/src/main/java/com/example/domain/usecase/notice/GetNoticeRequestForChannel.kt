package com.example.domain.usecase.notice

import com.example.domain.model.CommentWithReplies
import com.example.domain.model.NoticeRequest
import com.example.domain.repository.NoticeRepository
import javax.inject.Inject

class GetNoticeRequestForChannel @Inject constructor(
    private val noticeRepository: NoticeRepository
) {
    suspend operator fun invoke(channelId: String): Result<List<NoticeRequest>> {
        return noticeRepository.getNoticeRequestsForChannel(channelId)
    }
}