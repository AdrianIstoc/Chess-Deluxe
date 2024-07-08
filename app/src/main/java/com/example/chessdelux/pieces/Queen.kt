package com.example.chessdelux.pieces

import com.example.chessdelux.R
import com.example.chessdelux.board.*

class Queen(white: Boolean) : Piece(white, PieceType.QUEEN) {
    override var imageResource: Int? = if (white) R.drawable.queen_white else R.drawable.queen_black

    override fun moveOptions(board: Board, start: Spot): List<Spot> {
        val options = mutableListOf<Spot>()
        var x = start.getX()
        var y = start.getY()

        while (++x < 8 && ++y < 8)
            if(board.getBox(x, y).getPiece()?.isWhite() != isWhite()) {
                options.add(board.getBox(x, y))
                if(board.getBox(x, y).getPiece() != null)
                    break
            }
            else break

        x = start.getX()
        y = start.getY()

        while (++x < 8 && --y >= 0)
            if(board.getBox(x, y).getPiece()?.isWhite() != isWhite()) {
                options.add(board.getBox(x, y))
                if(board.getBox(x, y).getPiece() != null)
                    break
            }
            else break

        x = start.getX()
        y = start.getY()

        while (--x >= 0 && ++y < 8)
            if(board.getBox(x, y).getPiece()?.isWhite() != isWhite()) {
                options.add(board.getBox(x, y))
                if(board.getBox(x, y).getPiece() != null)
                    break
            }
            else break

        x = start.getX()
        y = start.getY()

        while (--x >= 0 && --y >= 0)
            if(board.getBox(x, y).getPiece()?.isWhite() != isWhite()) {
                options.add(board.getBox(x, y))
                if(board.getBox(x, y).getPiece() != null)
                    break
            }
            else break

        x = start.getX()
        y = start.getY()

        while (++x < 8)
            if(board.getBox(x, y).getPiece()?.isWhite() != isWhite()) {
                options.add(board.getBox(x, y))
                if(board.getBox(x, y).getPiece() != null)
                    break
            }
            else break

        x = start.getX()

        while (--x >= 0)
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