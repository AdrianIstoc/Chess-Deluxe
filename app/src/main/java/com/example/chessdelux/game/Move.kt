package com.example.chessdelux.game

import com.example.chessdelux.board.Spot
import com.example.chessdelux.pieces.Piece

class Move(val player: Player, val start: Spot, val end: Spot) {
    val pieceMoved: Piece? = start.getPiece()       // the piece that moved
    var pieceKilled: Piece? = null                  // the piece that was killed
    private var isCastlingMove: Boolean = false     // true if the move was a king castling

    fun setCastlingMove(castlingMove: Boolean) {    // set the move as a kincastling
        this.isCastlingMove = castlingMove
    }
}