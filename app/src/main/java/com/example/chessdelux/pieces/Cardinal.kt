package com.example.chessdelux.pieces

import com.example.chessdelux.R
import com.example.chessdelux.board.Board
import com.example.chessdelux.board.Spot

class Cardinal(white: Boolean) : Piece(white, PieceType.CARDINAL, 6, Int.MAX_VALUE) {
    override var imageResource: Int? = if (white) R.drawable.cardinal_white else R.drawable.cardinal_black

    override val evolutionOptions: List<PieceType> = listOf()

    override fun moveOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        var x = start.getX()
        var y = start.getY()
        var loop = 0

        while (x<7 && y<7) {
            if (loop % 2 == 1)
                x++
            y++
            loop++
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if (board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))
        }

        x = start.getX()
        y = start.getY()
        loop = 0

        while (x<7 && y>0) {
            if (loop % 2 == 1)
                x++
            y--
            loop++
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if (board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))
        }

        x = start.getX()
        y = start.getY()
        loop = 0

        while(x>0 && y<7){
            if (loop % 2 == 1)
                x--
            y++
            loop++
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if (board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))
        }

        x = start.getX()
        y = start.getY()
        loop = 0

        while (x>0 && y>0) {
            if (loop % 2 == 1)
                x--
            y--
            loop++
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if (board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))
        }

        x = start.getX()
        y = start.getY()
        loop = 0

        while (y<7 && x<7){
            if (loop % 2 == 1)
                y++
            x++
            loop++
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if (board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))
        }

        x = start.getX()
        y = start.getY()
        loop = 0

        while (y<7 && x>0){
            if (loop % 2 == 1)
                y++
            x--
            loop++
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if (board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))
        }

        x = start.getX()
        y = start.getY()
        loop = 0

        while (y>0 && x<7){
            if (loop % 2 == 1)
                y--
            x++
            loop++
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if (board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))
        }

        x = start.getX()
        y = start.getY()
        loop = 0

        while (y>0 && x>0){
            if (loop % 2 == 1)
                y--
            x--
            loop++
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if (board.getBox(x, y).getPiece() != null)
                break
            else options.add(board.getBox(x, y))
        }

        return options
    }

    override fun killOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        var x = start.getX()
        var y = start.getY()
        var loop = 0

        while (x<7 && y<7) {
            if (loop % 2 == 1)
                x++
            y++
            loop++
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
        loop = 0

        while (x<7 && y>0) {
            if (loop % 2 == 1)
                x++
            y--
            loop++
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
        loop = 0

        while(x>0 && y<7){
            if (loop % 2 == 1)
                x--
            y++
            loop++
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
        loop = 0

        while (x>0 && y>0) {
            if (loop % 2 == 1)
                x--
            y--
            loop++
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
        loop = 0

        while (y<7 && x<7){
            if (loop % 2 == 1)
                y++
            x++
            loop++
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
        loop = 0

        while (y<7 && x>0){
            if (loop % 2 == 1)
                y++
            x--
            loop++
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
        loop = 0

        while (y>0 && x<7){
            if (loop % 2 == 1)
                y--
            x++
            loop++
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
        loop = 0

        while (y>0 && x>0){
            if (loop % 2 == 1)
                y--
            x--
            loop++
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