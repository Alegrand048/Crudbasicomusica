package com.aba.crudsimplemusica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aba.crudsimplemusica.ui.theme.CrudSimpleMusicaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrudSimpleMusicaTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") {
            PantallaInicio(navController)
        }

        composable(
            route = "canciones/{artistaId}",
            arguments = listOf(navArgument("artistaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val artistaId = backStackEntry.arguments?.getInt("artistaId")
            PantallaCanciones(artistaId, navController)
        }

        composable(
            route = "detalle/{cancionId}",
            arguments = listOf(navArgument("cancionId") { type = NavType.IntType })
        ) { backStackEntry ->
            val cancionId = backStackEntry.arguments?.getInt("cancionId")
            PantallaDetalle(cancionId, navController)
        }
    }
}
