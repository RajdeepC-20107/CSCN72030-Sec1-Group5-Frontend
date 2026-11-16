package ru.anton2319.absolutcinema

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.anton2319.absolutcinema.ui.AbsolutCinemaApp
import ru.anton2319.absolutcinema.ui.theme.AbsolutCinemaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AbsolutCinemaTheme {
                AbsolutCinemaApp()
            }
        }
    }
}
