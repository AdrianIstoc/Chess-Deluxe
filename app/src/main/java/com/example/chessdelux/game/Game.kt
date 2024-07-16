package com.example.chessdelux.game

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.chessdelux.MainActivity
import com.example.chessdelux.R
import com.example.chessdelux.board.*
import com.example.chessdelux.pieces.*
import kotlin.time.measureTime

class Game {
    private val players = arrayOfNulls<Player>(2)  // array of players
    private var board: Board = Board()                  // board; is filled with cells
    private lateinit var currentTurn: Player            // current player
    private var selectedSpot: Spot? = null              // the spot that has been currently selected
    private var currentPieceSpot: Spot? = null          // the spot of the piece that is currently selected
    private var possibleMoves = mutableListOf<Spot>()   // list of possible moves that the current piece can make
    private var possibleKills = mutableListOf<Spot>()   // list of possible kills that the current piece can make
    private val movesPlayed = mutableListOf<Move>()     // list of moves that the players have done
    private var cellSize = 0                            // cell size for the chessboard
    private var gameover = false                        // true if the game is over

    fun initialize(p1: Player, p2: Player) {            // initialize the game
        players[0] = p1
        players[1] = p2

        board.resetBoard()                              // reset the board for a fresh start
        //board.testWin()

        currentTurn = if (p1.isWhiteSide()) {           // set the current player
            p1
        } else {
            p2
        }
        gameover = false
        possibleMoves.clear()                           // clear the list of possible moves
        possibleKills.clear()                           // clear the list of possible kills
        movesPlayed.clear()                             // clear the list of moves that the players have done
    }

    fun setCellSize(cellSize: Int) {
        this.cellSize = cellSize
    }

    private fun setSelectedSpot(i: Int, j: Int) {       // sets the selected spot
        selectedSpot = board.getBox(i, j)
    }

    // update the current piece spot
    private fun updateCurrentPieceSpot(context: MainActivity, chessboard: GridLayout) {
        currentPieceSpot?.getPiece()?.setSelected(false)    // deselect the piece
        currentPieceSpot = null                             // reset the current piece spot


        // check if selected spot has a piece of the current player
        if (selectedSpot != null && selectedSpot?.getPiece()?.isWhite() == currentTurn.isWhiteSide()) {
            currentPieceSpot = selectedSpot                 // set current piece spot as the selected spot
            val currentPiece = currentPieceSpot?.getPiece()
            if(currentPiece != null) {
                currentPiece.setSelected(true) // set the piece as selected
                if(currentPiece.readyToEvolve()){
                    currentPieceSpot?.let { currentPiece.checkIfPieceEvolves(it, context, cellSize, board, chessboard) }
                }
            }
        }
    }


