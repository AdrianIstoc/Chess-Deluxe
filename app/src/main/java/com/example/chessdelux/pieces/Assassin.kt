package com.example.chessdelux.pieces

import com.example.chessdelux.R
import com.example.chessdelux.board.Board
import com.example.chessdelux.board.Spot

class Assassin(white: Boolean) : Piece(white, PieceType.ASSASSIN, 4, Int.MAX_VALUE) {
    override var imageResource: Int? = if (white) R.drawable.assassin_white else R.drawable.assassin_black

    override val evolutionOptions: List<PieceType> = listOf()

    override fun moveOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        var x = start.getX()
        var y = start.getY()

        while (++x <= start.getX() + 2)
            if(x < 8)
                if(board.getBox(x, y).getPiece() != null)
                    break
                else options.add(board.getBox(x, y))

        x = start.getX()

        while (++y <= start.getY() + 2)
            if(y < 8)
                if(board.getBox(x, y).getPiece() != null)
                    break
                else options.add(board.getBox(x, y))

        y = start.getY()

        while (--x >= start.getX() - 2)
            if(x >= 0)
                if(board.getBox(x, y).getPiece() != null)
                    break
                else options.add(board.getBox(x, y))

        x = start.getX()

        while (--y >= start.getY() - 2)
            if(y >= 0)
                if(board.getBox(x, y).getPiece() != null)
                    break
                else options.add(board.getBox(x, y))

        return options
    }

    override fun killOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        val x = start.getX()
        val y = start.getY()
        val directions = listOf(
            Pair(-2,-1), Pair(-2,0), Pair(-2,1),
            Pair(-1,2), Pair(0,2), Pair(1,2),
            Pair(2,1), Pair(2,0), Pair(2,-1),
            Pair(1,-2), Pair(0,-2), Pair(-1,-2))

        for ((dx, dy) in directions) {
            if(x+dx in 0 .. 7 && y+dy in 0 .. 7) {
                val spot = board.getBox(x + dx, y + dy)
                if (spot.getPiece() !is Fortress)
                    if (spot.getPiece()?.isWhite() == !isWhite())
                        options.add(board.getBox(x + dx, y + dy))
            }
        }

        return options
    }
}