package org.ilerna.apidemoapp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.ilerna.apidemoapp.R
import org.ilerna.apidemoapp.model.DBCharacter
import org.ilerna.apidemoapp.viewmodel.APIViewModel

/**
 * HomeScreen - Pantalla principal que muestra la lista de personajes de Dragon Ball
 *
 * Componentes:
 * - Header fijo con logo de Dragon Ball
 * - Lista scrolleable de personajes
 */
@Composable
fun HomeScreen(
    viewModel: APIViewModel,
    modifier: Modifier = Modifier
) {
    val charactersResponse by viewModel.characters.observeAsState()

    // Cargar personajes al mostrar la pantalla
    viewModel.getCharacters()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        // Header fijo con logo de Dragon Ball
        DragonBallHeader()

        // Lista scrolleable de personajes
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            charactersResponse?.items?.let { charactersList ->
                items(charactersList) { character ->
                    CharacterCard(character = character)
                }
            }
        }
    }
}

/**
 * DragonBallHeader - Header fijo con el logo de Dragon Ball
 *
 * Este header permanece visible incluso cuando se hace scroll en la lista
 */
@Composable
fun DragonBallHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo de Dragon Ball
        Image(
            painter = painterResource(id = R.drawable.dragon_ball_header),
            contentDescription = "Logo Dragon Ball",
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 16.dp)
        )
    }

    // Divisor naranja (color secundario de Dragon Ball)
    HorizontalDivider(
        thickness = 10.dp,
        color = Color(0xFFF39122) // Naranja de Dragon Ball
    )
}

/**
 * CharacterCard - Card individual para cada personaje en la lista
 *
 * @param character Personaje de Dragon Ball a mostrar
 * @param modifier Modificador opcional
 */
@Composable
fun CharacterCard(
    character: DBCharacter,
    modifier: Modifier = Modifier
) {
    Card(
        border = BorderStroke(2.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Nombre del personaje
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )

            // Raza del personaje
            Text(
                text = "Raza: ${character.race}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )

            // Nivel de poder (Ki)
            Text(
                text = "Ki: ${character.ki}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Blue,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}