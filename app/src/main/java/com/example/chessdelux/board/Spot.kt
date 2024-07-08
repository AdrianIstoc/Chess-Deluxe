package com.example.chessdelux.board

import com.example.chessdelux.pieces.Piece

class Spot(private var x: Int,private var y: Int,private var piece: Piece?) {
    private var selectableSpot: Boolean = false     // shows if the spot is selectable for a move
    private var killableSpot: Boolean = false       // shows if the spot is selectable for a kill

    fun isKillableSpot(): Boolean {                 // returns if the spot is killable or not
        return killableSpot
    }

    fun setKillableSpot(killable: Boolean) {       // sets the killable state of the spot
        this.killableSpot = killable
    }

    fun isSelectableSpot(): Boolean {               // returns if the spot is selectable or not
        return selectableSpot
    }

    fun setSelectableSpot(selectable: Boolean) {    // sets the selectable state of the spot
        this.selectableSpot = selectable
    }

    fun getPiece(): Piece? {                        // get the piece that is on the spot if any
        return piece
    }

    fun setPiece(p: Piece?) {                       // set te piece on the spot
        this.piece = p
    }

    fun getX(): Int {                               // return the row of the spot
        return x
    }

    fun setX(x: Int) {                              // set the row of the spot
        this.x = x
    }

    fun getY(): Int {                               // return the column of the spot
        return y
    }

    fun setY(y: Int) {                              // set the column of the spot
        this.y = y
    }
}