    // renders the chessboard and updates it
    fun renderGameBoard(chessboard: GridLayout, context: MainActivity) {
        val white = ContextCompat.getColor(context, R.color.white)
        val black = ContextCompat.getColor(context, R.color.cheese)

        //removes all the squares of the board
        chessboard.removeAllViews()

        // render the board by creating a new one
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                val cell = ImageView(context)
                cell.layoutParams = GridLayout.LayoutParams().apply {
                    width = cellSize
                    height = cellSize
                    setMargins(1, 1, 1, 1)
                }
                cell.setBackgroundColor(if ((i + j) % 2 == 0) white else black)
                chessboard.addView(cell)

            }
        }
        // renders the pieces again for some reason
        renderPieces(chessboard, board)
    }

    // listens for user activity and updates the game based on it
    fun proceedWithTheGame(chessboard: GridLayout, context: MainActivity){

        if (!gameover)
            for(i in 0 until 8){
                for(j in 0 until 8){
                    val cell = chessboard.getChildAt(i*8+j) as ImageView

                    cell.setOnClickListener {
                        // set the selected spot
                        try {
                            setSelectedSpot(i, j)
                        } catch (e: Exception) {
                            Log.e("ObscureMove", "setSelectedSpot problem e -> ${e.message}")
                        }
                        // check if the selected spot indicated a player moving a piece
                        try {
                            if(currentPieceSpot != null)
                                checkIfPlayerMoves(context, chessboard, currentPieceSpot!!, selectedSpot!!)
                        } catch (e: Exception) {
                            Log.e("ObscureMove", "checkIfPlayerMoves problem e -> ${e.message}")
                        }
                        // update the game
                        try {
                            updateCurrentPieceSpot(context, chessboard)
                        } catch (e: Exception) {
                            Log.e("ObscureMove", "updateCurrentPieceSpot problem e -> ${e.message}")
                        }
                        // selects the current piece spot as the selected spot
                        try {
                            setPossibleMoves()
                        } catch (e: Exception) {
                            Log.e("ObscureMove", "setPossibleMoves problem e -> ${e.message}")
                        }
                        // updates the colors of the chessboard to indicate the possible moves of the selected piece
                        changeBoardOnSelection(chessboard, context)

                        // renders the pieces
                        renderPieces(chessboard, board)

                        // check if any player won
                        try{
                            checkWin(context)
                        }catch (e: Exception){
                            Log.e("ObscureMove", "checkWin problem e -> ${e.message}")
                        }

                        if(currentTurn is ComputerPlayer){
                            Handler(Looper.getMainLooper()).postDelayed({
                                try {
                                    val move = (this.currentTurn as ComputerPlayer).minimax(
                                            this,
                                            board,
                                            (currentTurn as ComputerPlayer).getDifficulty(),
                                            Int.MIN_VALUE,
                                            Int.MAX_VALUE,
                                            currentTurn.isWhiteSide(),
                                            currentTurn.isWhiteSide()
                                        ).first
                                    if (move != null) {
                                        val start = move.first
                                        val end = move.second
                                        checkIfPlayerMoves(context, chessboard, start, end)

                                    }
                                } catch (e: Exception) {
                                    Log.e("ObscureMove", "minimax problem e -> ${e.message}")
                                }
                                Log.i("ObscureMove", "Computer turn done")
                                changeBoardOnSelection(chessboard, context)
                                renderPieces(chessboard, board)
                                checkWin(context)
                            }, 500)
                        }
                    }
                }
            }
    }

    private fun checkWin(context: MainActivity) {
        val white = currentTurn.isWhiteSide()
        val king = board.getKingSpot(white).getPiece() as King

        var options = 0
        for (i in 0 until 8)
            for (j in 0 until 8){
                val piece = board.getBox(i, j).getPiece()
                if(piece != null && piece.isWhite() == white){
                    var moves = piece.moveOptions(board, board.getBox(i, j))
                    moves = validMoves(moves, board.getBox(i, j), board, currentTurn.isWhiteSide())
                    var kills = piece.killOptions(board, board.getBox(i, j))
                    kills = validKills(kills, board.getBox(i, j), board, currentTurn.isWhiteSide())
                    options+=moves.size+kills.size
                }
            }

        if(options == 0){
            king.checkIfKingInCheck(board, board.getKingSpot(white))
            val textView = context.findViewById<TextView>(R.id.game_text)
            if(king.isInCheck())
                textView.text = if (!white) "White won" else "Black won"
            else
                textView.text = if (white) "Stalemate/Draw" else "Stalemate/Draw"
            textView.visibility = View.VISIBLE
            gameover = true
        }
    }

    // check if the selected spot is one of the possible moves of the selected piece
    private fun checkValidMove(): Boolean {
        for(spot in possibleMoves + possibleKills)
            if(selectedSpot == spot)
                return true
        return false
    }



    // check if player move and make the move
    private fun checkIfPlayerMoves(context: MainActivity, chessboard: GridLayout, start: Spot, end: Spot) {
        if((checkValidMove() && currentTurn.isHumanPlayer()) || currentTurn is ComputerPlayer){           // check if the selected spot is one of the move options of the current piece
            val piece = start.getPiece()
            val endPiece = end.getPiece()
            val endPieceValue = endPiece?.getValue() ?: 0


            if (piece != null) {
                resetPawnSkipped(piece.isWhite())
            }
            // check for important moves
            checkImportantPieceMove(start)

            if(piece is Pawn) {
                // check if the pawn moved two spaces
                piece.checkPawnSkipped(start, end, board, currentTurn.isWhiteSide())
                // check if the pawn can make an en passant and sets the opponent pawn as killed
                piece.checkIfEnPassant(start, end, board)
            }


            // check if the king is castling
            // if so, move the king and the rook properly
            if(piece is King)
                piece.checkIfCastling(start, end, board)

            if(piece is Rook)
                piece.checkIfFortressing(start, end)

            if(piece is Thief)
                piece.checkIfStealing(start, end, board)

            // move piece
            board.movePiece(start, end)
            piece?.addExp(endPieceValue)
            setMoveIndication(start, end)
            addExpPerTurn()

            // change the turn of the players
            currentTurn = if (currentTurn.isWhiteSide()) {
                players[1]!!
            } else {
                players[0]!!
            }

            if(piece is Pawn)
                piece.checkIfPawnPromoting(end, context, cellSize, board, chessboard)
        }
    }

    private fun addExpPerTurn() {
        for (i in 0 until 8)
            for (j in 0 until 8){
                val piece = board.getBox(i, j).getPiece()
                piece?.addExp(1)
            }
    }


    private fun resetPawnSkipped(white: Boolean){
        // set all pawns as they didn't move two spots
        for (i in 0 until 8)
            for (j in 0 until 8){
                val piece = board.getBox(i, j).getPiece()
                if(piece is Pawn && piece.isWhite() == white){
                    piece.setPawnSkipped(false)
                }
            }
    }

    private fun setMoveIndication(start: Spot, end: Spot) {
        for (i in 0 until 8)
            for (j in 0 until 8)
                board.getBox(i, j).setMovedSpot(false)


        start.setMovedSpot(true)
        end.setMovedSpot(true)
    }

    // set the spots that show to possible moves of a selected piece
    private fun setPossibleMoves(){
        // set the list of possible moves of the current piece
        possibleMoves = mutableListOf()
        possibleKills = mutableListOf()
        if(currentPieceSpot?.getPiece() != null) {
            // get the possible moves
            possibleMoves = currentPieceSpot?.getPiece()?.moveOptions(board, currentPieceSpot!!) as MutableList<Spot>
            possibleMoves = validMoves(possibleMoves, currentPieceSpot!!, board, currentTurn.isWhiteSide())
            // get the possible kills
            possibleKills = currentPieceSpot?.getPiece()?.killOptions(board, currentPieceSpot!!) as MutableList<Spot>
            possibleKills = validKills(possibleKills, currentPieceSpot!!, board, currentTurn.isWhiteSide())
        }

        // deselect all spots
        for (i in 0 until 8)
            for (j in 0 until 8) {
                board.getBox(i, j).setSelectableSpot(false)
                board.getBox(i, j).setKillableSpot(false)
            }

        // select the spots that represent the moves of the current piece
        for (spot in possibleMoves)
            board.getBox(spot.getX(), spot.getY()).setSelectableSpot(true)

        for (spot in possibleKills)
            board.getBox(spot.getX(), spot.getY()).setKillableSpot(true)

    }



    // change the colors of the chessboard to show the current piece movement
    private fun changeBoardOnSelection(chessboard: GridLayout, context: MainActivity) {
        val white = ContextCompat.getColor(context, R.color.white)
        val black = ContextCompat.getColor(context, R.color.cheese)
        val whiteGreen = ContextCompat.getColor(context, R.color.green)
        val blackGreen = ContextCompat.getColor(context, R.color.mold_cheese)
        val whiteRed = ContextCompat.getColor(context, R.color.red)
        val blackRed = ContextCompat.getColor(context, R.color.cursed_cheese)

        for (i in 0 until 8)
            for (j in 0 until 8) {
                if((i+j) % 2 == 0) {
                    // white
                    // if the spot is not on the list it stays white
                    //if(possibleMoves.size == 0 && possibleKills.size == 0)
                        chessboard.getChildAt(i*8+j).setBackgroundColor(white)
                    if(board.getBox(i,j).isMovedSpot())
                        chessboard.getChildAt(i*8+j).setBackgroundColor(whiteRed)
                    if (board.getBox(i,j).isSelectableSpot() || board.getBox(i,j).isKillableSpot()) {
                        // else it changes into green
                        for (pair in possibleMoves + possibleKills)
                            if (pair.getX() == i && pair.getY() == j) {
                                chessboard.getChildAt(i * 8 + j).setBackgroundColor(whiteGreen)
                                break
                            } else
                                chessboard.getChildAt(i * 8 + j).setBackgroundColor(white)
                    }
                }
                else {
                    // black
                    //if the spot is not on the list it stays black
                    //if(possibleMoves.size == 0 && possibleKills.size == 0)
                        chessboard.getChildAt(i*8+j).setBackgroundColor(black)
                    if(board.getBox(i,j).isMovedSpot())
                        chessboard.getChildAt(i*8+j).setBackgroundColor(blackRed)
                    if(board.getBox(i,j).isSelectableSpot() || board.getBox(i,j).isKillableSpot()){
                        // else it changes into a greenish black
                        for(pair in possibleMoves + possibleKills)
                            if(pair.getX() == i && pair.getY() == j) {
                                chessboard.getChildAt(i * 8 + j).setBackgroundColor(blackGreen)
                                break
                            }
                            else
                                chessboard.getChildAt(i*8+j).setBackgroundColor(black)
                    }
                }
            }
    }
}


