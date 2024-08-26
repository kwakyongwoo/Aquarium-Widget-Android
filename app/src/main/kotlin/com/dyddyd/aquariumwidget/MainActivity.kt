package com.dyddyd.aquariumwidget

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dyddyd.aquariumwidget.MainActivityUiState.Loading
import com.dyddyd.aquariumwidget.core.designsystem.theme.AquariumWidgetTheme
import com.dyddyd.aquariumwidget.ui.AquariumApp
import com.dyddyd.aquariumwidget.ui.rememberAquariumAppState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach { uiState = it }
                    .collect()
            }
        }

        enableEdgeToEdge()
        setContent {
            viewModel.updateLastPlayedDate()

            val activity = (LocalContext.current as Activity)
            val windowInsetsController = remember {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    activity.window.insetsController
                } else {
                    null
                }
            }

            DisposableEffect(Unit) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.light(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ),
                    navigationBarStyle = SystemBarStyle.light(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ),
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    windowInsetsController?.let {
                        it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                        it.hide(WindowInsets.Type.navigationBars())
                    }

                    onDispose {
                        windowInsetsController?.show(WindowInsets.Type.navigationBars())
                    }
                } else {
                    val decorView = activity.window.decorView
                    val originalSystemUiVisibility = decorView.systemUiVisibility
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

                    onDispose {
                        decorView.systemUiVisibility = originalSystemUiVisibility
                    }
                }
            }

            val appState = rememberAquariumAppState()

            AquariumWidgetTheme {
                AquariumApp(appState)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
}