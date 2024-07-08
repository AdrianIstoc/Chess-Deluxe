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

    fun getCurrentPlayer(): Player {
        return currentTurn
    }

    fun getpossibleMoves(): List<Spot> {                // returns the list of possible spots
        return this.possibleMoves
    }

    fun setCurrentPieceSpot(spot: Spot) {               // sets the current piece spot
        currentPieceSpot = spot
    }

    fun getCurrentPieceSpot(): Spot? {                  // returns the current piece spot
        return currentPieceSpot
    }

    fun isEnd(): Boolean {                              // check if game ended
        return getStatus() != GameStatus.ACTIVE
    }

    private fun getStatus(): GameStatus {               // returns the game status
        return status
    }

    fun setStatus(status: GameStatus) {                 // sets the game status
        this.status = status
    }

    fun getBoard(): Board {                             // returns the board
        return board
    }

    private fun setSelectedSpot(i: Int, j: Int) {       // sets the selected spot
        selectedSpot = board.getBox(i, j)
    }

    private fun resetSelectedSpot() {                   // resets the selected spot to null
        selectedSpot = null
    }

    // update the current piece spot
    private fun updateCurrentPieceSpot() {
        currentPieceSpot?.getPiece()?.setSelected(false)    // deselect the piece
        currentPieceSpot = null                             // reset the current piece spot


        // check if selected spot has a piece of the current player
        if (selectedSpot != null && selectedSpot?.getPiece()?.isWhite() == currentTurn.isWhiteSide()) {
            currentPieceSpot = selectedSpot                 // set current piece spot as the selected spot
            currentPieceSpot?.getPiece()?.setSelected(true) // set the piece as selected

            // nu cred ca mai trebuie
//            try {
//                // set the possible moves of the current piece
//                possibleMoves = currentPieceSpot?.getPiece()?.moveOptions(board, currentPieceSpot!!) as MutableList<Spot>
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Log.e("MoveOptionsError", "Error getting move options: ${e.message}")
//            }
        }
        else {
            currentPieceSpot = null
        }
    }

    // render the pieces on the board
    private fun renderPieces(chessboard: GridLayout, cellSize: Int, white: Int, black: Int) {
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


                // next the game proceeds

                // check for user input
//                cell.setOnClickListener {
//                    // set the selected spot
//                    this.setSelectedSpot(i, j)
//                    // check if the selected spot indicated a player moving a piece
//                    this.checkIfPlayerMoves()
//                    // update the game
//                    this.updateCurrentPieceSpot()
//                    // selects the current piece spot as the selected spot
//                    this.setPossibleMoves()
//                    // check if the player is in check and if the current piece is not a king
//                    // if not, enter the code block
//                    if(!(checkIfInCheck() && currentPieceSpot?.getPiece() !is King)){
//                        // updates the colors of the chessboard to indicate the possible moves of the selected piece
//                        this.changeBoardOnSelection(chessboard, context)
//                        // renders the pieces
//                        this.renderPieces(chessboard, cellSize, white, black)
//                    }
//                }
            }
        }
        // renders the pieces again for some reason
        this.renderPieces(chessboard, cellSize, white, black)
    }

    // listens for user activity and updates the game based on it
    fun proceedWithTheGame(chessboard: GridLayout, cellSize: Int, context: MainActivity){
        val white = ContextCompat.getColor(context, R.color.white)
        val black = ContextCompat.getColor(context, R.color.cheese)

        for(i in 0 until 8){
            for(j in 0 until 8){
                val cell = chessboard.getChildAt(i*8+j) as ImageView

                cell.setOnClickListener {
                    // set the selected spot
                    this.setSelectedSpot(i, j)
                    // check if the selected spot indicated a player moving a piece
                    this.checkIfPlayerMoves()
                    // update the game
                    this.updateCurrentPieceSpot()
                    // selects the current piece spot as the selected spot
                    this.setPossibleMoves()
                    // updates the colors of the chessboard to indicate the possible moves of the selected piece
                    this.changeBoardOnSelection(chessboard, context)
                    // renders the pieces
                    this.renderPieces(chessboard, cellSize, white, black)
                }
            }
        }
    }

    // check if the selected spot is one of the possible moves of the selected piece
    private fun checkValidMove(): Boolean {
        for(spot in possibleMoves)
            if(selectedSpot == spot)
                return true
        return false
    }

    // check if the player is in check
    private fun checkIfInCheck(): Boolean {
        for (i in 0 until 8){
            for (j in 0 until 8) {
                val piece = board.getBox(i, j).getPiece()
                if(piece is King)                                               // check for every piece that is a king
                    if (piece.isWhite() == currentTurn.isWhiteSide()) {         // check for every piece that is the same color as the user
                        if (piece.checkIfKingInCheck(this))// check if the player will be in check on the spot of the piece (king)
                            return true
                    }
            }
        }
        return false
    }

    // check if player move and make the move
    private fun checkIfPlayerMoves() {
        if(currentPieceSpot != null){           // if there is a piece selected
            if(selectedSpot != null){           // and if there is a spot selected
                if(checkValidMove()){           // check if the selected spot is one of the move options of the current piece
                    // check if the pawn moved two spaces
                    checkPawnSkipped(currentPieceSpot!!, selectedSpot!!)
                    try {
                        // check if any "important" piece moved
                        checkImportantPieceMove()
                    }
                    catch (e: Exception){
                        Log.e("ObscureMove", "checkImportantPieceMove problem e -> ${e.message}")
                    }

                    // makes a move object to store the historic of the game
                    val move = Move(currentTurn, currentPieceSpot!!, selectedSpot!!)
                    try{
                        // check if the pawn is doing an en passant
                        checkIfEnPassant(currentPieceSpot!!, selectedSpot!!)
                    }
                    catch (e: Exception){
                        Log.e("ObscureMove", "checkIfEnPassant problem e -> ${e.message}")
                    }

                    // check if the king is castling
                    // if so, move the king and the rook properly
                    if(checkIfCastling(currentPieceSpot!!, selectedSpot!!)){
                        val rookSpot = if(currentPieceSpot?.getY()!! < selectedSpot?.getY()!!) board.getBox(selectedSpot?.getX()!!,selectedSpot?.getY()!!+1) else board.getBox(selectedSpot?.getX()!!, selectedSpot?.getY()!!-2)
                        val direction = if(currentPieceSpot?.getY()!! < selectedSpot?.getY()!!) 1 else -1
                        val kingNextSpot = board.getBox(currentPieceSpot?.getX()!!, currentPieceSpot?.getY()!!+2*direction)
                        val rookNextSpot = board.getBox(currentPieceSpot?.getX()!!, currentPieceSpot?.getY()!!+direction)
                        board.movePiece(currentPieceSpot!!, kingNextSpot)
                        board.movePiece(rookSpot, rookNextSpot)
                    }
                    // else just move the piece
                    else {
                        board.movePiece(currentPieceSpot!!, selectedSpot!!)
                    }
                    // adds the move to the historic of the game
                    movesPlayed.add(move)
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
            if(board.getBox(start.getX(), start.getY()-1).getPiece() is Pawn)
                if((board.getBox(start.getX(), start.getY()-1).getPiece() as Pawn).pawnSkipped) {
                    board.getBox(start.getX(), start.getY()-1).getPiece()?.setKilled(true)
                    return
                }
        }
        // check if a black pawn is going to the left
        else if(start.getPiece()?.isWhite() == false && start.getX() == 4)
            if(board.getBox(start.getX(), start.getY()-1).getPiece() is Pawn)
                if((board.getBox(start.getX(), start.getY()-1).getPiece() as Pawn).pawnSkipped){
                    board.getBox(start.getX(), start.getY()-1).getPiece()?.setKilled(true)
                    return
                }
        // check if a white pawn is going to the right
        if(start.getPiece()?.isWhite() == true && start.getX() == 3){
            if(board.getBox(start.getX(), start.getY()+1).getPiece() is Pawn)
                if((board.getBox(start.getX(), start.getY()+1).getPiece() as Pawn).pawnSkipped){
                    board.getBox(start.getX(), start.getY()+1).getPiece()?.setKilled(true)
                    return
                }
        }
        // check if a black pawn is going to the right
        else if(start.getPiece()?.isWhite() == false && start.getX() == 4)
            if(board.getBox(start.getX(), start.getY()+1).getPiece() is Pawn)
                if((board.getBox(start.getX(), start.getY()+1).getPiece() as Pawn).pawnSkipped){
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
    private fun checkIfCastling(start: Spot, end: Spot): Boolean {
        try{
            // check if the selected spot is valid for a castling
            if ((end.getY() == 6 || end.getY() == 2) && (end.getX() == 0 || end.getX() == 7)) {
                val king = start.getPiece()?.getType()                                      // get the piece type of the "king"
                val rookSpot = if (start.getY() < end.getY()) board.getBox(                 // get the spot of the rook based of the selected spot
                    selectedSpot?.getX()!!,
                    selectedSpot?.getY()!! + 1
                ) else board.getBox(selectedSpot?.getX()!!, selectedSpot?.getY()!! - 2)
                val rook = rookSpot.getPiece()?.getType()                                   // get the piece type of the "rook"
                return king == PieceType.KING && rook == PieceType.ROOK                     // return true if king is king and rook is rook
            }
        }
        catch (e: Exception)
        {
            Log.e("ObscureMove", "CheckIfCastling problem e -> ${e.message}")
        }

        return false
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
        var moves = mutableListOf<Spot>()
        if(currentPieceSpot?.getPiece() != null) {
            // get the possible moves
            possibleMoves = currentPieceSpot?.getPiece()?.moveOptions(board, currentPieceSpot!!) as MutableList<Spot>
            // filter the possible moves
            moves = validMoves(currentPieceSpot!!)
        }
        possibleMoves = moves

        // deselect all spots
        for (i in 0 until 8)
            for (j in 0 until 8)
                board.getBox(i, j).setSelectableSpot(false)

        // select the spots that represent the moves of the current piece
        for (spot in possibleMoves)
            board.getBox(spot.getX(), spot.getY()).setSelectableSpot(true)

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
                    if(possibleMoves.size == 0)
                        chessboard.getChildAt(i*8+j).setBackgroundColor(white)
                    else
                        // else it changes into green
                        for(pair in possibleMoves)
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
                    if(possibleMoves.size == 0)
                        chessboard.getChildAt(i*8+j).setBackgroundColor(black)
                    else
                        // else it changes into a greenish black
                        for(pair in possibleMoves)
                            if(pair.getX() == i && pair.getY() == j) {
                                chessboard.getChildAt(i * 8 + j).setBackgroundColor(blackGreen)
                                break
                            }
                            else
                                chessboard.getChildAt(i*8+j).setBackgroundColor(black)
                }
            }
    }
    
    // checks if the player moves a piece will result in his king being in check
    private fun checkIfKingInCheckAfterPieceMove(start: Spot, end: Spot): Boolean {
        val startPiece = start.getPiece()   // save the piece that wants to move
        val endPiece = end.getPiece()       // save the piece (if any) on the spot that is targeted
        board.movePiece(start, end)         // make the move
        val kingSpot = board.getKingSpot(currentTurn.isWhiteSide())  // get the king
        // check if the player is in check
        if((kingSpot.getPiece() as King).checkIfKingInCheck(this)) {
            // if so, reset the move and declare that the move is not a valid move
            start.setPiece(startPiece)
            end.setPiece(endPiece)
            end.getPiece()?.setKilled(false)
            return true
        }


        start.setPiece(startPiece)
        end.setPiece(endPiece)
        end.getPiece()?.setKilled(false)
        return false
    }
    
    // checks if a move will benefit the king (king will no longer be in check)
    private fun checkIfMoveHelpsKing(start: Spot, end: Spot): Boolean {
        val startPiece = start.getPiece()               // save the piece that wants to move
        val endPiece = end.getPiece()                   // save the piece (if any) on the targeted spot
        board.movePiece(start, end)                     // moves the piece
        val kingSpot = board.getKingSpot(currentTurn.isWhiteSide())  // get the king
        // check if the player is in check
        if(!(kingSpot.getPiece() as King).checkIfKingInCheck(this)){
            // if not, reset the move and declare that the move is a valid move
            start.setPiece(startPiece)
            end.setPiece(endPiece)
            end.getPiece()?.setKilled(false)
            return true
        }
        start.setPiece(startPiece)
        end.setPiece(endPiece)
        end.getPiece()?.setKilled(false)
        return false
    }
    
    // filters the move options of a piece
    private fun validMoves(start: Spot): MutableList<Spot> {
        val moves = mutableListOf<Spot>()
        // if the piece is not a king
        if(start.getPiece() !is King){
            val kingSpot = board.getKingSpot(currentTurn.isWhiteSide())
            // check if the player is in check
            if((kingSpot.getPiece() as King).checkIfKingInCheck(this)){
                for (option in possibleMoves)
                // checks every move to find out if it helps the king
                    if(checkIfMoveHelpsKing(start, option))
                        moves.add(option)
            }
            else{
                for (options in possibleMoves)
                // checks every move to find out if the king will not be in check
                    if (!checkIfKingInCheckAfterPieceMove(start, options))
                        moves.add(options)

            }
        }
        else {
            val king = start.getPiece() as King
            for (option in possibleMoves)
                // check if the king moves into check
                if(!checkIfKingInCheckAfterPieceMove(start, option))
                    moves.add(option)
        }
        
        return moves        // return the valid moves
    }
}