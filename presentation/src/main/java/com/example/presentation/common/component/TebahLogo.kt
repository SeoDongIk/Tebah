package com.example.presentation.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.presentation.R
import com.example.presentation.common.theme.Paddings

@Composable
fun TebahLogo(
    modifier: Modifier = Modifier,
    letterModifier: Modifier = Modifier.size(32.dp)
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Paddings.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        listOf(
            R.drawable.ic_t,
            R.drawable.ic_e,
            R.drawable.ic_b,
            R.drawable.ic_a,
            R.drawable.ic_h
        ).forEach { resId ->
            Image(
                painter = painterResource(id = resId),
                contentDescription = null,
                modifier = letterModifier
            )
        }
    }
}