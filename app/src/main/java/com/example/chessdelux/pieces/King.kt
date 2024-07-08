package com.example.chessdelux.pieces

import com.example.chessdelux.R
import com.example.chessdelux.board.*
import com.example.chessdelux.game.Game

class King(white: Boolean) : Piece(white, PieceType.KING) {
    // the king image
    override var imageResource: Int? = if (white) R.drawable.king_white else R.drawable.king_black

    private val moveDirections = listOf(
        Pair(1,1), Pair(1,0), Pair(1,-1), Pair(0,-1),
        Pair(-1,-1), Pair(-1,0), Pair(-1,1), Pair(0,1)
    )

    // king in check
    private var isInCheck = false

    // king moved at least once
    private var kingMoved = false

    // set if king moved
    fun setKingMoved() {
        kingMoved = true
    }

    // return if king moved
    fun isKingMoved(): Boolean {
        return kingMoved
    }

    // check if the king is in check
    fun isInCheck(): Boolean {
        return isInCheck
    }

    // set if the king is in check
    fun setInCheck(isInCheck: Boolean) {
        this.isInCheck = isInCheck
    }

    // check if the king will be in check after it moves
    fun checkIfKingToCheck(game: Game, end: Spot): Boolean {
        val board = game.getBoard()                             // get the board
        for (i in 0 until 8){
            for (j in 0 until 8) {
                val pieceSpot = board.getBox(i, j)              // get the piece spot
                val piece = pieceSpot.getPiece()                // get the piece
                // check if the piece belongs to the opponent
                if (piece != null && piece.isWhite() != game.getCurrentPlayer().isWhiteSide()) {
                    // get the list of possible moves of the piece
                    val possibleMove = piece.moveOptions(board, pieceSpot)
                    if (possibleMove != null)
                        for (option in possibleMove)
                            // check if the option is the selected spot (end)
                            if (option.getX() == end.getX() && option.getY() == end.getY())
                                return true                     // king will be in check
                }
            }
        }
        return false
    }

    // check if the current player is in check
    fun checkIfKingInCheck(game: Game): Boolean {
        val board = game.getBoard()                 // get the board
        val kingSpot = board.getKingSpot(game.getCurrentPlayer().isWhiteSide()) // get player king
        for (i in 0 until 8){
            for (j in 0 until 8){
                val pieceSpot = board.getBox(i, j)          // get the piece spot
                val piece = pieceSpot.getPiece()            // get the piece
                if(piece != null){        // check if the piece is anything but a king
                    // check if the piece is on opponent control
                    if(piece.isWhite() != game.getCurrentPlayer().isWhiteSide()){
                        val possibleMove = piece.moveOptions(board, pieceSpot)
                        for(move in possibleMove){          // test the possible moves of the piece
                            if(move.getX() == kingSpot.getX() && move.getY() == kingSpot.getY()){
                                isInCheck = true            // set king as in check
                                return true
                            }
                        }
                    }
                }
            }
        }
        return false
    }

    // check if the king will be in check after a move
    // used for king movement
    fun checkIfKingMoveInCheck(board: Board, end: Spot): Boolean{
        for (i in 0 until 8){
            for (j in 0 until 8){
                // get every piece
                val piece = board.getBox(i, j).getPiece()
                // check it so it is not a king  and the piece is different in color from the king
                if (piece !is King && piece?.isWhite() != this.isWhite()) {
                    // get the list of possible moves of the piece
                    val positions = piece?.moveOptions(board, board.getBox(i, j))
                    // check so that there is at least one move
                    if (positions != null)
                        for (option in positions)
                            // check every position to correspond with the selected spot (end)
                            // if so, the king will be in check
                            if (option.getX() == end.getX() && option.getY() == end.getY())
                                return true
                }
            }
        }
        return false
    }





    // return the king possible moves
    override fun moveOptions(board: Board, start: Spot): List<Spot> {
        val options = mutableListOf<Spot>()
        val x = start.getX()
        val y = start.getY()
        val directions = listOf(
            Pair(-1, 0), Pair(-1,-1), Pair(0, -1), Pair(1, -1),
            Pair(1, 0), Pair(1, 1), Pair(0, 1), Pair(-1, 1))

        // normal moves
        for ((dx, dy) in directions) {
            if (x+dx in 0 .. 7 && y+dy in 0 .. 7){
                // check arrival spot to not be a white piece
                if (board.getBox(x + dx, y + dy).getPiece()?.isWhite() != isWhite())
                    // check if the king will be in check after the move
                    if(!checkIfKingMoveInCheck(board, board.getBox(x + dx, y + dy)))
                        options.add(board.getBox(x + dx, y + dy))
            }
        }

        // castling
        if (!kingMoved && !isInCheck) {
            var rookSpot = board.getBox(start.getX(), start.getY()-4)   // get the spot of the left rook
            var piece = rookSpot.getPiece()                                // get the piece

            // check if piece is rook and if it hasn't moved yet
            if (piece is Rook && !piece.isRookMoved()) {
                // check if the space between the king and the rook is empty
                if(board.getBox(x, y-3).getPiece() == null && board.getBox(x, y-2).getPiece() == null && board.getBox(x , y-1).getPiece() == null)
                    // check if the king will be in check after the move or during the move
                    if(!checkIfKingMoveInCheck(board, board.getBox(x, y - 2)) && !checkIfKingMoveInCheck(board, board.getBox(x, y-1)) && !checkIfKingMoveInCheck(board, board.getBox(x, y)))
                        options.add(board.getBox(x, y-2))
            }

            rookSpot = board.getBox(start.getX(), start.getY()+3)       // get the spot of the right rook
            piece = rookSpot.getPiece()                                    // get the piece

            // check if piece is rook and if it hasn't moved yet
            if(piece is Rook && !piece.isRookMoved()){
                // check if the space between the king and the rook is empty
                if(board.getBox(x , y+2).getPiece() == null && board.getBox(x , y+1).getPiece() == null)
                    // check if the king will be in check after the move or during the move
                    if(!checkIfKingMoveInCheck(board, board.getBox(x, y + 2)) && !checkIfKingMoveInCheck(board, board.getBox(x, y+1)) && !checkIfKingMoveInCheck(board, board.getBox(x, y-1)))
                        options.add(board.getBox(x, y+2))
            }
        }

        return options
    }
}
