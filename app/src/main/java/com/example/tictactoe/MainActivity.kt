package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.tictactoe.model.Game
import com.example.tictactoe.model.Player

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameScreen()
        }
    }
}

@Composable
fun GameScreen() {
    val player1 = Player("Player 1", 'X')
    val player2 = Player("Player 2", 'O')
    val game = Game(player1, player2)

    val winTestResult = getWinTestResult(game, player1, player2)
    val tieTestResult = getTieTestResult(game, player1, player2)

    Column {
        Text("--- Testing Win Condition ---")
        Text(winTestResult)
        Text("---------------------------\n")

        Text("--- Testing Tie Condition ---")
        Text(tieTestResult)
        Text("-------------------------\n")
    }
}

fun getWinTestResult(game: Game, player1: Player, player2: Player): String {
    val winningBoard: Array<Array<Player?>> = arrayOf(
        arrayOf(player1, player2, player1),
        arrayOf(player1, player2, player2),
        arrayOf(player1, player1, player1)
    )
    val winner = game.checkWin(winningBoard)
    return if (winner != null) {
        "Winner found: ${winner.name}" // Expected: Player 1
    } else {
        "Error: No winner was found on the winning board."
    }
}

fun getTieTestResult(game: Game, player1: Player, player2: Player): String {
    val tieBoard: Array<Array<Player?>> = arrayOf(
        arrayOf(player1, player2, player1),
        arrayOf(player1, player2, player2),
        arrayOf(player2, player1, player1)
    )
    val winner = game.checkWin(tieBoard)
    val isFull = game.isBoardFull(tieBoard)
    return if (winner == null && isFull) {
        "Result: The game is a tie!"
    } else if (winner != null) {
        "Error: A winner was found on the tie board."
    } else if (!isFull) {
        "Error: The tie board is not full."
    } else {
        "An unexpected error occurred during the tie test."
    }
}
