package com.learn.jetmovie

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.learn.jetmovie.model.FakeMoviesData
import com.learn.jetmovie.ui.navigation.Screen
import com.learn.jetmovie.ui.theme.JetMovieTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JetMoviesAppTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetMovieTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                JetMoviesApp(navController = navController)
            }
        }
    }


    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("MovieList").performScrollToIndex(14)
        composeTestRule.onNodeWithText(FakeMoviesData.listMovies[14].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailMovie.route)
        composeTestRule.onNodeWithText(FakeMoviesData.listMovies[14].title).assertIsDisplayed()
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_cart).performClick()
        navController.assertCurrentRouteName(Screen.Checkout.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesBack() {
        composeTestRule.onNodeWithTag("MovieList").performScrollToIndex(14)
        composeTestRule.onNodeWithText(FakeMoviesData.listMovies[14].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailMovie.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back_text)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_checkout_rightBackStack() {
        composeTestRule.onNodeWithText(FakeMoviesData.listMovies[4].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailMovie.route)
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Buy").performClick()
        navController.assertCurrentRouteName(Screen.Checkout.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    //negatif case
    @Test
    fun navHost_invalidItemIndex_doesNotNavigateToDetail() {
        composeTestRule.onNodeWithTag("MovieList").performScrollToIndex(-1)
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_invalidRoute_doesNotNavigate() {
        composeTestRule.onNodeWithTag("InvalidRouteItem").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_checkout_withoutItemSelected_doesNotNavigateToCheckout() {
        composeTestRule.onNodeWithStringId(R.string.menu_cart).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_backButtonWithoutPreviousScreen_doesNotNavigateBack() {
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back_text)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }
}