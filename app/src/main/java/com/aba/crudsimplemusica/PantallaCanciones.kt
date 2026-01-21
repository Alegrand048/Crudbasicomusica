package com.aba.crudsimplemusica

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class Cancion(
    val id: Int,
    val titulo: String,
    val imagenResId: Int,
    val audioResId: Int,
    val videoResId: Int
)

//Solo he rellenado los detalles de las 2 primeras canciones de Mora
//De forma predeterminada las demas canciones tienen el contenido de la primera cancion
val cancionesMora = listOf(
    Cancion(1, "TUYO", R.drawable.tuyomora, R.raw.tuyo, R.raw.tuyo_video),
    Cancion(2, "AURORA", R.drawable.lo_mismo_de_siempre_mora, R.raw.aurora, R.raw.aurora_video),
    Cancion(3, "LA INOCENTE", R.drawable.microdosismora, R.raw.tuyo, R.raw.tuyo_video)
)

//No hay nada relleno todo esta predeterminado
val cancionesBadBunny = listOf(
    Cancion(4, "TITÍ ME PREGUNTÓ", R.drawable.error, R.raw.tuyo, R.raw.tuyo_video),
    Cancion(5, "UN X100TO", R.drawable.error, R.raw.tuyo, R.raw.tuyo_video),
    Cancion(6, "DAKITI", R.drawable.error, R.raw.tuyo, R.raw.tuyo_video)
)

//No hay nada relleno todo esta predeterminado
val cancionesFeid = listOf(
    Cancion(7, "BZRP MUSIC SESSIONS VOL.53", R.drawable.error, R.raw.tuyo, R.raw.tuyo_video),
    Cancion(8, "ELLA BAILA SOLA", R.drawable.error, R.raw.tuyo, R.raw.tuyo_video),
    Cancion(9, "PROVENZA", R.drawable.error, R.raw.tuyo, R.raw.tuyo_video)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCanciones(artistaId: Int?, navController: NavController) {
    val canciones = when (artistaId) {
        1 -> cancionesMora
        2 -> cancionesBadBunny
        3 -> cancionesFeid
        else -> emptyList()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Canciones") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver al inicio")
                }
            }
        )

        LazyColumn {
            items(canciones) { cancion ->
                CancionItem(cancion) {
                    navController.navigate("detalle/${cancion.id}")
                }
            }
        }
    }
}

@Composable
fun CancionItem(cancion: Cancion, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = cancion.imagenResId),
                contentDescription = cancion.titulo,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = cancion.titulo,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}