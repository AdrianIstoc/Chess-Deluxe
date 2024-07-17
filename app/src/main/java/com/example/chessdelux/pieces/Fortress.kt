package com.example.chessdelux.pieces

import com.example.chessdelux.R
import com.example.chessdelux.board.Board
import com.example.chessdelux.board.Spot

class Fortress(white: Boolean) : Piece(white, PieceType.FORTRESS, 20, Int.MAX_VALUE) {
    override var imageResource: Int? = if (white) R.drawable.fortress_white else R.drawable.fortress_black

    override val evolutionOptions: List<PieceType> = listOf()


    override fun moveOptions(board: Board, start: Spot): MutableList<Spot> {
        return mutableListOf()
    }

    override fun killOptions(board: Board, start: Spot): MutableList<Spot> {
        return mutableListOf()
    }

}