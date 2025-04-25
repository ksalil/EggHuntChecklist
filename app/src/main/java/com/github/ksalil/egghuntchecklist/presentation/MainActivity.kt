package com.github.ksalil.egghuntchecklist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.ksalil.egghuntchecklist.data.DataStoreEggHuntDataSource
import com.github.ksalil.egghuntchecklist.data.EggHuntDataSource
import com.github.ksalil.egghuntchecklist.domain.EggHuntRepository
import com.github.ksalil.egghuntchecklist.presentation.ui.theme.Background
import com.github.ksalil.egghuntchecklist.presentation.ui.theme.EggHuntChecklistTheme

class MainActivity : ComponentActivity() {
    private val dataSource: EggHuntDataSource by lazy {
        DataStoreEggHuntDataSource(applicationContext)
    }

    private val repository: EggHuntRepository by lazy {
        EggHuntRepository(dataSource)
    }

    private val viewModelFactory: EggHuntViewModelFactory by lazy {
        EggHuntViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Make system bars transparent and draw under them
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            EggHuntChecklistTheme {
                // Set system bars appearance based on theme
                val isDarkTheme = isSystemInDarkTheme()
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    isAppearanceLightStatusBars = !isDarkTheme
                    isAppearanceLightNavigationBars = !isDarkTheme
                }
                val viewModel: EggHuntViewModel = viewModel(factory = viewModelFactory)
                EggHuntScreen(viewModel = viewModel)
            }
        }
    }
}