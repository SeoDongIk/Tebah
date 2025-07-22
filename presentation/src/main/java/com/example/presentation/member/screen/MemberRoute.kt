package com.example.presentation.member.screen

sealed class MemberRoute(val route: String) {
    object PostDetail : MemberRoute("postDetail/{postId}") {
        fun create(postId: String) = "postDetail/$postId"
    }

    object ChannelDetail : MemberRoute("channelDetail/{channelId}") {
        fun create(channelId: String) = "channelDetail/$channelId"
    }

    object OtherUser : MemberRoute("otherUser/{userId}") {
        fun create(userId: String) = "otherUser/$userId"
    }

    object FollowList : MemberRoute("followList/{userId}") {
        fun create(userId: String) = "followList/$userId"
    }

    object PhotoDetail : MemberRoute("photoDetail/{photoUrl}") {
        fun create(photoUrl: String) = "photoDetail/$photoUrl"
    }

    object ProfileEdit : MemberRoute("profileEdit")
    object ChannelCreate : MemberRoute("channelCreate")
    object FollowSuggestion : MemberRoute("followSuggestion")
}