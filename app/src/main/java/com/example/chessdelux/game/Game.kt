package com.example.chessdelux.game

import android.util.Log
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.chessdelux.MainActivity
import com.example.chessdelux.R
import com.example.chessdelux.board.*
import com.example.chessdelux.pieces.*
import kotlin.math.abs

class Game {
    private val players = arrayOfNulls<Player>(2)  // array of players
    private var board: Board = Board()                  // board; is filled with cells
    private lateinit var currentTurn: Player            // current player
    private lateinit var status: GameStatus             // game status
    private var selectedSpot: Spot? = null              // the spot that has been currently selected
    private var currentPieceSpot: Spot? = null          // the spot of the piece that is currently selected
    private var possibleMoves = mutableListOf<Spot>()   // list of possible moves that the current piece can make
    private var possibleKills = mutableListOf<Spot>()   // list of possible kills that the current piece can make
    private val movesPlayed = mutableListOf<Move>()     // list of moves that the players have done

    fun initialize(p1: Player, p2: Player) {            // initialize the game
        players[0] = p1
        players[1] = p2

        board.resetBoard()                              // reset the board for a fresh start

        currentTurn = if (p1.isWhiteSide()) {           // set the current player
            p1
        } else {
            p2
        }

        status = GameStatus.ACTIVE                      // set the game status ACTIVE

        movesPlayed.clear()                             // clear the list of moves that the players have done
    }

    private fun setSelectedSpot(i: Int, j: Int) {       // sets the selected spot
        selectedSpot = board.getBox(i, j)
    }

    // update the current piece spot
    private fun updateCurrentPieceSpot() {
        currentPieceSpot?.getPiece()?.setSelected(false)    // deselect the piece
        currentPieceSpot = null                             // reset the current piece spot


        // check if selected spot has a piece of the current player
        if (selectedSpot != null && selectedSpot?.getPiece()?.isWhite() == currentTurn.isWhiteSide()) {
            currentPieceSpot = selectedSpot                 // set current piece spot as the selected spot
            currentPieceSpot?.getPiece()?.setSelected(true) // set the piece as selected
        }
    }

