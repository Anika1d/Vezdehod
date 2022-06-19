package com.template.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.template.foodies.MainActivity
import com.template.foodies.navigation.Route

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalFoundationApi::class)
@ExperimentalAnimationApi
@Composable
fun StartNavigation(navController: NavHostController, mainActivity: MainActivity) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Route.Splash.route
    ) {
        composable(route = Route.Splash.route) {
            mainActivity.SplashAnimation(navController = navController)
        }
        composable(route = Route.Menu.route) {
            mainActivity.MenuScreen(navController = navController)
        }
        composable(route = Route.Basket.route) {
            mainActivity.BasketScreen(navController = navController)
        }
        composable(route = Route.Product.route) {
            mainActivity.ProductScreen(navController = navController)
        }
        composable(route = Route.Clearance.route) {
            mainActivity.ClearanceScreen(navController = navController)
        }
    }
}