// make a piece
fun makePiece(pieceType: PieceType, white: Boolean): Piece {
    return when (pieceType) {
        PieceType.ROOK -> if (white) Rook(true) else Rook(false)
        PieceType.KNIGHT -> if (white) Knight(true) else Knight(false)
        PieceType.BISHOP -> if (white) Bishop(true) else Bishop(false)
        PieceType.QUEEN -> if (white) Queen(true) else Queen(false)
        PieceType.THIEF -> if (white) Thief(true) else Thief(false)
        else -> if (white) Pawn(true) else Pawn(false)
    }
}

// render the pieces on the board
fun renderPieces(chessboard: GridLayout, board: Board) {
    for (i in 0 until 8) {
        for (j in 0 until 8) {
            val cell = chessboard.getChildAt(i * 8 + j) as ImageView
            val piece = board.getBox(i, j).getPiece()
            // check if the piece exists
            if (piece != null && !piece.isKilled()) {
                piece.imageResource?.let { cell.setImageResource(it) }
            }
            else {
                cell.setImageResource(0)
            }
        }
    }

}

// check if any piece, that has a ruling
// based on the fact that it didn't moved,
// did indeed move and set that piece as moved
fun checkImportantPieceMove(spot: Spot) {
    when (spot.getPiece()?.getType()) {
        PieceType.KING -> {
            val king = spot.getPiece() as King
            king.setKingMoved()
        }
        PieceType.PAWN -> {
            val pawn = spot.getPiece() as Pawn
            pawn.setPawnMoved()
        }
        PieceType.ROOK -> {
            val rook = spot.getPiece() as Rook
            rook.setRookMoved()
        }
        else -> {}
    }
}

