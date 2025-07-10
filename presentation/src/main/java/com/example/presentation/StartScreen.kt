package com.example.presentation

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.presentation.component.LargeButton
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.TebahTypography
import com.example.presentation.theme.primary

@Composable
fun StartScreen(
    onNavigateToLogin: () -> Unit,
    onBrowseServiceClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = Paddings.layout_horizontal,
                vertical = Paddings.layout_vertical
            )
    ) {
        WelcomeTexts(modifier = Modifier.weight(1f))
        SpacerSection(modifier = Modifier.weight(1f))
        StartButtonSection(
            onClick = onNavigateToLogin,
            onBrowseServiceClick = onBrowseServiceClick,
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
private fun WelcomeTexts(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = stringResource(id = R.string.start_title),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.start_subtitle),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SpacerSection(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Spacer(modifier = Modifier.size(Paddings.startImageSize))
    }
}

@Composable
private fun StartButtonSection(
    onClick: () -> Unit,
    onBrowseServiceClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LargeButton(
            text = stringResource(id = R.string.start_button),
            onClick = onClick
        )
        BrowseServiceTextButton(
            modifier = Modifier,
            onClick = onBrowseServiceClick
        )
    }
}

@Composable
fun BrowseServiceTextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Text(
        text = stringResource(id = R.string.browse_service_button),
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = Paddings.xlarge),
        color = primary,
        style = TebahTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center
    )
}

@Preview(
    name = "StartScreen Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun StartScreenLightPreview() {
    MaterialTheme {
        StartScreen(
            onNavigateToLogin = {},
            onBrowseServiceClick = {}
        )
    }
}

@Preview(
    name = "StartScreen Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun StartScreenDarkPreview() {
    MaterialTheme {
        StartScreen(
            onNavigateToLogin = {},
            onBrowseServiceClick = {}
        )
    }
}