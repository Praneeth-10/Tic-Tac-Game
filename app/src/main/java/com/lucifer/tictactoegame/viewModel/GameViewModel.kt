package com.lucifer.tictactoegame.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lucifer.tictactoegame.models.BoardCellValue
import com.lucifer.tictactoegame.models.GameState
import com.lucifer.tictactoegame.models.UserAction

class GameViewModel : ViewModel(){

    var state by mutableStateOf(GameState())

    val boardItems : MutableMap<Int, BoardCellValue> = mutableMapOf(
        1 to BoardCellValue.NONE,
        2 to BoardCellValue.NONE,
        3 to BoardCellValue.NONE,
        4 to BoardCellValue.NONE,
        5 to BoardCellValue.NONE,
        6 to BoardCellValue.NONE,
        7 to BoardCellValue.NONE,
        8 to BoardCellValue.NONE,
        9 to BoardCellValue.NONE,
    )


    fun onAction(action : UserAction){
        when(action){
            is UserAction.BoardTapped -> {
                addValueToBoard(action.cellNo)
            }

            UserAction.PlayAgainButtonClicked -> {
                TODO()
            }
        }
    }

    private fun addValueToBoard(cellNo: Int) {
        if(boardItems[cellNo] != BoardCellValue.NONE){
            return
        }
        if(state.currentTurn == BoardCellValue.CIRCLE){
            boardItems[cellNo] = BoardCellValue.CIRCLE
            state =state.copy(
                hintText = "Player 'X' turn",
                currentTurn = BoardCellValue.CROSS
            )
        }
        else if (state.currentTurn == BoardCellValue.CROSS){
            boardItems[cellNo] = BoardCellValue.CROSS
            state = state.copy(
                hintText = "Player 'O' turn",
                currentTurn = BoardCellValue.CIRCLE
            )
        }
    }
}