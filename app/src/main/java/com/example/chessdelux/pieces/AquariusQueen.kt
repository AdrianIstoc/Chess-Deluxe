package com.example.chessdelux.pieces

import com.example.chessdelux.R
import com.example.chessdelux.board.Board
import com.example.chessdelux.board.Spot

class AquariusQueen(white: Boolean) : Piece(white, PieceType.AQUARIUS_QUEEN, 18, Int.MAX_VALUE) {
    override var imageResource: Int? = if (white) R.drawable.aquarius_queen_white else R.drawable.aquarius_queen_black

    override val evolutionOptions: List<PieceType> = listOf()

    override fun moveOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        if(board.isRiverOnBoard()) {
            var x = start.getX()
            var y = start.getY()

            while (++x <= start.getX() + 3 && ++y <= start.getY() + 3)
                if (x < 8 && y < 8)
                    if (board.getBox(x, y).getPiece() is RiverBridge)
                        continue
                    else if (board.getBox(x, y).getPiece() != null)
                        break
                    else options.add(board.getBox(x, y))

            x = start.getX()
            y = start.getY()

            while (++x <= start.getX() + 3 && --y >= start.getY() - 3)
                if (x < 8 && y >= 0)
                    if (board.getBox(x, y).getPiece() is RiverBridge)
                        continue
                    else if (board.getBox(x, y).getPiece() != null)
                        break
                    else options.add(board.getBox(x, y))

            x = start.getX()
            y = start.getY()

            while (--x >= start.getX() - 3 && ++y <= start.getY() + 3)
                if (x >= 0 && y < 8)
                    if (board.getBox(x, y).getPiece() is RiverBridge)
                        continue
                    else if (board.getBox(x, y).getPiece() != null)
                        break
                    else options.add(board.getBox(x, y))

            x = start.getX()
            y = start.getY()

            while (--x >= start.getX() - 3 && --y >= start.getY() - 3)
                if (x >= 0 && y >= 0)
                    if (board.getBox(x, y).getPiece() is RiverBridge)
                        continue
                    else if (board.getBox(x, y).getPiece() != null)
                        break
                    else options.add(board.getBox(x, y))

            x = start.getX()
            y = start.getY()

            while (++x <= start.getX() + 3)
                if (x < 8)
                    if (board.getBox(x, y).getPiece() is RiverBridge)
                        continue
                    else if (board.getBox(x, y).getPiece() != null)
                        break
                    else options.add(board.getBox(x, y))

            x = start.getX()

            while (--x >= start.getX() - 3)
                if (x >= 0)
                    if (board.getBox(x, y).getPiece() is RiverBridge)
                        continue
                    else if (board.getBox(x, y).getPiece() != null)
                        break
                    else options.add(board.getBox(x, y))

            x = start.getX()

            while (++y <= start.getY() + 3)
                if (y < 8)
                    if (board.getBox(x, y).getPiece() is RiverBridge)
                        continue
                    else if (board.getBox(x, y).getPiece() != null)
                        break
                    else options.add(board.getBox(x, y))

            y = start.getY()

            while (--y >= start.getY() - 3)
                if (y >= 0)
                    if (board.getBox(x, y).getPiece() is RiverBridge)
                        continue
                    else if (board.getBox(x, y).getPiece() != null)
                        break
                    else options.add(board.getBox(x, y))

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

        }
        else {
            val spots = listOf(
                Pair(3,2), Pair(3,3), Pair(3,4), Pair(3,5),
                Pair(4,2), Pair(4,3), Pair(4,4), Pair(4,5))

            for (spot in spots) {
                val bridgeSpot = board.getBox(spot.first, spot.second)
                if (bridgeSpot.getPiece() == null)
                    if((board.getBox(spot.first, spot.second-1).getPiece() == null || board.getBox(spot.first, spot.second-1).getPiece() is Pawn)
                        && (board.getBox(spot.first, spot.second+1).getPiece() == null || board.getBox(spot.first, spot.second+1).getPiece() is Pawn))
                        options.add(bridgeSpot)
            }
        }
        return options
    }

    override fun killOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        if(board.isRiverOnBoard()) {
            var x = start.getX()
            var y = start.getY()

            while (++x <= start.getX() + 3 && ++y <= start.getY() + 3)
                if (x < 8 && y < 8) {
                    val spot = board.getBox(x, y)
                    if (board.getBox(x, y).getPiece() is RiverBridge)
                        continue
                    else if (spot.getPiece() is Fortress || spot.getPiece()?.isRiver() == true)
                        break
                    else if (spot.getPiece()?.isWhite() == !isWhite()) {
                        options.add(spot)
                        break
                    } else if (spot.getPiece() != null)
                        break
                }

            x = start.getX()
            y = start.getY()

            while (++x <= start.getX() + 3 && --y >= start.getY() - 3)
                if (x < 8 && y >= 0) {
                    val spot = board.getBox(x, y)
                    if (board.getBox(x, y).getPiece() is RiverBridge)
                        continue
                    else if (spot.getPiece() is Fortress || spot.getPiece()?.isRiver() == true)
                        break
                    else if (spot.getPiece()?.isWhite() == !isWhite()) {
                        options.add(spot)
                        break
                    } else if (spot.getPiece() != null)
                        break
                }

            x = start.getX()
            y = start.getY()

            while (--x >= start.getX() - 3 && ++y <= start.getY() + 3)
                if (x >= 0 && y < 8) {
                    val spot = board.getBox(x, y)
                    if (board.getBox(x, y).getPiece() is RiverBridge)
                        continue
                    else if (spot.getPiece() is Fortress || spot.getPiece()?.isRiver() == true)
                        break
                    else if (spot.getPiece()?.isWhite() == !isWhite()) {
                        options.add(spot)
                        break
                    } else if (spot.getPiece() != null)
                        break
                }

            x = start.getX()
            y = start.getY()

            while (--x >= start.getX() - 3 && --y >= start.getY() - 3)
                if (x >= 0 && y >= 0) {
                    val spot = board.getBox(x, y)
                    if (board.getBox(x, y).getPiece() is RiverBridge)
                        continue
                    else if (spot.getPiece() is Fortress || spot.getPiece()?.isRiver() == true)
                        break
                    else if (spot.getPiece()?.isWhite() == !isWhite()) {
                        options.add(spot)
                        break
                    } else if (spot.getPiece() != null)
                        break
                }

            x = start.getX()
            y = start.getY()

            while (++x <= start.getX() + 3)
                if (x < 8) {
                    val spot = board.getBox(x, y)
                    if (board.getBox(x, y).getPiece() is RiverBridge)
                        continue
                    else if (spot.getPiece() is Fortress || spot.getPiece()?.isRiver() == true)
                        break
                    else if (spot.getPiece()?.isWhite() == !isWhite()) {
                        options.add(spot)
                        break
                    } else if (spot.getPiece() != null)
                        break
                }

            x = start.getX()

            while (--x >= start.getX() - 3)
                if (x >= 0) {
                    val spot = board.getBox(x, y)
                    if (board.getBox(x, y).getPiece() is RiverBridge)
                        continue
                    else if (spot.getPiece() is Fortress || spot.getPiece()?.isRiver() == true)
                        break
                    else if (spot.getPiece()?.isWhite() == !isWhite()) {
                        options.add(spot)
                        break
                    } else if (spot.getPiece() != null)
                        break
                }

            x = start.getX()

            while (++y <= start.getY() + 3)
                if (y < 8) {
                    val spot = board.getBox(x, y)
                    if (board.getBox(x, y).getPiece() is RiverBridge)
                        continue
                    else if (spot.getPiece() is Fortress || spot.getPiece()?.isRiver() == true)
                        break
                    else if (spot.getPiece()?.isWhite() == !isWhite()) {
                        options.add(spot)
                        break
                    } else if (spot.getPiece() != null)
                        break
                }

            y = start.getY()

            while (--y >= start.getY() - 3)
                if (y >= 0) {
                    val spot = board.getBox(x, y)
                    if (board.getBox(x, y).getPiece() is RiverBridge)
                        continue
                    else if (spot.getPiece() is Fortress || spot.getPiece()?.isRiver() == true)
                        break
                    else if (spot.getPiece()?.isWhite() == !isWhite()) {
                        options.add(spot)
                        break
                    } else if (spot.getPiece() != null)
                        break
                }
        }

        return options
    }

    fun checkIfSummonRiver(end: Spot, board: Board) {
        board.getBox(end.getX(),end.getY()).setPiece(RiverBridge(this.isWhite()))

        var y = end.getY()
        var x = end.getX()

        val riverLeftList = mutableListOf<Pair<Int, Int>>()
        val riverRightList = mutableListOf<Pair<Int, Int>>()

        if(this.isWhite()){
            y--
            while(y >= 0 && x in 0..7){
                if(y == 0) {
                    riverLeftList.add(Pair(x, y))
                    break
                }
                else if(x == 0 || x == 7){
                    riverLeftList.add(Pair(x, y))
                    break
                }
                else if(board.getBox(x, y-1).getPiece() is Pawn || board.getBox(x, y-1).getPiece() == null){
                    riverLeftList.add(Pair(x, y))
                    y--
                }
                else if(board.getBox(x-1, y).getPiece() is Pawn || board.getBox(x-1, y).getPiece() == null){
                    riverLeftList.add(Pair(x, y))
                    x--
                }
                else if(board.getBox(x+1, y).getPiece() is Pawn || board.getBox(x+1, y).getPiece() == null){
                    riverLeftList.add(Pair(x, y))
                    x++
                }
                else{
                    riverLeftList.add(Pair(x, y))
                    break
                }
            }
            x = end.getX()
            y = end.getY()+1
            while(y < 8 && x in 0..7){
                if(y == 7) {
                    riverRightList.add(Pair(x, y))
                    break
                }
                else if(x == 0 || x == 7) {
                    riverRightList.add(Pair(x, y))
                    break
                }
                else if(board.getBox(x, y+1).getPiece() is Pawn || board.getBox(x, y+1).getPiece() == null) {
                    riverRightList.add(Pair(x, y))
                    y++
                }
                else if(board.getBox(x+1, y).getPiece() is Pawn || board.getBox(x+1, y).getPiece() == null) {
                    riverRightList.add(Pair(x, y))
                    x++
                }
                else if(board.getBox(x-1, y).getPiece() is Pawn || board.getBox(x-1, y).getPiece() == null) {
                    riverRightList.add(Pair(x, y))
                    x--
                }
                else {
                    riverRightList.add(Pair(x, y))
                    break
                }
            }
        }
        else{
            y--
            while(y >= 0 && x in 0..7){
                if(y == 0) {
                    riverLeftList.add(Pair(x, y))
                    break
                }
                else if(x == 0 || x == 7){
                    riverLeftList.add(Pair(x, y))
                }
                else if(board.getBox(x, y-1).getPiece() is Pawn || board.getBox(x, y-1).getPiece() == null){
                    riverLeftList.add(Pair(x, y))
                    y--
                }
                else if(board.getBox(x+1, y).getPiece() is Pawn || board.getBox(x+1, y).getPiece() == null){
                    riverLeftList.add(Pair(x, y))
                    x++
                }
                else if(board.getBox(x-1, y).getPiece() is Pawn || board.getBox(x-1, y).getPiece() == null){
                    riverLeftList.add(Pair(x, y))
                    x--
                }
                else{
                    riverLeftList.add(Pair(x, y))
                    break
                }
            }
            x=end.getX()
            y=end.getY()+1
            while(y < 8 && x in 0..7){
                if(y == 7) {
                    riverRightList.add(Pair(x, y))
                    break
                }
                else if(x == 0 || x == 7) {
                    riverRightList.add(Pair(x, y))
                    break
                }
                else if(board.getBox(x, y+1).getPiece() is Pawn || board.getBox(x,y+1).getPiece() == null) {
                    riverRightList.add(Pair(x, y))
                    y++
                }
                else if(board.getBox(x-1, y).getPiece() is Pawn || board.getBox(x-1, y).getPiece() == null) {
                    riverRightList.add(Pair(x, y))
                    x--
                }
                else if(board.getBox(x+1, y).getPiece() is Pawn || board.getBox(x+1, y).getPiece() == null) {
                    riverRightList.add(Pair(x, y))
                    x++
                }
                else {
                    riverRightList.add(Pair(x, y))
                    break
                }
            }
        }

        for(i in 0 until riverLeftList.size){
            if(riverLeftList.size == 1)
                board.getBox(riverLeftList[i].first, riverLeftList[i].second).setPiece(RiverStart(this.isWhite()))
            else if(i == 0){
                // next to bridge
                if(riverLeftList[i].second == riverLeftList[1].second+1)
                    board.getBox(riverLeftList[i].first, riverLeftList[i].second).setPiece(RiverHorizontal(this.isWhite()))
                else if(riverLeftList[i].first == riverLeftList[1].first+1)
                    board.getBox(riverLeftList[i].first, riverLeftList[i].second).setPiece(RiverRightUp(this.isWhite()))
                else board.getBox(riverLeftList[i].first, riverLeftList[i].second).setPiece(RiverRightDown(this.isWhite()))
            }
            else if(i == riverLeftList.size-1){
                // river start
                if(riverLeftList[i].second == 0 || riverLeftList[i-1].second == riverLeftList[i].second+1)
                    board.getBox(riverLeftList[i].first, riverLeftList[i].second).setPiece(RiverStart(this.isWhite()))
                else if(riverLeftList[i].first == 0 || riverLeftList[i-1].first == riverLeftList[i].first+1)
                    board.getBox(riverLeftList[i].first, riverLeftList[i].second).setPiece(RiverStartUp(this.isWhite()))
                else if(riverLeftList[i].first == 7 || riverLeftList[i-1].first == riverLeftList[i].first-1)
                    board.getBox(riverLeftList[i].first, riverLeftList[i].second).setPiece(RiverStartDown(this.isWhite()))
            }
            else{
                if(riverLeftList[i-1].second == riverLeftList[i].second+1) {
                    if(riverLeftList[i+1].second == riverLeftList[i].second-1)
                        board.getBox(riverLeftList[i].first,riverLeftList[i].second).setPiece(RiverHorizontal(this.isWhite()))
                    else if(riverLeftList[i+1].first == riverLeftList[i].first+1)
                        board.getBox(riverLeftList[i].first, riverLeftList[i].second).setPiece(RiverRightDown(this.isWhite()))
                    else board.getBox(riverLeftList[i].first, riverLeftList[i].second).setPiece(RiverRightUp(this.isWhite()))
                }
                else if(riverLeftList[i-1].first == riverLeftList[i].first+1) {
                    if(riverLeftList[i+1].second == riverLeftList[i].second-1)
                        board.getBox(riverLeftList[i].first, riverLeftList[i].second).setPiece(RiverLeftDown(this.isWhite()))
                    else board.getBox(riverLeftList[i].first, riverLeftList[i].second).setPiece(RiverVertical(this.isWhite()))
                }
                else board.getBox(riverLeftList[i].first, riverLeftList[i].second).setPiece(RiverLeftUp(this.isWhite()))
            }
        }
        for (i in 0 until riverRightList.size){
            if(riverRightList.size == 1)
                board.getBox(riverRightList[i].first, riverRightList[i].second).setPiece(RiverEnd(this.isWhite()))
            else if(i == 0){
                // next to bridge
                if(riverRightList[1].second == riverRightList[i].second+1)
                    board.getBox(riverRightList[i].first, riverRightList[i].second).setPiece(RiverHorizontal(this.isWhite()))
                else if(riverRightList[1].first == riverRightList[i].first+1)
                    board.getBox(riverRightList[i].first, riverRightList[i].second).setPiece(RiverLeftDown(this.isWhite()))
                else board.getBox(riverRightList[i].first, riverRightList[i].second).setPiece(RiverLeftUp(this.isWhite()))
            }
            else if(i == riverRightList.size-1){
                // river ends
                if(riverRightList[i].second == 7 || riverRightList[i-1].second == riverRightList[i].second-1)
                    board.getBox(riverRightList[i].first, riverRightList[i].second).setPiece(RiverEnd(this.isWhite()))
                else if(riverRightList[i].first == 0 || riverRightList[i-1].first == riverRightList[i].first+1)
                    board.getBox(riverRightList[i].first, riverRightList[i].second).setPiece(RiverEndUp(this.isWhite()))
                else if(riverRightList[i].first == 7 || riverRightList[i-1].first == riverRightList[i].first-1)
                    board.getBox(riverRightList[i].first, riverRightList[i].second).setPiece(RiverEndDown(this.isWhite()))
            }
            else {
                if(riverRightList[i-1].second == riverRightList[i].second-1) {
                    if(riverRightList[i+1].second == riverRightList[i].second+1)
                        board.getBox(riverRightList[i].first,riverRightList[i].second).setPiece(RiverHorizontal(this.isWhite()))
                    else if(riverRightList[i+1].first == riverRightList[i].first+1)
                        board.getBox(riverRightList[i].first, riverRightList[i].second).setPiece(RiverLeftDown(this.isWhite()))
                    else board.getBox(riverRightList[i].first, riverRightList[i].second).setPiece(RiverLeftUp(this.isWhite()))
                }
                else if(riverRightList[i-1].first == riverRightList[i].first+1) {
                    if(riverRightList[i+1].second == riverRightList[i].second+1)
                        board.getBox(riverRightList[i].first, riverRightList[i].second).setPiece(RiverRightDown(this.isWhite()))
                    else board.getBox(riverRightList[i].first, riverRightList[i].second).setPiece(RiverVertical(this.isWhite()))
                }
                else board.getBox(riverRightList[i].first, riverRightList[i].second).setPiece(RiverRightUp(this.isWhite()))
            }
        }
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