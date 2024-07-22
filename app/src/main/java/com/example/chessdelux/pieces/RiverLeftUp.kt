package com.example.chessdelux.pieces

import com.example.chessdelux.R
import com.example.chessdelux.board.Board
import com.example.chessdelux.board.Spot

class RiverLeftUp(white: Boolean) : Piece(white, PieceType.RIVER_LEFT_UP, 1, Int.MAX_VALUE) {
    override var imageResource: Int? = if (white) R.drawable.river_left_up_white else R.drawable.river_left_up_black

    override val evolutionOptions: List<PieceType> = listOf()

    override fun moveOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        val x = start.getX()
        val y = start.getY()
        val direction = listOf(
            Pair(1, 0), Pair(0, 1))

        for ((dx,dy) in direction) {
            if(x+dx in 0..7 && y+dy in 0..7)
                if(board.getBox(x+dx, y+dy).getPiece() == null)
                    options.add(board.getBox(x+dx, y+dy))
        }

        return options
    }

    override fun killOptions(board: Board, start: Spot): MutableList<Spot> {
        return mutableListOf()
    }

}