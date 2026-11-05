package com.example.listaslazy.repository

import com.example.listaslazy.model.Game

fun getAllGames(): List<Game> {
    return listOf(
        Game(id = 1, title = "Resident Evil Village", studio = "Capcom", releaseYear = 2021),
        Game(id = 2, title = "The Last of US Part II", studio = "Naughty Dog", releaseYear = 2020),
        Game(id = 4, title = "Elden Ring", studio = "FromSoftware", releaseYear = 2022),
        Game(id = 6, title = "God of War Ragnarök", studio = "Santa Monica Studio", releaseYear = 2022),
        Game(id = 8, title = "Marvel's Spider-Man 2", studio = "Insomniac Games", releaseYear = 2023),
        Game(id = 10, title = "Resident Evil 4 Remake", studio = "Capcom", releaseYear = 2023),
        Game(id = 3, title = "Uncharted 4: A Thief's End", studio = "Naughty Dog", releaseYear = 2016),
        Game(id = 5, title = "Sekiro: Shadows Die Twice", studio = "FromSoftware", releaseYear = 2019),
        Game(id = 7, title = "God of War", studio = "Santa Monica Studio", releaseYear = 2018),
        Game(id = 9, title = "Marvel's Spider-Man", studio = "Insomniac Games", releaseYear = 2018),
    )
}

fun getGamesByStudio(studio: String): List<Game>{
    return  getAllGames().filter {
        it.studio.startsWith(prefix = studio, ignoreCase = true)
    }
}