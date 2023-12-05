package com.lucifer.tictactoegame.models

data class GameState(
    val playerCircleCount : Int = 0,
    val playerCrossCount : Int = 0,
    val playerDrawCount : Int = 0,
    val hintText : String = "Player 'O' turn",
    val currentTurn : BoardCellValue = BoardCellValue.CIRCLE,
    val victoryType : VictoryType = VictoryType.NONE,
    val hasWon : Boolean = false
    )

enum class VictoryType {
    HORIZONTAL1,
    HORIZONTAL2,
    HORIZONTAL3,
    VERTICAL1,
    VERTICAL2,
    VERTICAL3,
    DIAGONAL1,
    DIAGONAL2,
    NONE
}

enum class BoardCellValue {
    CROSS,
    CIRCLE,
    NONE
}
