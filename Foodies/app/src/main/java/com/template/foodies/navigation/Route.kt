package com.template.foodies.navigation

sealed class Route(val route: String) {
    object Splash : Route("splash_screen")
    object Menu : Route("menu_screen")
    object Product : Route("productInfo")
    object Basket : Route("basket")
    object Clearance : Route("clearance")
}