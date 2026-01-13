package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.model.Player
import com.example.tictactoe.ui.TicTacToeViewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<TicTacToeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun TicTacToeScreen(viewModel: TicTacToeViewModel) {
    val board = viewModel.board
    val currentPlayer = viewModel.currentPlayer
    val winner = viewModel.winner
    val isDraw = viewModel.isDraw

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val statusText = when {
                winner != null -> "🎉 ${winner.name} wins! 🎉"
                isDraw -> "It's a Tie! 🧐"
                else -> "${currentPlayer.name}'s turn"
            }

            Text(
                text = statusText,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            GameBoard(board = board, onClick = { row, col -> viewModel.onCellClicked(row, col) })

            if (winner != null || isDraw) {
                Button(
                    onClick = { viewModel.playAgain() },
                    modifier = Modifier.padding(top = 24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Play Again", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
fun GameBoard(board: Array<Array<Player?>>, onClick: (row: Int, col: Int) -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .aspectRatio(1f)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            for (i in 0..2) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    for (j in 0..2) {
                        Cell(symbol = board[i][j]?.symbol, onClick = { onClick(i, j) }, modifier = Modifier.weight(1f))
                    }
                }
            }
        }
        DrawGridLines()
    }
}

@Composable
fun DrawGridLines() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val lineStrokeWidth = 3.dp.toPx()
        val lineColor = Color.Gray

        drawLine(lineColor, start = Offset(size.width / 3, 0f), end = Offset(size.width / 3, size.height), strokeWidth = lineStrokeWidth)
        drawLine(lineColor, start = Offset(2 * size.width / 3, 0f), end = Offset(2 * size.width / 3, size.height), strokeWidth = lineStrokeWidth)
        drawLine(lineColor, start = Offset(0f, size.height / 3), end = Offset(size.width, size.height / 3), strokeWidth = lineStrokeWidth)
        drawLine(lineColor, start = Offset(0f, 2 * size.height / 3), end = Offset(size.width, 2 * size.height / 3), strokeWidth = lineStrokeWidth)
    }
}

@Composable
fun Cell(symbol: Char?, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (symbol != null) {
            val (color, fontWeight) = if (symbol == 'X') {
                MaterialTheme.colorScheme.primary to FontWeight.Bold
            } else {
                MaterialTheme.colorScheme.secondary to FontWeight.Normal
            }

            Text(
                text = symbol.toString(),
                style = TextStyle(fontSize = 56.sp, fontWeight = fontWeight),
                color = color
            )
        }
    }
}
