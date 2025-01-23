package com.devikiran.seekhoassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.devikiran.seekhoassignment.ui.GETData
import com.devikiran.seekhoassignment.ui.theme.SeekhoAssignmentTheme
import com.devikiran.seekhoassignment.util.ApiState

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SeekhoAssignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)){
                        GETData(viewModel)

                        BackHandler {
                            when (viewModel.response.value) {
                                is ApiState.DetailedAnimeSuccess -> {
                                    viewModel.navigateToTopAnime()
                                }
                                else ->{
                                    finish()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
