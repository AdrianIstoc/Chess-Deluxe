package com.example.chessdelux.pieces

import com.example.chessdelux.R
import com.example.chessdelux.board.*

class Knight(white: Boolean) : Piece(white, PieceType.KNIGHT) {
    // the knight image
    override var imageResource: Int? = if (white) R.drawable.knight_white else R.drawable.knight_black

    // return the knight possible moves
        override fun moveOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        val x = start.getX()
        val y = start.getY()
        val directions = listOf(
            Pair(-2, 1), Pair(-2, -1), Pair(2, 1), Pair(2, -1),
            Pair(-1, 2), Pair(-1, -2), Pair(1, 2), Pair(1, -2))

        // add the possible moves
        for ((dx, dy) in directions) {
            if (x + dx in 0 .. 7 && y + dy in 0 .. 7){
                if (board.getBox(x + dx, y + dy).getPiece() == null || board.getBox(x + dx, y + dy)
                        .getPiece()?.isWhite() != isWhite()
                )
                    options.add(board.getBox(x + dx, y + dy))
            }
        }

        return options
    }

    // return the knight kill options
    override fun killOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        val x = start.getX()
        val y = start.getY()
        val directions = listOf(
            Pair(-2, 1), Pair(-2, -1), Pair(2, 1), Pair(2, -1),
            Pair(-1, 2), Pair(-1, -2), Pair(1, 2), Pair(1, -2))

        // add the possible moves
        for ((dx, dy) in directions) {
            if (x + dx in 0 .. 7 && y + dy in 0 .. 7){
                if (board.getBox(x + dx, y + dy).getPiece() == null || board.getBox(x + dx, y + dy)
                        .getPiece()?.isWhite() != isWhite()
                )
                    options.add(board.getBox(x + dx, y + dy))
            }
        }

        return options
    }
}