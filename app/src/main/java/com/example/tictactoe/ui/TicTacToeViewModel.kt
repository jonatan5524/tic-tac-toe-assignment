package com.example.tictactoe.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tictactoe.model.Game
import com.example.tictactoe.model.Player

class TicTacToeViewModel : ViewModel() {
    private val player1 = Player("Player X", 'X')
    private val player2 = Player("Player O", 'O')
    private val game = Game(player1, player2)

    var board by mutableStateOf(Array(3) { Array<Player?>(3) { null } })
        private set

    var currentPlayer by mutableStateOf(player1)
        private set

    var winner by mutableStateOf<Player?>(null)
        private set

    var isDraw by mutableStateOf(false)
        private set

    fun onCellClicked(row: Int, col: Int) {
        if (winner == null && !isDraw && board[row][col] == null) {
            val newBoard = board.map { it.clone() }.toTypedArray()
            newBoard[row][col] = currentPlayer
            board = newBoard // Trigger recomposition

            winner = game.checkWin(board)
            if (winner == null) {
                isDraw = game.isBoardFull(board)
                if (!isDraw) {
                    currentPlayer = if (currentPlayer == player1) player2 else player1
                }
            }
        }
    }

    fun playAgain() {
        board = Array(3) { Array<Player?>(3) { null } }
        currentPlayer = player1
        winner = null
        isDraw = false
    }
}
