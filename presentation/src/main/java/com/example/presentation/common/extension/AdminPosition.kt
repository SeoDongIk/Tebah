package com.example.presentation.common.extension

import android.content.Context
import com.example.domain.model.AdminPosition
import com.example.presentation.R

fun AdminPosition.displayName(context: Context): String {
    return when (this) {
        AdminPosition.PASTOR -> context.getString(R.string.admin_position_pastor)
        AdminPosition.ADMIN -> context.getString(R.string.admin_position_admin)
        AdminPosition.CUSTOM -> context.getString(R.string.admin_position_custom)
    }
}

fun resIdToAdminPosition(resId: Int): AdminPosition =
    when (resId) {
        R.string.admin_position_pastor -> AdminPosition.PASTOR
        R.string.admin_position_admin -> AdminPosition.ADMIN
        R.string.admin_position_custom -> AdminPosition.CUSTOM
        else -> AdminPosition.CUSTOM
    }