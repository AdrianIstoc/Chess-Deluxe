package com.example.chessdelux.pieces

import com.example.chessdelux.R
import com.example.chessdelux.board.*

class Queen(white: Boolean) : Piece(white, PieceType.QUEEN, 9, 30) {
    override var imageResource: Int? = if (white) R.drawable.queen_white else R.drawable.queen_black

    override val evolutionOptions: List<PieceType> = listOf(PieceType.CAPRICORN_QUEEN)

    override fun moveOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        var x = start.getX()
        var y = start.getY()

        while (++x < 8 && ++y < 8)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if(board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))

        x = start.getX()
        y = start.getY()

        while (++x < 8 && --y >= 0)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if(board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))

        x = start.getX()
        y = start.getY()

        while (--x >= 0 && ++y < 8)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if(board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))

        x = start.getX()
        y = start.getY()

        while (--x >= 0 && --y >= 0)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if(board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))

        x = start.getX()
        y = start.getY()

        while (++x < 8)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if(board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))

        x = start.getX()

        while (--x >= 0)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if(board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))

        x = start.getX()

        while (++y < 8)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if(board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))

        y = start.getY()

        while (--y >= 0)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if(board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))

        return options

    }

    // return the queen kill options
        override fun killOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        var x = start.getX()
        var y = start.getY()

        while (++x < 8 && ++y < 8){
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
        y = start.getY()

        while (++x < 8 && --y >= 0){
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
        y = start.getY()

        while (--x >= 0 && ++y < 8){
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
        y = start.getY()

        while (--x >= 0 && --y >= 0){
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
        y = start.getY()

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

}