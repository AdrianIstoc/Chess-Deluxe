package com.example.chessdelux.pieces

import com.example.chessdelux.R
import com.example.chessdelux.board.*
import com.example.chessdelux.game.Game

class Bishop(white: Boolean) : Piece(white, PieceType.BISHOP, 3, 25) {
    // the bishop image
    override var imageResource: Int? = if (white) R.drawable.bishop_white else R.drawable.bishop_black

    override val evolutionOptions: List<PieceType> = listOf(PieceType.CARDINAL)


    // return the bishop possible moves
    override fun moveOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        var x = start.getX()
        var y = start.getY()

        // checking all diagonals
        // also stopping at a black piece or before a white piece
        while (++x < 8 && ++y < 8)
            if(board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))

        x = start.getX()
        y = start.getY()

        while (++x < 8 && --y >= 0)
            if(board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))

        x = start.getX()
        y = start.getY()

        while (--x >= 0 && ++y < 8)
            if(board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))

        x = start.getX()
        y = start.getY()

        while (--x >= 0 && --y >= 0)
            if(board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))

        return options
    }

    // return the bishop kill options
    override fun killOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        var x = start.getX()
        var y = start.getY()

        // checking all diagonals
        // also stopping at a black piece or before a white piece

        while (++x < 8 && ++y < 8){
            val spot = board.getBox(x, y)
            if(spot.getPiece() is Fortress)
                break
            else if (spot.getPiece()?.isWhite() == !isWhite()) {
                options.add(spot)
                break
            }
            else if (spot.getPiece() != null)
                break
        }

        x = start.getX()
        y = start.getY()

        while (++x < 8 && --y >= 0){
            val spot = board.getBox(x, y)
            if(spot.getPiece() is Fortress)
                break
            else if (spot.getPiece()?.isWhite() == !isWhite()) {
                options.add(spot)
                break
            }
            else if (spot.getPiece() != null)
                break
        }

        x = start.getX()
        y = start.getY()

        while (--x >= 0 && ++y < 8){
            val spot = board.getBox(x, y)
            if(spot.getPiece() is Fortress)
                break
            else if (spot.getPiece()?.isWhite() == !isWhite()) {
                options.add(spot)
                break
            }
            else if (spot.getPiece() != null)
                break
        }

        x = start.getX()
        y = start.getY()

        while (--x >= 0 && --y >= 0){
            val spot = board.getBox(x, y)
            if(spot.getPiece() is Fortress)
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
}