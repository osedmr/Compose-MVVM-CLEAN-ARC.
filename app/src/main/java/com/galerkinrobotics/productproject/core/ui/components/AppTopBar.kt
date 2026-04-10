package com.galerkinrobotics.productproject.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Uygulama genelinde ortak [TopAppBar].
 * Geri tuşu: [onNavigationClick] verildiğinde gösterilir; açıklama erişilebilirlik için önerilir.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onNavigationClick: (() -> Unit)? = null,
    navigationIconContentDescription: String? = null,
) {
    TopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = {
            if (onNavigationClick != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = navigationIconContentDescription,
                    )
                }
            }
        },
    )
}
