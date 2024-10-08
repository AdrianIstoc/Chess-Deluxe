package com.example.chessdelux.pieces

import android.util.Log
import com.example.chessdelux.R
import com.example.chessdelux.board.*
import kotlin.math.abs

class King(white: Boolean) : Piece(white, PieceType.KING, 90, Int.MAX_VALUE) {
    // the king image
    override var imageResource: Int? = if (white) R.drawable.king_white else R.drawable.king_black

    override val evolutionOptions: List<PieceType> = listOf()

    // king in check
    private var isInCheck = false

    // king moved at least once
    private var kingMoved = false

    // set if king moved
    fun setKingMoved(value: Boolean) {
        kingMoved = value
    }

    fun isKingMoved(): Boolean {
        return kingMoved
    }

    fun isInCheck(): Boolean{
        return isInCheck
    }
    fun setInCheck(value: Boolean){
        isInCheck = value
    }

    // return the king possible moves
    override fun moveOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        val x = start.getX()
        val y = start.getY()
        val directions = listOf(
            Pair(-1, 0), Pair(-1,-1), Pair(0, -1), Pair(1, -1),
            Pair(1, 0), Pair(1, 1), Pair(0, 1), Pair(-1, 1))

        // check if the king is in check
        // for fortress scheme
        if(checkIfKingInCheck(board, start)){
            val fortressSpots = board.getFortressSpots(this.isWhite())
            for (fortressSpot in fortressSpots){
                // check if the king will be in check after the move
                if(!checkIfKingMoveInCheck(board, fortressSpot))
                    options.add(fortressSpot)
            }
        }


        // normal moves
        for ((dx, dy) in directions) {
            if (x+dx in 0 .. 7 && y+dy in 0 .. 7){
                val spot = board.getBox(x+dx, y+dy)
                // check arrival spot to not be a fortress
                if(spot.getPiece() == null)
                    // check if the king will be in check after the move
                    if(!checkIfKingMoveInCheck(board, spot))
                        options.add(spot)
            }
        }

        if(x+1 in 0 .. 7)
            if(board.getBox(x+1, y).getPiece() is RiverBridge)
                if(x+2 in 0 .. 7){
                    val spot = board.getBox(x+2, y)
                    if(spot.getPiece() == null)
                        if(!checkIfKingMoveInCheck(board, spot))
                            options.add(spot)
                }

        if(x-1 in 0 .. 7)
            if(board.getBox(x-1, y).getPiece() is RiverBridge)
                if(x-2 in 0 .. 7) {
                    val spot = board.getBox(x - 2, y)
                    if (spot.getPiece() == null)
                        if (!checkIfKingMoveInCheck(board, spot))
                            options.add(spot)
                }

