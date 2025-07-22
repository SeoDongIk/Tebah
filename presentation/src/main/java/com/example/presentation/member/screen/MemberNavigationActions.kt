package com.example.presentation.member.screen

interface MemberNavigationActions {
    fun onPostClick(postId: String)
    fun onUserClick(userId: String)
    fun onChannelClick(channelId: String)
}