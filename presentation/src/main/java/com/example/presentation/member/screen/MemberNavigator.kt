package com.example.presentation.member.screen

import androidx.navigation.NavController

class MemberNavigator(private val navController: NavController) {
    fun onPostClick(postId: String) =
        navController.navigate(MemberRoute.PostDetail.create(postId))

    fun onUserClick(userId: String) =
        navController.navigate(MemberRoute.OtherUser.create(userId))

    fun onChannelClick(channelId: String) =
        navController.navigate(MemberRoute.ChannelDetail.create(channelId))

    fun onNavigateToProfileEdit() =
        navController.navigate(MemberRoute.ProfileEdit.route)

    fun onNavigateToFollowList(userId: String) =
        navController.navigate(MemberRoute.FollowList.create(userId))

    fun onNavigateToChannelCreate() =
        navController.navigate(MemberRoute.ChannelCreate.route)

    fun onNavigateToFollowSuggestion() =
        navController.navigate(MemberRoute.FollowSuggestion.route)

    fun onNavigateToPhotoDetail(photoUrl: String) =
        navController.navigate(MemberRoute.PhotoDetail.create(photoUrl))

    fun onBack() =
        navController.popBackStack()
}