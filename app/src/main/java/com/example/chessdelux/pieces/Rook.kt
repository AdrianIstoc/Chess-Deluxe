package com.example.chessdelux.pieces

import com.example.chessdelux.R
import com.example.chessdelux.board.*

class Rook(white: Boolean) : Piece(white, PieceType.ROOK, 5, Int.MAX_VALUE) {
    override var imageResource: Int? = if (white) R.drawable.rook_white else R.drawable.rook_black

    override val evolutionOptions: List<PieceType> = listOf()


    private var rookMoved = false

    fun setRookMoved(value: Boolean) {
        this.rookMoved = value
    }

    fun isRookMoved(): Boolean {
        return rookMoved
    }

    override fun moveOptions(board: Board, start: Spot): MutableList<Spot> {
        val options= mutableListOf<Spot>()
        var x = start.getX()
        var y = start.getY()

        while (++x < 8) {
            val spot = board.getBox(x, y)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if (spot.getPiece() is Rook && spot.getPiece()?.isWhite() == isWhite())
                options.add(spot)
            else if (board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))
        }

        x = start.getX()

        while (++y < 8) {
            val spot = board.getBox(x, y)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if (spot.getPiece() is Rook && spot.getPiece()?.isWhite() == isWhite())
                options.add(spot)
            else if (board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))
        }

        y = start.getY()

        while (--x >= 0) {
            val spot = board.getBox(x, y)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if (spot.getPiece() is Rook && spot.getPiece()?.isWhite() == isWhite())
                options.add(spot)
            else if (board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))
        }

        x = start.getX()

        while (--y >= 0) {
            val spot = board.getBox(x, y)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if (spot.getPiece() is Rook && spot.getPiece()?.isWhite() == isWhite())
                options.add(spot)
            else if (board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))
        }

        return options

    }

    // return the rook kill options
    override fun killOptions(board: Board, start: Spot): MutableList<Spot> {
        val options= mutableListOf<Spot>()
        var x = start.getX()
        var y = start.getY()

        while (++x < 8){
            val spot = board.getBox(x, y)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if(spot.getPiece() is Fortress || spot.getPiece()?.isRiver() == true)
                break
            else if (spot.getPiece()?.isWhite() == !isWhite()) {
                options.add(spot)
                break
            }
            else if (spot.getPiece() != null)
                break
        }

        x = start.getX()

        while (++y < 8){
            val spot = board.getBox(x, y)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if(spot.getPiece() is Fortress || spot.getPiece()?.isRiver() == true)
                break
            else if (spot.getPiece()?.isWhite() == !isWhite()) {
                options.add(spot)
                break
            }
            else if (spot.getPiece() != null)
                break
        }

        y = start.getY()

        while (--x >= 0){
            val spot = board.getBox(x, y)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if(spot.getPiece() is Fortress || spot.getPiece()?.isRiver() == true)
                break
            else if (spot.getPiece()?.isWhite() == !isWhite()) {
                options.add(spot)
                break
            }
            else if (spot.getPiece() != null)
                break
        }

        x = start.getX()

        while (--y >= 0){
            val spot = board.getBox(x, y)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if(spot.getPiece() is Fortress || spot.getPiece()?.isRiver() == true)
                break
            else if (spot.getPiece()?.isWhite() == !isWhite()) {
                options.add(spot)
                break
            }
            else if (spot.getPiece() != null)
                break
        }

        return options
    }

    fun checkIfFortressing(start: Spot, end: Spot){
        val endPiece = end.getPiece()
        if (endPiece is Rook && endPiece.isWhite() == isWhite())
            start.setPiece(Fortress(endPiece.isWhite()))
    }
}