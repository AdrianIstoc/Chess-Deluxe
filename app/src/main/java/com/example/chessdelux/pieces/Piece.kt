package com.example.chessdelux.pieces

import com.example.chessdelux.board.*
import com.example.chessdelux.game.Game

abstract class Piece(private var white: Boolean, private var type: PieceType) {
    private var killed: Boolean = false     // true if the piece was killed
    open var imageResource: Int? = null     // the piece image
    private var selected: Boolean = false   // true if the piece is selected

    // return if the piece is selected
    fun isSelected(): Boolean {
        return selected
    }

    // set if the piece is selected
    fun setSelected(selected: Boolean) {
        this.selected = selected
    }

    // return if the piece is white
    fun isWhite(): Boolean {
        return white
    }

    // set if the piece is white
    fun setWhite(white: Boolean) {
        this.white = white
    }

    // return if the piece was killed
    fun isKilled(): Boolean {
        return killed
    }

    // set if the piece was killed
    fun setKilled(killed: Boolean) {
        this.killed = killed
    }

    // set the piece type
    fun setType(type: PieceType) {
        this.type = type
    }

    // return the piece type
    fun getType(): PieceType {
        return type
    }

    // return the piece possible moves
    abstract fun moveOptions(board: Board, start: Spot): List<Spot>
}
