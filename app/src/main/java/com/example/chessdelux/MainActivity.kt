package com.example.chessdelux

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.chessdelux.game.*
import android.app.AlertDialog
import android.view.View
import android.widget.TextView


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val playButton = findViewById<Button>(R.id.play_button)
        val creditsButton = findViewById<Button>(R.id.credits_button)
        val howToPlayButton = findViewById<Button>(R.id.how_to_play_button)

        playButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)


            setContentView(R.layout.activity_game)
            try {
                val backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                val game = Game()
                val humanPlayer = HumanPlayer(true)
                val computerPlayer = ComputerPlayer(false)

                val chessboard = findViewById<GridLayout>(R.id.chess_board)
                val cellSize = resources.displayMetrics.widthPixels / 8

                game.setCellSize(cellSize)
                game.initialize(humanPlayer, computerPlayer)
                game.renderGameBoard(chessboard, this)
                game.proceedWithTheGame(chessboard, this)

                backButton.setOnClickListener {
                    finish()
                    startActivity(intent)
                }

                restartButton.setOnClickListener{
                    game.initialize(humanPlayer, computerPlayer)
                    game.renderGameBoard(chessboard, this)
                    game.proceedWithTheGame(chessboard, this)

                    val text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }

            }
            catch (e: Exception) {
                e.printStackTrace()
                Log.e("GameBoardError", "Error initializing game board: ${e.message}")
            }

        }
    }

    fun createPopUpView(): View {
        return layoutInflater.inflate(R.layout.popup_layout, null)
    }
}



