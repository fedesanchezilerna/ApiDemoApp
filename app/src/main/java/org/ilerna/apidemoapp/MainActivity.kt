package org.ilerna.apidemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.ilerna.apidemoapp.model.DBCharacter
import org.ilerna.apidemoapp.ui.theme.ApiDemoAppTheme
import org.ilerna.apidemoapp.viewmodel.APIViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApiDemoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val myViewModel: APIViewModel = viewModel()
                    MyRecyclerView(
                        myViewModel = myViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MyRecyclerView(myViewModel: APIViewModel, modifier: Modifier = Modifier) {
    val charactersResponse by myViewModel.characters.observeAsState()

    myViewModel.getCharacters()

    LazyColumn(
        Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        charactersResponse?.items?.let { charactersList ->
            items(charactersList) { character ->
                CharacterItem(character = character)
            }
        }
    }
}

@Composable
fun CharacterItem(character: DBCharacter) {
    Card(
        border = BorderStroke(2.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Raza: ${character.race}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Ki: ${character.ki}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Blue,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}