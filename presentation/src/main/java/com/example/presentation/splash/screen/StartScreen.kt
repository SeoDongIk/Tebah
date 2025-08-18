package com.example.presentation.splash.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.R
import com.example.presentation.common.component.LargeButton
import com.example.presentation.common.theme.GradientNavy
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.TebahTheme

@Composable
fun StartScreen(
    onNavigateToLogin: () -> Unit,
    onBrowseServiceClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush =Brush.verticalGradient(
                    colors = listOf(GradientNavy.copy(0f), GradientNavy.copy(1f)),
                    startY = 0f,
                    endY = 1000f
                )
            )
    ) {
        SpacerSection(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = Paddings.layout_horizontal,
                    vertical = Paddings.layout_vertical
                ),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            WelcomeTexts(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.weight(1f))
            StartButtonSection(
                onClick = onNavigateToLogin,
                onBrowseServiceClick = onBrowseServiceClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
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
        Image(
            painter = painterResource(id = R.drawable.ic_splash_background),
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.ic_splash_rocket),
            contentDescription = "Rocket",
            modifier = Modifier
                .size(300.dp)
        )
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
private fun BrowseServiceTextButton(
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
        color =  MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
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
    TebahTheme {
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
    TebahTheme {
        StartScreen(
            onNavigateToLogin = {},
            onBrowseServiceClick = {}
        )
    }
}