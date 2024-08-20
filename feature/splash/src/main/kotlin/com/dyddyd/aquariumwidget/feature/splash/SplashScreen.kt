package com.dyddyd.aquariumwidget.feature.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxSize

@Composable
internal fun SplashRoute(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val splashUiState by viewModel.splashUiState.collectAsStateWithLifecycle()

    SplashScreen(
        modifier = modifier,
        splashUiState = splashUiState,
        navigateToHome = navigateToHome
    )
}

@Composable
internal fun SplashScreen(
    modifier: Modifier = Modifier,
    splashUiState: SplashUiState,
    navigateToHome: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        ImageMaxSize(
            painter = painterResource(id = R.drawable.feature_splash_background),
            contentDescription = "Splash Background",
        )

        if (splashUiState == SplashUiState.Finished) {
            navigateToHome()
        }
    }
}