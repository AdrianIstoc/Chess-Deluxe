package com.example.chessdelux.pieces

import com.example.chessdelux.R
import com.example.chessdelux.board.Board
import com.example.chessdelux.board.Spot

class Thief(white: Boolean) : Piece(white, PieceType.THIEF, 4, Int.MAX_VALUE) {
    override var imageResource: Int? = if (white) R.drawable.thief_white else R.drawable.thief_black

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

        y = start.getY()

        if(x+2 < 8) {
            val pawn = board.getBox(x + 1, y).getPiece()
            val emptySpot = board.getBox(x+2,y).getPiece()
            if (pawn is Pawn && pawn.isWhite() != this.isWhite() && emptySpot == null)
                options.add(board.getBox(x + 2, y))
        }

        if(x-2 >= 0) {
            val pawn = board.getBox(x - 1, y).getPiece()
            val emptySpot = board.getBox(x-2,y).getPiece()
            if (pawn is Pawn && pawn.isWhite() != this.isWhite() && emptySpot == null)
                options.add(board.getBox(x - 2, y))
        }

        if(y+2 < 8) {
            val pawn = board.getBox(x, y + 1).getPiece()
            val emptySpot = board.getBox(x,y+2).getPiece()
            if (pawn is Pawn && pawn.isWhite() != this.isWhite() && emptySpot == null)
                options.add(board.getBox(x, y + 2))
        }

        if(y-2 >= 0) {
            val pawn = board.getBox(x, y - 1).getPiece()
            val emptySpot = board.getBox(x,y-2).getPiece()
            if (pawn is Pawn && pawn.isWhite() != this.isWhite() && emptySpot == null)
                options.add(board.getBox(x, y - 2))
        }

        return options
    }

    override fun killOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        var x = start.getX()
        var y = start.getY()


        //checking diagonals
        if (++x < 8 && ++y < 8)
            if(board.getBox(x, y).getPiece() !is Fortress && board.getBox(x, y).getPiece()?.isWhite() == !isWhite())
                options.add(board.getBox(x, y))

        x = start.getX()
        y = start.getY()

        if(++x < 8 && --y >= 0)
            if(board.getBox(x, y).getPiece() !is Fortress && board.getBox(x, y).getPiece()?.isWhite() == !isWhite())
                options.add(board.getBox(x, y))

        x = start.getX()
        y = start.getY()

        if(--x >= 0 && ++y < 8)
            if(board.getBox(x, y).getPiece() !is Fortress && board.getBox(x, y).getPiece()?.isWhite() == !isWhite())
                options.add(board.getBox(x, y))

        x = start.getX()
        y = start.getY()

        if(--x >= 0 && --y >= 0)
            if(board.getBox(x, y).getPiece() !is Fortress && board.getBox(x, y).getPiece()?.isWhite() == !isWhite())
                options.add(board.getBox(x, y))

        return options
    }

    fun checkIfStealing(start: Spot, end: Spot, board: Board){
        val pawn: Piece?
        val pawnSpot: Spot?
        val startX = start.getX()
        val startY = start.getY()
        val endX = end.getX()
        val endY = end.getY()

        if(startX+2 == endX) {
            pawnSpot = board.getBox(startX + 1, startY)
            pawn = pawnSpot.getPiece()
            if(pawn is Pawn) {
                pawnSpot.setPiece(Pawn(this.isWhite()))
                (pawnSpot.getPiece() as Pawn).setPawnMoved(true)
            }
        }
        else if(startX-2 == endX) {
            pawnSpot = board.getBox(startX - 1, startY)
            pawn = pawnSpot.getPiece()
            if(pawn is Pawn) {
                pawnSpot.setPiece(Pawn(this.isWhite()))
                (pawnSpot.getPiece() as Pawn).setPawnMoved(true)
            }
        }
        else if(startY +2 == endY){
            pawnSpot = board.getBox(startX, startY+1)
            pawn = pawnSpot.getPiece()
            if(pawn is Pawn) {
                pawnSpot.setPiece(Pawn(this.isWhite()))
                (pawnSpot.getPiece() as Pawn).setPawnMoved(true)
            }
        }
        else if(startY -2 == endY){
            pawnSpot = board.getBox(startX, startY-1)
            pawn = pawnSpot.getPiece()
            if(pawn is Pawn) {
                pawnSpot.setPiece(Pawn(this.isWhite()))
                (pawnSpot.getPiece() as Pawn).setPawnMoved(true)
            }
        }
    }
}