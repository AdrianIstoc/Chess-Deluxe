package com.example.chessdelux

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.chessdelux.game.*
import android.view.View
import android.widget.TextView


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menu_layout)

        val playButton = findViewById<Button>(R.id.play_button)
        val howToPlayButton = findViewById<Button>(R.id.how_to_play_button)
        val exitButton = findViewById<Button>(R.id.exit_button)

        playButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            setContentView(R.layout.play_layout)

            val localButton = findViewById<Button>(R.id.local_button)
            val computerButton = findViewById<Button>(R.id.computer_button)

            localButton.setOnClickListener {
                startGame(false, 1)
            }

            computerButton.setOnClickListener {
                setContentView(R.layout.difficulty_menu)
                val d1Button = findViewById<Button>(R.id.d1_button)
                val d3Button = findViewById<Button>(R.id.d3_button)
                val d5Button = findViewById<Button>(R.id.d5_button)
                val d7Button = findViewById<Button>(R.id.d7_button)
                val d9Button = findViewById<Button>(R.id.d9_button)

                d1Button.setOnClickListener {
                    startGame(true, 1)
                }

                d3Button.setOnClickListener {
                    startGame(true, 3)
                }

                d5Button.setOnClickListener {
                    startGame(true, 5)
                }

                d7Button.setOnClickListener {
                    startGame(true, 7)
                }

                d9Button.setOnClickListener {
                    startGame(true, 9)
                }

            }

        }


        howToPlayButton.setOnClickListener{

        }

        exitButton.setOnClickListener {
            finish()
        }
    }

    fun createPopUpView(): View {
        return layoutInflater.inflate(R.layout.popup_layout, null)
    }

    private fun startGame(computerPlayer: Boolean, difficulty: Int){
        setContentView(R.layout.activity_game)
        try {
            val backButton = findViewById<Button>(R.id.back)
            val restartButton = findViewById<Button>(R.id.restart)

            val game = Game()
            val humanPlayer = HumanPlayer(true)
            val player2 = if(computerPlayer)
                ComputerPlayer(false).apply { setDifficulty(difficulty) }
            else
                HumanPlayer(false)

            val chessboard = findViewById<GridLayout>(R.id.chess_board)
            val cellSize = resources.displayMetrics.widthPixels / 8

            game.setCellSize(cellSize)
            game.initialize(humanPlayer, player2)
            game.renderGameBoard(chessboard, this)
            game.proceedWithTheGame(chessboard, this)

            backButton.setOnClickListener {
                finish()
                startActivity(intent)
            }

            restartButton.setOnClickListener {
                game.initialize(humanPlayer, player2)
                game.renderGameBoard(chessboard, this)
                game.proceedWithTheGame(chessboard, this)

                val text = findViewById<TextView>(R.id.game_text)
                text.text = null
                text.visibility = View.INVISIBLE
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("GameBoardError", "Error initializing game board: ${e.message}")
        }
    }
}



