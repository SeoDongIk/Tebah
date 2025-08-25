package com.example.presentation.common.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.domain.model.Region
import com.example.presentation.R

@Composable
fun Region.toDisplayName(): String {
    return when (this) {
        Region.SEOUL -> stringResource(R.string.region_seoul)
        Region.INCHEON_GYEONGGI -> stringResource(R.string.region_incheon_gyeonggi)
        Region.DAEJEON_CHUNGNAM -> stringResource(R.string.region_daejeon_chungnam)
        Region.GWANGJU_JEONNAM -> stringResource(R.string.region_gwangju_jeonnam)
        Region.DAEGU_GYEONGBUK -> stringResource(R.string.region_daegu_gyeongbuk)
        Region.BUSAN_GYEONGNAM -> stringResource(R.string.region_busan_gyeongnam)
    }
}