package com.learn.jetmovie

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.learn.jetmovie.ui.Screen.Checkout.CheckoutScreen
import com.learn.jetmovie.ui.Screen.detail.DetailScreen
import com.learn.jetmovie.ui.Screen.home.HomeScreen
import com.learn.jetmovie.ui.Screen.profile.ProfileScreen
import com.learn.jetmovie.ui.navigation.NavigationItem
import com.learn.jetmovie.ui.navigation.Screen
import com.learn.jetmovie.ui.theme.JetMovieTheme

@Composable
fun JetMoviesApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    //menghilangkan bottom bar pada halamn detail
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailMovie.route){
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) {innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ){
                composable(Screen.Home.route){
                    HomeScreen(
                        navigateToDetail = { movieId ->
                            navController.navigate(Screen.DetailMovie.createRoute(movieId))
                        }
                    )
                }
                composable(Screen.Checkout.route){
                    val context = LocalContext.current
                    CheckoutScreen(
                        onOrderButtonCLicked = { message ->
                                ShareOrder(context, message)
                        }
                    )
                }
                composable(Screen.Profile.route){
                    ProfileScreen()
                }
                composable(
                    route = Screen.DetailMovie.route,
                    arguments = listOf(navArgument("movieId"){ type = NavType.LongType }),
                ){
                    val id = it.arguments?.getLong("movieId") ?: -1L
                    DetailScreen(
                        movieId = id,
                        navigateBack = {
                            navController.navigateUp()
                        },
                        navigateToCart = {
                            navController.popBackStack()
                            navController.navigate(Screen.Checkout.route){
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                        }
                    )
                }
            }
    }
}

//membuat botton bar

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavigationBar(
        modifier = modifier
    ) {

        //untuk menegtahui route apa yang di pilih
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationsItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home,
            ),
            NavigationItem(
                title = stringResource(R.string.menu_cart),
                icon = Icons.Default.Star,
                screen = Screen.Checkout
            ),
            NavigationItem(
                title = stringResource(R.string.menu_Profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        navigationsItems.map {item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                icon = {
                       Icon(
                           imageVector = item.icon,
                           contentDescription = item.title,
                           tint = if (currentRoute == item.screen.route){
                               Color.Red
                           }else{
                               Color.Gray
                           }
                           )
                },
                label = { Text(item.title)},
                onClick = {
                    navController.navigate(item.screen.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

//fungsi untuk share ke aplikasi lain
private fun ShareOrder(context : Context, summary : String){
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.movie_time))
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.movie_time)
        )
    )
}


@Preview(showBackground = true)
@Composable
fun JetMoviesAppPreview() {
    JetMovieTheme {
        JetMoviesApp()
    }
}