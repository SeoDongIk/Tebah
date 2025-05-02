package com.example.domain.repository

import com.example.domain.model.NoticeRequest

interface NoticeRepository {
    suspend fun requestNotice(postId: String, targetChannelId: String, requesterUserId: String): Result<Unit>
    suspend fun getNoticeRequestsForChannel(channelId: String): Result<List<NoticeRequest>>
    suspend fun approveNotice(noticeRequestId: String): Result<Unit>
    suspend fun rejectNotice(noticeRequestId: String): Result<Unit>
    suspend fun distributeNotice(noticeId: String): Result<Unit>
}