package com.example.tictactoe.model

class Game(val player1: Player, val player2: Player) {

    fun checkWin(board: Array<Array<Player?>>): Player? {
        // Check rows
        for (i in 0..2) {
            if (board[i][0] != null && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0]
            }
        }
        // Check columns
        for (i in 0..2) {
            if (board[0][i] != null && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return board[0][i]
            }
        }
        // Check diagonals
        if (board[0][0] != null && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0]
        }
        if (board[0][2] != null && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2]
        }
        return null
    }

    fun isBoardFull(board: Array<Array<Player?>>): Boolean {
        return board.all { row -> row.all { it != null } }
    }
}
