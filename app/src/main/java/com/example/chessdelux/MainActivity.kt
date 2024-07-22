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
                    startGame(true, 2)
                }

                d3Button.setOnClickListener {
                    startGame(true, 3)
                }

                d5Button.setOnClickListener {
                    startGame(true, 4)
                }

                d7Button.setOnClickListener {
                    startGame(true, 5)
                }

                d9Button.setOnClickListener {
                    startGame(true, 6)
                }

            }

        }


        howToPlayButton.setOnClickListener{
            setContentView(R.layout.tutorial_layout)

            var backButton = findViewById<Button>(R.id.back_button)

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
            val paladinButton = findViewById<Button>(R.id.paladin_button)
            val riverButton = findViewById<Button>(R.id.river_button)
            val capricornButton = findViewById<Button>(R.id.capricorn_button)

            backButton.setOnClickListener {
                finish()
                startActivity(intent)
            }

            pawnButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                var text = findViewById<TextView>(R.id.tutorial_text)
                text.text = getString(R.string.pawn_tutorial_text)
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

                    text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            knightButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                var text = findViewById<TextView>(R.id.tutorial_text)
                text.text = getString(R.string.knight_tutorial_text)
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

                    text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            bishopButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                var text = findViewById<TextView>(R.id.tutorial_text)
                text.text = getString(R.string.bishop_tutorial_text)
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

                    text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            rookButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                var text = findViewById<TextView>(R.id.tutorial_text)
                text.text = getString(R.string.rook_tutorial_text)
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

                    text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            queenButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                var text = findViewById<TextView>(R.id.tutorial_text)
                text.text = getString(R.string.queen_tutorial_text)
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

                    text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            kingButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                var text = findViewById<TextView>(R.id.tutorial_text)
                text.text = getString(R.string.king_tutorial_text)
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

                    text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            fortressButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                var text = findViewById<TextView>(R.id.tutorial_text)
                text.text = getString(R.string.fortress_tutorial_text)
                text.visibility = View.VISIBLE

                val game = Game()
                val humanPlayer = HumanPlayer(true)
                val player2 = HumanPlayer(false)

                val chessboard = findViewById<GridLayout>(R.id.chess_board)
                val cellSize = resources.displayMetrics.widthPixels / 8

                game.setCellSize(cellSize)
                game.initialize(humanPlayer, player2)
                game.getBoard().fortressTutorial()
                game.renderGameBoard(chessboard, this)
                game.proceedWithTheGame(chessboard, this)

                backButton.setOnClickListener {
                    finish()
                    startActivity(intent)
                }

                restartButton.setOnClickListener {
                    game.initialize(humanPlayer, player2)
                    game.getBoard().fortressTutorial()
                    game.renderGameBoard(chessboard, this)
                    game.proceedWithTheGame(chessboard, this)

                    text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            thiefButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                var text = findViewById<TextView>(R.id.tutorial_text)
                text.text = getString(R.string.thief_tutorial_text)
                text.visibility = View.VISIBLE

                val game = Game()
                val humanPlayer = HumanPlayer(true)
                val player2 = HumanPlayer(false)

                val chessboard = findViewById<GridLayout>(R.id.chess_board)
                val cellSize = resources.displayMetrics.widthPixels / 8

                game.setCellSize(cellSize)
                game.initialize(humanPlayer, player2)
                game.getBoard().thiefTutorial()
                game.renderGameBoard(chessboard, this)
                game.proceedWithTheGame(chessboard, this)

                backButton.setOnClickListener {
                    finish()
                    startActivity(intent)
                }

                restartButton.setOnClickListener {
                    game.initialize(humanPlayer, player2)
                    game.getBoard().thiefTutorial()
                    game.renderGameBoard(chessboard, this)
                    game.proceedWithTheGame(chessboard, this)

                    text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            assassinButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                var text = findViewById<TextView>(R.id.tutorial_text)
                text.text = getString(R.string.assassin_tutorial_text)
                text.visibility = View.VISIBLE

                val game = Game()
                val humanPlayer = HumanPlayer(true)
                val player2 = HumanPlayer(false)

                val chessboard = findViewById<GridLayout>(R.id.chess_board)
                val cellSize = resources.displayMetrics.widthPixels / 8

                game.setCellSize(cellSize)
                game.initialize(humanPlayer, player2)
                game.getBoard().assassinTutorial()
                game.renderGameBoard(chessboard, this)
                game.proceedWithTheGame(chessboard, this)

                backButton.setOnClickListener {
                    finish()
                    startActivity(intent)
                }

                restartButton.setOnClickListener {
                    game.initialize(humanPlayer, player2)
                    game.getBoard().assassinTutorial()
                    game.renderGameBoard(chessboard, this)
                    game.proceedWithTheGame(chessboard, this)

                    text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            cardinalButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                var text = findViewById<TextView>(R.id.tutorial_text)
                text.text = getString(R.string.cardinal_tutorial_text)
                text.visibility = View.VISIBLE

                val game = Game()
                val humanPlayer = HumanPlayer(true)
                val player2 = HumanPlayer(false)

                val chessboard = findViewById<GridLayout>(R.id.chess_board)
                val cellSize = resources.displayMetrics.widthPixels / 8

                game.setCellSize(cellSize)
                game.initialize(humanPlayer, player2)
                game.getBoard().cardinalTutorial()
                game.renderGameBoard(chessboard, this)
                game.proceedWithTheGame(chessboard, this)

                backButton.setOnClickListener {
                    finish()
                    startActivity(intent)
                }

                restartButton.setOnClickListener {
                    game.initialize(humanPlayer, player2)
                    game.getBoard().cardinalTutorial()
                    game.renderGameBoard(chessboard, this)
                    game.proceedWithTheGame(chessboard, this)

                    text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            paladinButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                var text = findViewById<TextView>(R.id.tutorial_text)
                text.text = getString(R.string.paladin_tutorial_text)
                text.visibility = View.VISIBLE

                val game = Game()
                val humanPlayer = HumanPlayer(true)
                val player2 = HumanPlayer(false)

                val chessboard = findViewById<GridLayout>(R.id.chess_board)
                val cellSize = resources.displayMetrics.widthPixels / 8

                game.setCellSize(cellSize)
                game.initialize(humanPlayer, player2)
                game.getBoard().paladinTutorial()
                game.renderGameBoard(chessboard, this)
                game.proceedWithTheGame(chessboard, this)

                backButton.setOnClickListener {
                    finish()
                    startActivity(intent)
                }

                restartButton.setOnClickListener {
                    game.initialize(humanPlayer, player2)
                    game.getBoard().paladinTutorial()
                    game.renderGameBoard(chessboard, this)
                    game.proceedWithTheGame(chessboard, this)

                    text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            riverButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                var text = findViewById<TextView>(R.id.tutorial_text)
                text.text = getString(R.string.river_tutorial_text)
                text.visibility = View.VISIBLE

                val game = Game()
                val humanPlayer = HumanPlayer(true)
                val player2 = HumanPlayer(false)

                val chessboard = findViewById<GridLayout>(R.id.chess_board)
                val cellSize = resources.displayMetrics.widthPixels / 8

                game.setCellSize(cellSize)
                game.initialize(humanPlayer, player2)
                game.getBoard().riverTutorial()
                game.renderGameBoard(chessboard, this)
                game.proceedWithTheGame(chessboard, this)

                backButton.setOnClickListener {
                    finish()
                    startActivity(intent)
                }

                restartButton.setOnClickListener {
                    game.initialize(humanPlayer, player2)
                    game.getBoard().riverTutorial()
                    game.renderGameBoard(chessboard, this)
                    game.proceedWithTheGame(chessboard, this)

                    text = findViewById<TextView>(R.id.game_text)
                    text.text = null
                    text.visibility = View.INVISIBLE
                }
            }

            capricornButton.setOnClickListener {
                setContentView(R.layout.activity_game)

                backButton = findViewById<Button>(R.id.back)
                val restartButton = findViewById<Button>(R.id.restart)

                var text = findViewById<TextView>(R.id.tutorial_text)
                text.text = getString(R.string.capricorn_queen_tutorial_text)
                text.visibility = View.VISIBLE

                val game = Game()
                val humanPlayer = HumanPlayer(true)
                val player2 = HumanPlayer(false)

                val chessboard = findViewById<GridLayout>(R.id.chess_board)
                val cellSize = resources.displayMetrics.widthPixels / 8

                game.setCellSize(cellSize)
                game.initialize(humanPlayer, player2)
                game.getBoard().capricornTutorial()
                game.renderGameBoard(chessboard, this)
                game.proceedWithTheGame(chessboard, this)

                backButton.setOnClickListener {
                    finish()
                    startActivity(intent)
                }

                restartButton.setOnClickListener {
                    game.initialize(humanPlayer, player2)
                    game.getBoard().capricornTutorial()
                    game.renderGameBoard(chessboard, this)
                    game.proceedWithTheGame(chessboard, this)

                    text = findViewById<TextView>(R.id.game_text)
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



