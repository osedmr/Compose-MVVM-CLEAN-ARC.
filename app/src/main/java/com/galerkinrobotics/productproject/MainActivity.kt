package com.galerkinrobotics.productproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.galerkinrobotics.productproject.core.ui.theme.ProductProjectTheme
import com.galerkinrobotics.productproject.feature.product.presentation.navigation.ProductNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductProjectTheme {
                ProductNavGraph()
            }
        }
    }
}

