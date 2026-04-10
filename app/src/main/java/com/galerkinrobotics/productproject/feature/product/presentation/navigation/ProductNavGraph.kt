package com.galerkinrobotics.productproject.feature.product.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.galerkinrobotics.productproject.feature.product.presentation.add.ProductAddScreen
import com.galerkinrobotics.productproject.feature.product.presentation.detail.ProductDetailScreen
import com.galerkinrobotics.productproject.feature.product.presentation.list.ProductListScreen

@Composable
fun ProductNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = ProductRoute.List.route,
        modifier = modifier,
    ) {
        composable(ProductRoute.List.route) {
            ProductListScreen(
                onAddClick = { navController.navigate(ProductRoute.Add.route) },
                onProductClick = { productId ->
                    navController.navigate(ProductRoute.Detail.createRoute(productId))
                },
            )
        }
        composable(ProductRoute.Add.route) {
            ProductAddScreen(
                onNavigateBack = { navController.popBackStack() },
            )
        }
        composable(
            route = ProductRoute.Detail.route,
            arguments = listOf(
                navArgument(ProductNavArgs.PRODUCT_ID) { type = NavType.IntType },
            ),
        ) {
            ProductDetailScreen(
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}
