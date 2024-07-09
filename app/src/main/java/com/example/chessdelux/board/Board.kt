package com.example.chessdelux.board

import com.example.chessdelux.pieces.*

class Board {
    private val boxes: Array<Array<Spot?>> = Array(8) { arrayOfNulls(8) } // array of the cells of the chessboard

    // resets the board
    init {
        resetBoard()
    }

    fun getKingSpot(white: Boolean): Spot {
        var kingSpot = Spot(-1, -1, null)        // set the spot of the king as null
        for (i in 0 until 8){
            for (j in 0 until 8){
                val pieceSpot = this.getBox(i, j)
                val piece = pieceSpot.getPiece()
                if(piece is King && piece.isWhite() == white){
                    kingSpot = pieceSpot            // get the current player king
                    return kingSpot
                }
            }
        }
        return kingSpot
    }

    // move a piece
    fun movePiece(start: Spot, end: Spot){
        val piece = start.getPiece()            // get the piece
        if (end.getPiece() != null) {           // check if the arrival spot is occupied by a piece
            end.getPiece()?.setKilled(true)     // kill that piece
        }
        end.setPiece(piece)                     // move the piece to the arrival spot
        start.setPiece(null)                    // delete the piece at the starting point

    }

    // return the box if it is inside the board
    fun getBox(x: Int, y: Int): Spot {
        if (x !in 0..7 || y !in 0..7) {
            throw Exception("Index out of bound")
        }
        return boxes[x][y] ?: throw Exception("Box is null")
    }

    // reset the pieces on the board
    fun resetBoard() {
        // initialize white pieces
        boxes[0][0] = Spot(0, 0, Rook(false))
        boxes[0][1] = Spot(0, 1, Knight(false))
        boxes[0][2] = Spot(0, 2, Bishop(false))
        boxes[0][3] = Spot(0, 3, Queen(false))
        boxes[0][4] = Spot(0, 4, King(false))
        boxes[0][5] = Spot(0, 5, Bishop(false))
        boxes[0][6] = Spot(0, 6, Knight(false))
        boxes[0][7] = Spot(0, 7, Rook(false))
        boxes[1][0] = Spot(1, 0, Pawn(false))
        boxes[1][1] = Spot(1, 1, Pawn(false))
        boxes[1][2] = Spot(1, 2, Pawn(false))
        boxes[1][3] = Spot(1, 3, Pawn(false))
        boxes[1][4] = Spot(1, 4, Pawn(false))
        boxes[1][5] = Spot(1, 5, Pawn(false))
        boxes[1][6] = Spot(1, 6, Pawn(false))
        boxes[1][7] = Spot(1, 7, Pawn(false))
        //...

        // initialize black pieces
        boxes[7][0] = Spot(7, 0, Rook(true))
        boxes[7][1] = Spot(7, 1, Knight(true))
        boxes[7][2] = Spot(7, 2, Bishop(true))
        boxes[7][3] = Spot(7, 3, Queen(true))
        boxes[7][4] = Spot(7, 4, King(true))
        boxes[7][5] = Spot(7, 5, Bishop(true))
        boxes[7][6] = Spot(7, 6, Knight(true))
        boxes[7][7] = Spot(7, 7, Rook(true))
        boxes[6][0] = Spot(6, 0, Pawn(true))
        boxes[6][1] = Spot(6, 1, Pawn(true))
        boxes[6][2] = Spot(6, 2, Pawn(true))
        boxes[6][3] = Spot(6, 3, Pawn(true))
        boxes[6][4] = Spot(6, 4, Pawn(true))
        boxes[6][5] = Spot(6, 5, Pawn(true))
        boxes[6][6] = Spot(6, 6, Pawn(true))
        boxes[6][7] = Spot(6, 7, Pawn(true))
        //...

        // initialize remaining boxes without any piece
        for (i in 2..5) {
            for (j in 0..7) {
                boxes[i][j] = Spot(i, j, null)
            }
        }

        // remove this

        boxes[2][5] = Spot(2,5, Pawn(true))
    }
}
