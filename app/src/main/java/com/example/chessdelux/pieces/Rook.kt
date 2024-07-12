package com.example.chessdelux.pieces

import com.example.chessdelux.R
import com.example.chessdelux.board.*

class Rook(white: Boolean) : Piece(white, PieceType.ROOK, 5) {
    override var imageResource: Int? = if (white) R.drawable.rook_white else R.drawable.rook_black

    var rookMoved = false
        private set

    fun setRookMoved() {
        this.rookMoved = true
    }

    fun isRookMoved(): Boolean {
        return rookMoved
    }

    override fun moveOptions(board: Board, start: Spot): MutableList<Spot> {
        val options= mutableListOf<Spot>()
        var x = start.getX()
        var y = start.getY()

        while (++x < 8)
            if(board.getBox(x, y).getPiece()?.isWhite() != isWhite()) {
                options.add(board.getBox(x, y))
                if(board.getBox(x, y).getPiece() != null)
                    break
            }
            else break

        x = start.getX()

        while (++y < 8)
            if(board.getBox(x, y).getPiece()?.isWhite() != isWhite()) {
                options.add(board.getBox(x, y))
                if(board.getBox(x, y).getPiece() != null)
                    break
            }
            else break

        y = start.getY()

        while (--x >= 0)
            if(board.getBox(x, y).getPiece()?.isWhite() != isWhite()) {
                options.add(board.getBox(x, y))
                if(board.getBox(x, y).getPiece() != null)
                    break
            }
            else break

        x = start.getX()

        while (--y >= 0)
            if(board.getBox(x, y).getPiece()?.isWhite() != isWhite()) {
                options.add(board.getBox(x, y))
                if(board.getBox(x, y).getPiece() != null)
                    break
            }
            else break

        return options

    }

    // return the rook kill options
    override fun killOptions(board: Board, start: Spot): MutableList<Spot> {
        val options= mutableListOf<Spot>()
        var x = start.getX()
        var y = start.getY()

        while (++x < 8)
            if(board.getBox(x, y).getPiece()?.isWhite() != isWhite()) {
                options.add(board.getBox(x, y))
                if(board.getBox(x, y).getPiece() != null)
                    break
            }
            else break

        x = start.getX()

        while (++y < 8)
            if(board.getBox(x, y).getPiece()?.isWhite() != isWhite()) {
                options.add(board.getBox(x, y))
                if(board.getBox(x, y).getPiece() != null)
                    break
            }
            else break

        y = start.getY()

        while (--x >= 0)
            if(board.getBox(x, y).getPiece()?.isWhite() != isWhite()) {
                options.add(board.getBox(x, y))
                if(board.getBox(x, y).getPiece() != null)
                    break
            }
            else break

        x = start.getX()

        while (--y >= 0)
            if(board.getBox(x, y).getPiece()?.isWhite() != isWhite()) {
                options.add(board.getBox(x, y))
                if(board.getBox(x, y).getPiece() != null)
                    break
            }
            else break

        return options
    }
}