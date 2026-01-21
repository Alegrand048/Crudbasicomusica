package com.aba.crudsimplemusica

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class Artista(
    val id: Int,
    val nombre: String,
    val imagenResId: Int
)

val listaArtistas = listOf(
    Artista(1, "Mora", R.drawable.mora),
    Artista(2, "Bad Bunny", R.drawable.badbunny),
    Artista(3, "Feid", R.drawable.feid)
)

@Composable
fun PantallaInicio(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "Artistas",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(listaArtistas) { artista ->
                ArtistaCardGrid(artista) {
                    navController.navigate("canciones/${artista.id}")
                }
            }
        }
    }
}

@Composable
fun ArtistaCardGrid(artista: Artista, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = artista.imagenResId),
                contentDescription = artista.nombre,
                modifier = Modifier.size(140.dp)
            )
            Text(
                text = artista.nombre,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
