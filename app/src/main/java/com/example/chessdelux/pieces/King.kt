package com.example.chessdelux.pieces

import android.util.Log
import com.example.chessdelux.R
import com.example.chessdelux.board.*
import kotlin.math.abs

class King(white: Boolean) : Piece(white, PieceType.KING) {
    // the king image
    override var imageResource: Int? = if (white) R.drawable.king_white else R.drawable.king_black

    // king in check
    private var isInCheck = false

    // king moved at least once
    private var kingMoved = false

    // set if king moved
    fun setKingMoved() {
        kingMoved = true
    }

    // return the king possible moves
    override fun moveOptions(board: Board, start: Spot): MutableList<Spot> {
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
                // check arrival spot to not be a white piece
                if (board.getBox(x + dx, y + dy).getPiece()?.isWhite() != isWhite())
                    // check if the king will be in check after the move
                    if(!checkIfKingMoveInCheck(board, board.getBox(x + dx, y + dy)))
                        options.add(board.getBox(x + dx, y + dy))
            }
        }

        return options
    }

    // check if the king will be in check after it moves
    private fun checkIfKingMoveInCheck(board: Board, end: Spot): Boolean {
        val kingSpot = board.getKingSpot(isWhite())
        val startPiece = kingSpot.getPiece() as King
        val endPiece = end.getPiece()
        board.movePiece(kingSpot, end)
        if(startPiece.checkIfKingInCheck(board, end)){ // here end is the king spot because we move the king on the end spot
            kingSpot.setPiece(startPiece)
            end.setPiece(endPiece)
            end.getPiece()?.setKilled(false)
            return true
        }
        else {
            kingSpot.setPiece(startPiece)
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
                            if (options.getX() == kingSpot.getX() && options.getY() == kingSpot.getY())
                                return true

                    } else if (piece is King && piece.isWhite() != isWhite()) {
                        if (abs(kingSpot.getX() - pieceSpot.getX()) <= 1 && abs(kingSpot.getY() - pieceSpot.getY()) <= 1)
                            return true
                    }
                }catch (e: Exception){
                    Log.e("ObscureMove", "checkIfKingInCheck problem ${i},${j} e -> ${e.message}")
                }
            }
        }
        return false
    }
}
