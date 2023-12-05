package com.lucifer.tictactoegame.models

sealed class UserAction {
    object PlayAgainButtonClicked : UserAction()
    data class  BoardTapped(val cellNo : Int) : UserAction()
}