package org.ilerna.apidemoapp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.ilerna.apidemoapp.model.DBCharacter
import org.ilerna.apidemoapp.viewmodel.APIViewModel

/**
 * HomeScreen - Pantalla principal que muestra la lista de personajes de Dragon Ball
 */
@Composable
fun HomeScreen(
    viewModel: APIViewModel,
    modifier: Modifier = Modifier
) {
    val charactersResponse by viewModel.characters.observeAsState()

    // Cargar personajes al mostrar la pantalla
    viewModel.getCharacters()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        charactersResponse?.items?.let { charactersList ->
            items(charactersList) { character ->
                CharacterCard(character = character)
            }
        }
    }
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