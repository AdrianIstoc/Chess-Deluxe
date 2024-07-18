package com.example.chessdelux.pieces

import com.example.chessdelux.R
import com.example.chessdelux.board.Board
import com.example.chessdelux.board.Spot

class Paladin(white: Boolean) : Piece(white, PieceType.PALADIN, 7, Int.MAX_VALUE) {
    override var imageResource: Int? = if (white) R.drawable.paladin_white else R.drawable.paladin_black

    override val evolutionOptions: List<PieceType> = listOf()


    override fun moveOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        var x = start.getX()
        var y = start.getY()

        while (++x <= start.getX() + 3)
            if(x < 8)
                if(board.getBox(x, y).getPiece() != null)
                    break
                else options.add(board.getBox(x, y))

        x = start.getX()

        while (++y <= start.getY() + 3)
            if(y < 8)
                if(board.getBox(x, y).getPiece() != null)
                    break
                else options.add(board.getBox(x, y))

        y = start.getY()

        while (--x >= start.getX() - 3)
            if(x >= 0)
                if(board.getBox(x, y).getPiece() != null)
                    break
                else options.add(board.getBox(x, y))

        x = start.getX()

        while (--y >= start.getY() - 3)
            if(y >= 0)
                if(board.getBox(x, y).getPiece() != null)
                    break
                else options.add(board.getBox(x, y))

        y = start.getY()

        if (x + 2 < 8 && x + 3 < 8)
            if(board.getBox(x+2, y).getPiece()?.isWhite() == !isWhite() && board.getBox(x+2, y).getPiece() !is Fortress)
                if(board.getBox(x+3, y).getPiece() == null && board.getBox(x+1, y).getPiece() == null)
                    options.add(board.getBox(x+2, y))

        if (x - 2 >= 0 && x - 3 >= 0)
            if(board.getBox(x-2, y).getPiece()?.isWhite() == !isWhite() && board.getBox(x-2, y).getPiece() !is Fortress)
                if(board.getBox(x-3, y).getPiece() == null && board.getBox(x-1, y).getPiece() == null)
                    options.add(board.getBox(x-2, y))

        if (y + 2 < 8 && y + 3 < 8)
            if(board.getBox(x, y+2).getPiece()?.isWhite() == !isWhite() && board.getBox(x, y+2).getPiece() !is Fortress)
                if(board.getBox(x, y+3).getPiece() == null && board.getBox(x, y+1).getPiece() == null)
                    options.add(board.getBox(x, y+2))

        if (y - 2 >= 0 && y - 3 >= 0)
            if(board.getBox(x, y-2).getPiece()?.isWhite() == !isWhite() && board.getBox(x, y-2).getPiece() !is Fortress)
                if(board.getBox(x, y-3).getPiece() == null && board.getBox(x, y-1).getPiece() == null)
                    options.add(board.getBox(x, y-2))

        return options
    }

    override fun killOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        val x = start.getX()
        val y = start.getY()
        val directions = listOf(
            Pair(0,1), Pair(0,-1),
            Pair(1,0), Pair(-1,0))

        for((dx, dy) in directions)
            if (x + dx in 0..7 && y + dy in 0..7){
                val spot = board.getBox(x + dx, y + dy)
                if (spot.getPiece() !is Fortress)
                    if (spot.getPiece()?.isWhite() == !isWhite())
                        options.add(board.getBox(x + dx, y + dy))
            }

        return options
    }

    fun checkIfPushing(start: Spot, end: Spot, board: Board) {
        val endPiece = end.getPiece()
        if(endPiece?.isWhite() == !isWhite()) {
            val sx = start.getX()
            val sy = start.getY()
            val ex = end.getX()
            val ey = end.getY()
            if(sx + 2 == ex)
                board.movePiece(end, board.getBox(ex+1, ey))
            else if(sx - 2 == ex)
                board.movePiece(end, board.getBox(ex-1, ey))
            else if(sy + 2 == ey)
                board.movePiece(end, board.getBox(ex, ey+1))
            else if(sy - 2 == ey)
                board.movePiece(end, board.getBox(ex, ey-1))
        }
    }
}