        // castling
        if (!kingMoved && !isInCheck) {
            var rookSpotY = start.getY()-4
            if(rookSpotY in 0 .. 7) {
                val rookSpot = board.getBox(start.getX(), start.getY() - 4)   // get the spot of the left rook
                val piece = rookSpot.getPiece()                                // get the piece

                // check if piece is rook and if it hasn't moved yet
                if (piece is Rook && !piece.isRookMoved()) {
                    // check if the space between the king and the rook is empty
                    if (board.getBox(x, y - 3).getPiece() == null && board.getBox(x, y - 2)
                            .getPiece() == null && board.getBox(x, y - 1).getPiece() == null
                    )
                    // check if the king will be in check after the move or during the move
                        if (!checkIfKingMoveInCheck(
                                board,
                                board.getBox(x, y - 2)
                            ) && !checkIfKingMoveInCheck(
                                board,
                                board.getBox(x, y - 1)
                            ) && !checkIfKingMoveInCheck(board, board.getBox(x, y))
                        )
                            options.add(board.getBox(x, y - 2))
                }
            }

            rookSpotY = start.getY()+3
            if(rookSpotY in 0 .. 7) {
                val rookSpot = board.getBox(
                    start.getX(),
                    start.getY() + 3
                )       // get the spot of the right rook
                val piece = rookSpot.getPiece()                                    // get the piece

                // check if piece is rook and if it hasn't moved yet
                if (piece is Rook && !piece.isRookMoved()) {
                    // check if the space between the king and the rook is empty
                    if (board.getBox(x, y + 2).getPiece() == null && board.getBox(x, y + 1)
                            .getPiece() == null
                    )
                    // check if the king will be in check after the move or during the move
                        if (!checkIfKingMoveInCheck(
                                board,
                                board.getBox(x, y + 2)
                            ) && !checkIfKingMoveInCheck(
                                board,
                                board.getBox(x, y + 1)
                            ) && !checkIfKingMoveInCheck(board, board.getBox(x, y - 1))
                        )
                            options.add(board.getBox(x, y + 2))
                }
            }
        }
        return options
    }


    // return the king kill options
    override fun killOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        val x = start.getX()
        val y = start.getY()
        val directions = listOf(
            Pair(-1, 0), Pair(-1,-1), Pair(0, -1), Pair(1, -1),
            Pair(1, 0), Pair(1, 1), Pair(0, 1), Pair(-1, 1))

        // normal moves
        for ((dx, dy) in directions) {
            if (x+dx in 0 .. 7 && y+dy in 0 .. 7){
                val spot = board.getBox(x+dx, y+dy)
                // check arrival spot to not be a fortress
                if(spot.getPiece() !is Fortress && spot.getPiece()?.isRiver() == false)
                    // check arrival spot to not be a white piece
                    if (spot.getPiece()?.isWhite() == !isWhite())
                        // check if the king will be in check after the move
                        if(!checkIfKingMoveInCheck(board, spot))
                            options.add(spot)
            }
        }

        if(x+1 in 0 .. 7)
            if(board.getBox(x+1, y).getPiece() is RiverBridge)
                if(x+2 in 0 .. 7){
                    val spot = board.getBox(x+2, y)
                    if(spot.getPiece() !is Fortress && spot.getPiece()?.isRiver() == false)
                        if(spot.getPiece()?.isWhite() == !isWhite())
                            if(!checkIfKingMoveInCheck(board, spot))
                                options.add(spot)
                }

        if(x-1 in 0 .. 7)
            if(board.getBox(x-1, y).getPiece() is RiverBridge)
                if(x-2 in 0 .. 7) {
                    val spot = board.getBox(x - 2, y)
                    if(spot.getPiece() !is Fortress && spot.getPiece()?.isRiver() == false)
                        if (spot.getPiece()?.isWhite() == !isWhite())
                            if (!checkIfKingMoveInCheck(board, spot))
                                options.add(spot)
                }

        return options
    }

    // check if the king will be in check after it moves
    private fun checkIfKingMoveInCheck(board: Board, end: Spot): Boolean {
        val kingSpot = board.getKingSpot(isWhite())
        val startPiece = kingSpot.getPiece() as King
        val check = startPiece.isInCheck()
        val endPiece = end.getPiece()
        board.movePiece(kingSpot, end)
        if(startPiece.checkIfKingInCheck(board, end)){ // here end is the king spot because we move the king on the end spot
            kingSpot.setPiece(startPiece)
            startPiece.isInCheck = check
            end.setPiece(endPiece)
            end.getPiece()?.setKilled(false)
            return true
        }
        else {
            kingSpot.setPiece(startPiece)
            startPiece.isInCheck = check
            end.setPiece(endPiece)
            end.getPiece()?.setKilled(false)
            return false
        }

    }

    // check if the king is in check
    fun checkIfKingInCheck(board: Board, kingSpot: Spot): Boolean {
        for (i in 0 until 8){
            for (j in 0 until 8){
                val pieceSpot = board.getBox(i, j)
                val piece = pieceSpot.getPiece()
                try{
                    if (piece != null && piece.isWhite() != isWhite() && piece !is King) {
                        for (options in piece.killOptions(board, pieceSpot))
                            if (options.getX() == kingSpot.getX() && options.getY() == kingSpot.getY()){
                                return true
                            }
                    } else if (piece is King && piece.isWhite() != isWhite()) {
                        if (abs(kingSpot.getX() - pieceSpot.getX()) <= 1 && abs(kingSpot.getY() - pieceSpot.getY()) <= 1){
                            return true
                        }
                    }
                }catch (e: Exception){
                    Log.e("ObscureMove", "checkIfKingInCheck problem ${i},${j} e -> ${e.message}")
                }
            }
        }
        return false
    }


    // check if the king is castling
    fun checkIfCastling(start: Spot, end: Spot, board: Board) {
        try{
            // check if the selected spot is valid for a castling
            if ((end.getY() == 6 || end.getY() == 2) && (end.getX() == 0 || end.getX() == 7)) {
                val king = start.getPiece()                                      // get the piece type of the "king"
                // get the spot of the rook based of the selected spot
                val rookSpot = if (start.getY() < end.getY()) board.getBox(end.getX(), end.getY() + 1)
                else board.getBox(end.getX(), end.getY() - 2)
                val rook = rookSpot.getPiece()                                // get the piece type of the "rook"
                if(king is King && rook is Rook && !king.kingMoved && !rook.isRookMoved()) {                      // return true if king is king and rook is rook
                    val direction = if(start.getY() < end.getY()) 1 else -1
                    val rookNextSpot = board.getBox(start.getX(), start.getY()+direction)
                    board.movePiece(rookSpot, rookNextSpot)
                }
            }
        }
        catch (e: Exception)
        {
            Log.e("ObscureMove", "CheckIfCastling problem e -> ${e.message}")
        }
    }

}