    // render the pieces on the board
    private fun renderPieces(chessboard: GridLayout) {
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

    // renders the chessboard and updates it
    fun renderGameBoard(chessboard: GridLayout, cellSize: Int, context: MainActivity) {
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
        this.renderPieces(chessboard)
    }

    // listens for user activity and updates the game based on it
    fun proceedWithTheGame(chessboard: GridLayout, context: MainActivity){

        for(i in 0 until 8){
            for(j in 0 until 8){
                val cell = chessboard.getChildAt(i*8+j) as ImageView

                cell.setOnClickListener {
                    // set the selected spot
                    try{
                        this.setSelectedSpot(i, j)
                    }catch (e: Exception)
                    {
                        Log.e("ObscureMove", "setSelectedSpot problem e -> ${e.message}")
                    }
                    // check if the selected spot indicated a player moving a piece
                    try {
                        this.checkIfPlayerMoves()
                    }catch (e: Exception)
                    {
                        Log.e("ObscureMove", "checkIfPlayerMoves problem e -> ${e.message}")
                    }
                    // update the game
                    try{
                        this.updateCurrentPieceSpot()
                    }catch (e: Exception)
                    {
                        Log.e("ObscureMove", "updateCurrentPieceSpot problem e -> ${e.message}")
                    }
                    // selects the current piece spot as the selected spot
                    try{
                        this.setPossibleMoves()
                    }catch (e: Exception)
                    {
                        Log.e("ObscureMove", "setPossibleMoves problem e -> ${e.message}")
                    }
                    // updates the colors of the chessboard to indicate the possible moves of the selected piece
                    this.changeBoardOnSelection(chessboard, context)
                    // renders the pieces
                    this.renderPieces(chessboard)
                }
            }
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
    private fun checkIfPlayerMoves() {
        if(currentPieceSpot != null){           // if there is a piece selected
            if(selectedSpot != null){           // and if there is a spot selected
                if(checkValidMove()){           // check if the selected spot is one of the move options of the current piece
                    val piece = currentPieceSpot?.getPiece()

                    if(piece is Pawn)
                        // check if the pawn moved two spaces
                        checkPawnSkipped(currentPieceSpot!!, selectedSpot!!)

                    // check for important moves
                    checkImportantPieceMove()

                    if(piece is Pawn)
                        // check if the pawn can make an en passant and sets the opponent pawn as killed
                        checkIfEnPassant(currentPieceSpot!!, selectedSpot!!)


                    // check if the king is castling
                    // if so, move the king and the rook properly
                    if(piece is King)
                        checkIfCastling(currentPieceSpot!!, selectedSpot!!)


                    // move piece
                    board.movePiece(currentPieceSpot!!, selectedSpot!!)

                    // change the turn of the players
                    currentTurn = if (currentTurn.isWhiteSide()) {
                        players[1]!!
                    } else {
                        players[0]!!
                    }
                }
            }
        }
    }

    // check if the pawn is doing an en passant
    private fun checkIfEnPassant(start: Spot, end: Spot){
        // check if a white pawn is going to the left
        if(start.getPiece()?.isWhite() == true && start.getX() == 3 ){
            if(start.getY()-1 in 0 .. 7 && board.getBox(start.getX(), start.getY()-1).getPiece() is Pawn)
                if((board.getBox(start.getX(), start.getY()-1).getPiece() as Pawn).pawnSkipped)
                    if(end.getX()==start.getX()-1 && end.getY() == start.getY()-1) {
                        board.getBox(start.getX(), start.getY() - 1).getPiece()?.setKilled(true)
                        return
                    }
        }
        // check if a black pawn is going to the left
        else if(start.getPiece()?.isWhite() == false && start.getX() == 4)
            if(start.getY()-1 in 0 .. 7 && board.getBox(start.getX(), start.getY()-1).getPiece() is Pawn)
                if((board.getBox(start.getX(), start.getY()-1).getPiece() as Pawn).pawnSkipped)
                    if(end.getX() == start.getX()+1 && end.getY() == start.getY()-1){
                        board.getBox(start.getX(), start.getY()-1).getPiece()?.setKilled(true)
                        return
                    }
        // check if a white pawn is going to the right
        if(start.getPiece()?.isWhite() == true && start.getX() == 3){
            if(start.getY()+1 in 0 .. 7 && board.getBox(start.getX(), start.getY()+1).getPiece() is Pawn)
                if((board.getBox(start.getX(), start.getY()+1).getPiece() as Pawn).pawnSkipped)
                    if(end.getX() == start.getX()-1 && end.getY() == start.getY()+1){
                        board.getBox(start.getX(), start.getY()+1).getPiece()?.setKilled(true)
                        return
                    }
        }
        // check if a black pawn is going to the right
        else if(start.getPiece()?.isWhite() == false && start.getX() == 4)
            if(start.getY()+1 in 0 .. 7 && board.getBox(start.getX(), start.getY()+1).getPiece() is Pawn)
                if((board.getBox(start.getX(), start.getY()+1).getPiece() as Pawn).pawnSkipped)
                    if(end.getX() == start.getX()+1 && end.getY() == start.getY()+1){
                        board.getBox(start.getX(), start.getY()+1).getPiece()?.setKilled(true)
                        return
                    }

    }

    // check if the piece is a pawn that moved two spaces
    private fun checkPawnSkipped(start: Spot, end: Spot){
        // set all pawns as they didn't move two spots
        for (i in 0 until 8)
            for (j in 0 until 8){
                val piece = board.getBox(i, j).getPiece()
                if(piece is Pawn && piece.isWhite() == currentTurn.isWhiteSide()){
                    piece.setPawnSkipped(false)
                }
            }

        //check if the pawn moved two spaces and stets the pawn as "skipped"
        if (abs(start.getX() - end.getX()) == 2 && start.getPiece() is Pawn)
            (start.getPiece() as Pawn).setPawnSkipped(true)
    }

    // check if the king is castling
    private fun checkIfCastling(start: Spot, end: Spot) {
        try{
            // check if the selected spot is valid for a castling
            if ((end.getY() == 6 || end.getY() == 2) && (end.getX() == 0 || end.getX() == 7)) {
                val king = start.getPiece()?.getType()                                      // get the piece type of the "king"
                val rookSpot = if (start.getY() < end.getY()) board.getBox(                 // get the spot of the rook based of the selected spot
                    selectedSpot?.getX()!!,
                    selectedSpot?.getY()!! + 1
                ) else board.getBox(selectedSpot?.getX()!!, selectedSpot?.getY()!! - 2)
                val rook = rookSpot.getPiece()?.getType()                                   // get the piece type of the "rook"
                if(king == PieceType.KING && rook == PieceType.ROOK) {                      // return true if king is king and rook is rook
                    val direction = if(currentPieceSpot?.getY()!! < selectedSpot?.getY()!!) 1 else -1
                    val rookNextSpot = board.getBox(currentPieceSpot?.getX()!!, currentPieceSpot?.getY()!!+direction)
                    board.movePiece(rookSpot, rookNextSpot)
                }
            }
        }
        catch (e: Exception)
        {
            Log.e("ObscureMove", "CheckIfCastling problem e -> ${e.message}")
        }
    }

    // check if any piece, that has a ruling
    // based on the fact that it didn't moved,
    // did indeed move and set that piece as moved
    private fun checkImportantPieceMove() {
        when (currentPieceSpot?.getPiece()?.getType()) {
            PieceType.KING -> {
                val king = currentPieceSpot?.getPiece() as King
                king.setKingMoved()
            }
            PieceType.PAWN -> {
                val pawn = currentPieceSpot?.getPiece() as Pawn
                pawn.setPawnMoved()
            }
            PieceType.ROOK -> {
                val rook = currentPieceSpot?.getPiece() as Rook
                rook.setRookMoved()
            }
            else -> {}
        }
    }

    // set the spots that show to possible moves of a selected piece
    private fun setPossibleMoves(){
        // set the list of possible moves of the current piece
        possibleMoves = mutableListOf()
        possibleKills = mutableListOf()
        if(currentPieceSpot?.getPiece() != null) {
            // get the possible moves
            possibleMoves = currentPieceSpot?.getPiece()?.moveOptions(board, currentPieceSpot!!) as MutableList<Spot>
            possibleMoves = validMoves()
            // get the possible kills
            possibleKills = currentPieceSpot?.getPiece()?.killOptions(board, currentPieceSpot!!) as MutableList<Spot>
            possibleKills = validKills()
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

    private fun validMoves(): MutableList<Spot> {
        val moves = mutableListOf<Spot>()
        val currentPiece = currentPieceSpot?.getPiece()
        //if (currentPiece !is King) {
            for (option in possibleMoves) {
                if (!checkIfMovePutsPlayerInCheck(currentPieceSpot!!, option)) {
                    moves.add(option)
                }
            }
        //}

        return moves
    }

    private fun validKills(): MutableList<Spot>{
        val moves = mutableListOf<Spot>()
        val currentPiece = currentPieceSpot?.getPiece()
        //if(currentPiece !is King){
            for (option in possibleKills){
                if(!checkIfMovePutsPlayerInCheck(currentPieceSpot!!, option)){
                    moves.add(option)
                }
            }
        //}

        return moves
    }

    // change the colors of the chessboard to show the current piece movement
    private fun changeBoardOnSelection(chessboard: GridLayout, context: MainActivity) {
        val white = ContextCompat.getColor(context, R.color.white)
        val black = ContextCompat.getColor(context, R.color.cheese)
        val whiteGreen = ContextCompat.getColor(context, R.color.green)
        val blackGreen = ContextCompat.getColor(context, R.color.mold_cheese)


        for (i in 0 until 8)
            for (j in 0 until 8) {
                if((i+j) % 2 == 0) {
                    // white
                    // if the spot is not on the list it stays white
                    if(possibleMoves.size == 0 && possibleKills.size == 0)
                        chessboard.getChildAt(i*8+j).setBackgroundColor(white)
                    else
                        // else it changes into green
                        for(pair in possibleMoves + possibleKills)
                            if(pair.getX() == i && pair.getY() == j) {
                                chessboard.getChildAt(i * 8 + j).setBackgroundColor(whiteGreen)
                                break
                            }
                            else
                                chessboard.getChildAt(i*8+j).setBackgroundColor(white)
                }
                else {
                    // black
                    //if the spot is not on the list it stays black
                    if(possibleMoves.size == 0 && possibleKills.size == 0)
                        chessboard.getChildAt(i*8+j).setBackgroundColor(black)
                    else
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



    
    // checks if a move will benefit the king (king will no longer be in check)
    private fun checkIfMovePutsPlayerInCheck(start: Spot, end: Spot): Boolean {
        val startPiece = start.getPiece()               // save the piece that wants to move
        val endPiece = end.getPiece()                   // save the piece (if any) on the targeted spot
        board.movePiece(start, end)                     // moves the piece
        val kingSpot = board.getKingSpot(currentTurn.isWhiteSide())  // get the king
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
}