fun validMoves(options: MutableList<Spot>, spot: Spot, board: Board, currentTurn: Boolean): MutableList<Spot> {
    val moves = mutableListOf<Spot>()
    for (option in options) {
        if (!checkIfMovePutsPlayerInCheck(spot, option, board, currentTurn)) {
            moves.add(option)
        }
    }

    return moves
}

fun validKills(options: MutableList<Spot>, spot: Spot, board: Board, currentTurn: Boolean): MutableList<Spot>{
    val moves = mutableListOf<Spot>()
    for (option in options){
        if(!checkIfMovePutsPlayerInCheck(spot, option, board, currentTurn)){
            moves.add(option)
        }
    }

    return moves
}

// checks if a move will benefit the king (king will no longer be in check)
fun checkIfMovePutsPlayerInCheck(start: Spot, end: Spot, board: Board, currentTurn: Boolean): Boolean {
    val startPiece = start.getPiece()               // save the piece that wants to move
    val endPiece = end.getPiece()                   // save the piece (if any) on the targeted spot
    board.movePiece(start, end)                     // moves the piece
    val kingSpot = board.getKingSpot(currentTurn)  // get the king
    val king = kingSpot.getPiece() as King
    // check if the player is in check
    if(king.checkIfKingInCheck(board, kingSpot)){
        // if not, reset the move and declare that the move is a valid move
        start.setPiece(startPiece)
        end.setPiece(endPiece)
        end.getPiece()?.setKilled(false)
        return true
    }
    else {
        start.setPiece(startPiece)
        end.setPiece(endPiece)
        end.getPiece()?.setKilled(false)
        return false
    }
}