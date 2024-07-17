package com.example.chessdelux

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
            setContentView(R.layout.tutorial_layout)

            val backButton = findViewById<Button>(R.id.back_button)

            val pawnButton = findViewById<Button>(R.id.pawn_button)
            val knightButton = findViewById<Button>(R.id.knight_button)
            val bishopButton = findViewById<Button>(R.id.bishop_button)
            val rookButton = findViewById<Button>(R.id.rook_button)
            val queenButton = findViewById<Button>(R.id.queen_button)
            val kingButton = findViewById<Button>(R.id.king_button)
            val fortressButton = findViewById<Button>(R.id.fortress_button)
            val thiefButton = findViewById<Button>(R.id.thief_button)
            val assassinButton = findViewById<Button>(R.id.assassin_button)
            val cardinalButton = findViewById<Button>(R.id.cardinal_button)

            backButton.setOnClickListener {
                finish()
                startActivity(intent)
            }

            pawnButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                val backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                val text = findViewById<TextView>(R.id.tutorial_text)
                text.text = "Pawn Tutorial:\n\n Pawns can move only forwords one space at a time. They can move two spaces on their first move. The pawns can capture other pieces diagonally. Once a pawn reaches the end of the board, it is changed into a knight, bishop, rook or queen, it is the player's choice. Try it out!"
                text.visibility = View.VISIBLE

                val game = Game()
                val humanPlayer = HumanPlayer(true)
                val player2 = HumanPlayer(false)

                val chessboard = findViewById<GridLayout>(R.id.chess_board)
                val cellSize = resources.displayMetrics.widthPixels / 8

                game.setCellSize(cellSize)
                game.initialize(humanPlayer, player2)
                game.getBoard().pawnTutorial()
                game.renderGameBoard(chessboard, this)
                game.proceedWithTheGame(chessboard, this)

                backButton.setOnClickListener {
                    finish()
                    startActivity(intent)
                }

                restartButton.setOnClickListener {
                    game.initialize(humanPlayer, player2)
                    game.getBoard().pawnTutorial()
                    game.renderGameBoard(chessboard, this)
                    game.proceedWithTheGame(chessboard, this)

                    val text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            knightButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                val backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                val text = findViewById<TextView>(R.id.tutorial_text)
                text.text = "Knight Tutorial:\n\n Knights can move and capture other pieces in an L shape. The knights can jump over other pieces. Try it out!"
                text.visibility = View.VISIBLE

                val game = Game()
                val humanPlayer = HumanPlayer(true)
                val player2 = HumanPlayer(false)

                val chessboard = findViewById<GridLayout>(R.id.chess_board)
                val cellSize = resources.displayMetrics.widthPixels / 8

                game.setCellSize(cellSize)
                game.initialize(humanPlayer, player2)
                game.getBoard().knightTutorial()
                game.renderGameBoard(chessboard, this)
                game.proceedWithTheGame(chessboard, this)

                backButton.setOnClickListener {
                    finish()
                    startActivity(intent)
                }

                restartButton.setOnClickListener {
                    game.initialize(humanPlayer, player2)
                    game.getBoard().knightTutorial()
                    game.renderGameBoard(chessboard, this)
                    game.proceedWithTheGame(chessboard, this)

                    val text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            bishopButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                val backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                val text = findViewById<TextView>(R.id.tutorial_text)
                text.text = "Bishop Tutorial:\n\n Bishops can move and capture other pieces diagonally. Try it out!"
                text.visibility = View.VISIBLE

                val game = Game()
                val humanPlayer = HumanPlayer(true)
                val player2 = HumanPlayer(false)

                val chessboard = findViewById<GridLayout>(R.id.chess_board)
                val cellSize = resources.displayMetrics.widthPixels / 8

                game.setCellSize(cellSize)
                game.initialize(humanPlayer, player2)
                game.getBoard().bishopTutorial()
                game.renderGameBoard(chessboard, this)
                game.proceedWithTheGame(chessboard, this)

                backButton.setOnClickListener {
                    finish()
                    startActivity(intent)
                }

                restartButton.setOnClickListener {
                    game.initialize(humanPlayer, player2)
                    game.getBoard().bishopTutorial()
                    game.renderGameBoard(chessboard, this)
                    game.proceedWithTheGame(chessboard, this)

                    val text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            rookButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                val backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                val text = findViewById<TextView>(R.id.tutorial_text)
                text.text = "Rook Tutorial:\n\n Rooks can move and capture other pieces horizontally and vertically. Try it out!"
                text.visibility = View.VISIBLE

                val game = Game()
                val humanPlayer = HumanPlayer(true)
                val player2 = HumanPlayer(false)

                val chessboard = findViewById<GridLayout>(R.id.chess_board)
                val cellSize = resources.displayMetrics.widthPixels / 8

                game.setCellSize(cellSize)
                game.initialize(humanPlayer, player2)
                game.getBoard().rookTutorial()
                game.renderGameBoard(chessboard, this)
                game.proceedWithTheGame(chessboard, this)

                backButton.setOnClickListener {
                    finish()
                    startActivity(intent)
                }

                restartButton.setOnClickListener {
                    game.initialize(humanPlayer, player2)
                    game.getBoard().rookTutorial()
                    game.renderGameBoard(chessboard, this)
                    game.proceedWithTheGame(chessboard, this)

                    val text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            queenButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                val backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                val text = findViewById<TextView>(R.id.tutorial_text)
                text.text = "Queen Tutorial:\n\n Queens can move and capture other pieces horizontally, vertically and diagonally. Try it out!"
                text.visibility = View.VISIBLE

                val game = Game()
                val humanPlayer = HumanPlayer(true)
                val player2 = HumanPlayer(false)

                val chessboard = findViewById<GridLayout>(R.id.chess_board)
                val cellSize = resources.displayMetrics.widthPixels / 8

                game.setCellSize(cellSize)
                game.initialize(humanPlayer, player2)
                game.getBoard().queenTutorial()
                game.renderGameBoard(chessboard, this)
                game.proceedWithTheGame(chessboard, this)

                backButton.setOnClickListener {
                    finish()
                    startActivity(intent)
                }

                restartButton.setOnClickListener {
                    game.initialize(humanPlayer, player2)
                    game.getBoard().queenTutorial()
                    game.renderGameBoard(chessboard, this)
                    game.proceedWithTheGame(chessboard, this)

                    val text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            kingButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                val backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                val text = findViewById<TextView>(R.id.tutorial_text)
                text.text = "King Tutorial:\n\n Kings are the center of this game. Kings can't be captured by other pieces, but they can be put in check. If a king is in check and there are no moves available, the king is in checkmate and the game is over. Kings move and capture pieces in any direction one space at a time. Kings can't move on a space if that space is in check. Protect your king at all costs! Also kings can castle. Castling can be achived by moving the king two spaces towords the rook and the rook will be placed next to the king in the opposite direction of the king's move. Castling is not allowed if the king or the rook moved from their initial spots or if the king is in check or if the spaces that the king moves through are in check. Try it out!"
                text.visibility = View.VISIBLE

                val game = Game()
                val humanPlayer = HumanPlayer(true)
                val player2 = HumanPlayer(false)

                val chessboard = findViewById<GridLayout>(R.id.chess_board)
                val cellSize = resources.displayMetrics.widthPixels / 8

                game.setCellSize(cellSize)
                game.initialize(humanPlayer, player2)
                game.getBoard().kingTutorial()
                game.renderGameBoard(chessboard, this)
                game.proceedWithTheGame(chessboard, this)

                backButton.setOnClickListener {
                    finish()
                    startActivity(intent)
                }

                restartButton.setOnClickListener {
                    game.initialize(humanPlayer, player2)
                    game.getBoard().kingTutorial()
                    game.renderGameBoard(chessboard, this)
                    game.proceedWithTheGame(chessboard, this)

                    val text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

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



