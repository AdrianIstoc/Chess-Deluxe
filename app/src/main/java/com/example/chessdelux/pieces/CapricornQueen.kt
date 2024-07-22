package com.example.chessdelux.pieces

import com.example.chessdelux.R
import com.example.chessdelux.board.Board
import com.example.chessdelux.board.Spot

class CapricornQueen(white: Boolean) : Piece(white, PieceType.CAPRICORN_QUEEN, 18, Int.MAX_VALUE) {
    override var imageResource: Int? = if (white) R.drawable.capricorn_queen_white else R.drawable.capricorn_queen_black

    override val evolutionOptions: List<PieceType> = listOf()

    override fun moveOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        var x = start.getX()
        var y = start.getY()

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

        y = start.getY()

        if(board.isRiverOnBoard()){
            if(checkNextRiver(board, start)){
                val river = board.getRiverSpots()
                for(spot in river){
                    val riverOptions = spot.getPiece()?.moveOptions(board, spot)
                    for(option in riverOptions!!){
                        options.add(option)
                    }
                }
            }
        }

        return options
    }

    override fun killOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        var x = start.getX()
        var y = start.getY()

        while (++x < 8){
            val spot = board.getBox(x, y)
            if(board.getBox(x, y).getPiece() is RiverBridge)
                continue
            else if(spot.getPiece() is Fortress)
                break
            else if (spot.getPiece()?.isWhite() == !isWhite() && spot.getPiece()?.isRiver() == false) {
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
            else if(spot.getPiece() is Fortress)
                break
            else if (spot.getPiece()?.isWhite() == !isWhite() && spot.getPiece()?.isRiver() == false) {
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
            else if(spot.getPiece() is Fortress)
                break
            else if (spot.getPiece()?.isWhite() == !isWhite() && spot.getPiece()?.isRiver() == false) {
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
            else if(spot.getPiece() is Fortress)
                break
            else if (spot.getPiece()?.isWhite() == !isWhite() && spot.getPiece()?.isRiver() == false) {
                options.add(spot)
                break
            }
            else if (spot.getPiece() != null)
                break
        }

        board.getPieceSpotThatChecks(this.isWhite(), board)?.let { options.add(it) }

        return options
    }

    private fun checkNextRiver(board: Board, start: Spot): Boolean{
        val direction = listOf(
            Pair(1, 0),
            Pair(0, 1),
            Pair(-1, 0),
            Pair(0, -1)
        )
        val x = start.getX()
        val y = start.getY()
        for((dx, dy) in direction){
            if(x+dx in 0..7 && y+dy in 0..7) {
                val box = board.getBox(x + dx, y + dy)
                if (box.getPiece() is RiverStart ||
                    box.getPiece() is RiverEnd ||
                    box.getPiece() is RiverHorizontal ||
                    box.getPiece() is RiverVertical ||
                    box.getPiece() is RiverLeftUp ||
                    box.getPiece() is RiverLeftDown ||
                    box.getPiece() is RiverRightUp ||
                    box.getPiece() is RiverRightDown ||
                    box.getPiece() is RiverBridge
                )
                    return true
            }
        }
        return false
    }
}