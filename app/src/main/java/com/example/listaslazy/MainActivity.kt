package com.example.listaslazy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listaslazy.model.Game
import com.example.listaslazy.repository.getAllGames
import com.example.listaslazy.repository.getGamesByStudio
import com.example.listaslazy.ui.theme.ListasLazyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListasLazyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GamesScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun GamesScreen(modifier: Modifier) {

    // Estado que guarda o texto digitado no campo de busca (nome do estúdio).
    // O "remember" mantém o valor entre recomposições e o mutableStateOf
    // faz a tela ser recomposta sempre que o valor mudar.
    var studio by remember {
        mutableStateOf("")
    }

    // Estado que representa a lista filtrada. Começa com getGamesByStudio("")
    // que, como todo nome começa com "", retorna todos os jogos.
    var gamesListByStudio by remember {
        mutableStateOf(getGamesByStudio(""))
    }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Meus jogos favoritos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            // value = "",  // ANTIGO: valor fixo, não permitia digitar.
            // Agora o campo usa o estado "studio".
            value = studio,
            // onValueChange = {/* TODO */},  // ANTIGO: substituído pela linha abaixo,
            // que atualiza o estado a cada letra digitada.
            onValueChange = { studio = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Nome do estúdio") },
            trailingIcon = {
                // IconButton(onClick = {/* TODO */}) {  // ANTIGO: sem ação.
                // Agora, ao clicar na lupa, filtramos a lista pelo estúdio digitado.
                IconButton(onClick = {
                    gamesListByStudio = getGamesByStudio(studio)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                }
            }
        )

        // LazyRow -> uma lista de itens inseridos na horizontal, com rolagem lateral.
        // Funciona de forma muito parecida com a LazyColumn.
        LazyRow() {
            items(gamesListByStudio) {
                StudioCard(game = it)
            }
        }

        // LazyColumn -> uma lista de itens inseridos na vertical
        // ela possui um scroll infinito, determinado pela quantidade de itens

        // criando uma lazy column e adicionando os itens manualmente
//        LazyColumn() {
//            item {
//                Text(text = "Item 1")
//            }
//
//            item {
//                Text(text = "Item 2")
//            }
//
//            item {
//                Text(text = "Item 3")
//            }
//        }

        // criando uma lazy column e adicionando os itens de forma dinâmica
//        LazyColumn() {
//            // o count determina a quantidade de itens, nesse caso seriam 6 itens
//            items(count = 6 ) {
//                Text(text = "Item $it")
//            }
//        }

        // ANTIGO: exibia apenas o título de cada jogo, usando todos os jogos do mock.
        // Foi substituído pela versão com GameCard e lista filtrada (abaixo).
//        LazyColumn() {
//            // Recebe uma lista como parâmetro para empilhar os itens
//            items( getAllGames()) {
//                Text(text = it.title)
//            }
//        }

        // ANTIGO: lista vertical com um GameCard por linha. Foi comentada porque
        // a LazyColumn e a LazyVerticalGrid rolam na vertical e disputariam o mesmo
//        LazyColumn() {
//            items(gamesListByStudio) {
//                GameCard(it)
//            }
//        }

        // ATUAL: LazyGrid (LazyVerticalGrid)   -> os itens são organizados em colunas.
        // GridCells.Fixed(2)                   -> cria 2 colunas de mesma largura
        // GridCells.Adaptive(minSize)          -> calcula o número de colunas pelo tamanho mínimo)

        // A lista continua vindo do estado "gamesListByStudio", então o filtro da lupa funciona.
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(gamesListByStudio) {
                GameCard(it)
            }
        }
    }

}

@Composable
fun GameCard(game: Game){
    Card(modifier = Modifier.padding(bottom = 8.dp)){
        Row( modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column( modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
                .weight(3f)
            ) {
                Text(
                    text = game.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = game.studio,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Text(
                modifier = Modifier.weight(1f)
                    .fillMaxWidth(),
                text = "${game.releaseYear}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue

            )
        }
    }
}

// Card quadrado (150dp) que mostra apenas o nome do estúdio,
// usado dentro da LazyRow de rolagem lateral.
@Composable
fun StudioCard(game: Game) {
    Card(
        modifier = Modifier
            .size(150.dp)
            .padding(end = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = game.studio,
                textAlign = TextAlign.Center
            )
        }
    }
}