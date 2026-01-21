package com.aba.crudsimplemusica

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController

fun obtenerTodasLasCanciones(): List<Cancion> {
    return cancionesMora + cancionesBadBunny + cancionesFeid
}

fun buscarCancionPorId(cancionId: Int?): Cancion? {
    return obtenerTodasLasCanciones().find { it.id == cancionId }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(cancionId: Int?, navController: NavController) {
    val context = LocalContext.current
    val cancion = buscarCancionPorId(cancionId) ?: return

    val player = remember {
        MediaPlayer.create(context, cancion.audioResId)
    }

    DisposableEffect(Unit) {
        onDispose {
            try {
                if (player.isPlaying) {
                    player.stop()
                }
            } catch (_: IllegalStateException) {
            }
            player.release()
        }
    }

    val videoUri = Uri.parse("android.resource://${context.packageName}/${cancion.videoResId}")
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUri))
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(cancion.titulo) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Videoclip",
                style = MaterialTheme.typography.titleLarge
            )

            AndroidView(
                factory = { ctx -> PlayerView(ctx) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                update = { playerView ->
                    playerView.player = exoPlayer
                    playerView.useController = true
                }
            )

            Text(
                text = "Escuchar Canción",
                style = MaterialTheme.typography.titleLarge
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = { if (!player.isPlaying) player.start() }) {
                    Text("Play")
                }

                Button(onClick = { if (player.isPlaying) player.pause() }) {
                    Text("Pause")
                }

                Button(
                    onClick = {
                        player.seekTo(0)
                        if (player.isPlaying) player.pause()
                    }
                ) {
                    Text("Reiniciar")
                }
            }
        }
    }
}
