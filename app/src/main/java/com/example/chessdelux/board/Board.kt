package com.example.chessdelux.board

import com.example.chessdelux.game.validKills
import com.example.chessdelux.game.validMoves
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

    fun getFortressSpots(white: Boolean): MutableList<Spot> {
        val fortressSpot = mutableListOf<Spot>()        // set the spot of the king as null
        for (i in 0 until 8){
            for (j in 0 until 8){
                val pieceSpot = this.getBox(i, j)
                val piece = pieceSpot.getPiece()
                if(piece is Fortress && piece.isWhite() == white){
                    fortressSpot.add(pieceSpot)
                }
            }
        }
        return fortressSpot
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
    }

    fun testWin(){
        for (i in 0 until 8)
            for (j in 0 until 8)
                boxes[i][j] = Spot(i, j, null)

        boxes[7][0] = Spot(7, 0, King(false))
        boxes[7][7] = Spot(7,7,King(true))

        boxes[3][4] = Spot(3,4, Assassin(true))

        boxes[1][3] = Spot(1,3, Rook(false))
        boxes[1][4] = Spot(1,4, Rook(false))
        boxes[1][5] = Spot(1,5, Rook(false))
        boxes[2][2] = Spot(2,2, Rook(false))
        boxes[2][4] = Spot(2,4, Rook(false))
        boxes[2][6] = Spot(2,6, Rook(false))
        boxes[3][2] = Spot(3,2, Rook(false))
        boxes[3][5] = Spot(3,5, Rook(false))
        boxes[3][6] = Spot(3,6, Rook(false))
        boxes[4][2] = Spot(4,2, Rook(false))
        boxes[4][6] = Spot(4,6, Rook(false))
        boxes[5][3] = Spot(5,3, Rook(false))
        boxes[5][4] = Spot(5,4, Rook(false))
        boxes[5][5] = Spot(5,5, Rook(false))
    }

    fun pawnTutorial(){
        for (i in 0 until 8)
            for (j in 0 until 8)
                boxes[i][j] = Spot(i, j, null)

        boxes[7][0] = Spot(7, 0, King(false))
        boxes[7][7] = Spot(7,7, King(true))

        boxes[1][1] = Spot(1,1, Pawn(true))
        boxes[6][2] = Spot(6,2, Pawn(true))
        boxes[6][5] = Spot(6,5, Pawn(true))

        boxes[3][1] = Spot(3,1, Knight(false))
        boxes[3][3] = Spot(3,3, Knight(false))

        boxes[5][4] = Spot(5,4, Rook(false))
        boxes[5][5] = Spot(5,5, Rook(false))
        boxes[5][6] = Spot(5,6, Rook(false))
        boxes[6][4] = Spot(6,4, Rook(false))
        boxes[6][6] = Spot(6,6, Rook(false))
        boxes[7][4] = Spot(7,4, Bishop(false))
        boxes[7][5] = Spot(7,5, Rook(false))
        boxes[7][6] = Spot(7,6, Bishop(false))
    }

    fun knightTutorial(){
        for (i in 0 until 8)
            for (j in 0 until 8)
                boxes[i][j] = Spot(i, j, null)

        boxes[7][0] = Spot(7, 0, King(false))
        boxes[7][7] = Spot(7,7, King(true))

        boxes[3][4] = Spot(3,4, Knight(true))

        boxes[1][3] = Spot(1,3, Rook(false))
        boxes[1][5] = Spot(1,5, Rook(false))
        boxes[2][2] = Spot(2,2, Rook(false))
        boxes[2][6] = Spot(2,6, Rook(false))
        boxes[4][2] = Spot(4,2, Rook(false))
        boxes[4][6] = Spot(4,6, Rook(false))
        boxes[5][3] = Spot(5,3, Rook(false))
        boxes[5][5] = Spot(5,5, Rook(false))

        boxes[2][3] = Spot(2,3, Pawn(false))
        boxes[2][4] = Spot(2,4, Pawn(false))
        boxes[2][5] = Spot(2,5, Pawn(false))
        boxes[3][3] = Spot(3,3, Pawn(false))
        boxes[3][5] = Spot(3,5, Pawn(false))
        boxes[4][3] = Spot(4,3, Pawn(false))
        boxes[4][4] = Spot(4,4, Pawn(false))
        boxes[4][5] = Spot(4,5, Pawn(false))
    }

    fun bishopTutorial(){
        for (i in 0 until 8)
            for (j in 0 until 8)
                boxes[i][j] = Spot(i, j, null)

        boxes[7][0] = Spot(7, 0, King(false))
        boxes[7][7] = Spot(7,7, King(true))

        boxes[3][4] = Spot(3,4, Bishop(true))

        boxes[0][1] = Spot(0,1, Rook(false))
        boxes[1][6] = Spot(1,6, Rook(false))
        boxes[4][5] = Spot(4,5, Rook(false))
    }

    fun rookTutorial(){
        for (i in 0 until 8)
            for (j in 0 until 8)
                boxes[i][j] = Spot(i, j, null)

        boxes[7][0] = Spot(7, 0, King(false))
        boxes[7][7] = Spot(7,7, King(true))

        boxes[3][4] = Spot(3,4, Rook(true))

        boxes[2][4] = Spot(2,4, Knight(false))
        boxes[3][1] = Spot(3,1, Knight(false))
        boxes[3][6] = Spot(3,6, Knight(false))
        boxes[7][4] = Spot(7,4, Knight(false))
    }


    fun queenTutorial(){
        for (i in 0 until 8)
            for (j in 0 until 8)
                boxes[i][j] = Spot(i, j, null)

        boxes[7][0] = Spot(7, 0, King(false))
        boxes[7][7] = Spot(7,7, King(true))

        boxes[4][5] = Spot(4,5, Queen(true))

        boxes[0][1] = Spot(0,1, Pawn(false))
        boxes[1][5] = Spot(1,5, Pawn(false))
        boxes[2][7] = Spot(2,7, Pawn(false))
        boxes[4][0] = Spot(4,0, Pawn(false))
        boxes[4][6] = Spot(4,6, Pawn(false))
    }

    fun kingTutorial(){
        for (i in 0 until 8)
            for (j in 0 until 8)
                boxes[i][j] = Spot(i, j, null)

        boxes[0][6] = Spot(0, 6, King(false))
        boxes[7][4] = Spot(7,4, King(true))

        boxes[7][0] = Spot(7,0, Rook(true))
        boxes[7][7] = Spot(7,7, Rook(true))

        boxes[0][1] = Spot(0,1, Rook(false))
        boxes[0][5] = Spot(0,5, Rook(false))
    }

    fun fortressTutorial() {
        for (i in 0 until 8)
            for (j in 0 until 8)
                boxes[i][j] = Spot(i, j, null)

        boxes[0][0] = Spot(0, 0, King(false))
        boxes[7][7] = Spot(7,7, King(true))

        boxes[2][1] = Spot(2,1, Rook(true))
        boxes[2][3] = Spot(2,3, Rook(true))
        boxes[2][5] = Spot(2,5, Rook(true))
        boxes[5][2] = Spot(5,2, Fortress(true))
        boxes[5][4] = Spot(5,4, Fortress(true))
        boxes[6][3] = Spot(6,3, Fortress(true))

        boxes[5][5] = Spot(5,5, Knight(false))
        boxes[6][6] = Spot(6,6, Queen(false))
        boxes[6][7] = Spot(6,7, Rook(false))
        boxes[7][6] = Spot(7,6, Rook(false))

    }

    fun thiefTutorial() {
        for (i in 0 until 8)
            for (j in 0 until 8)
                boxes[i][j] = Spot(i, j, null)

        boxes[7][0] = Spot(7, 0, King(false))
        boxes[7][7] = Spot(7,7, King(true))

        boxes[1][5] = Spot(1,5, Thief(true))
        boxes[5][4] = Spot(5,4, Thief(true))
        boxes[7][1] = Spot(7,1, Pawn(true))

        boxes[0][0] = Spot(0,0, Queen(false))
        boxes[1][1] = Spot(1,1, Queen(false))
        boxes[2][0] = Spot(2,0, Queen(false))
        boxes[3][1] = Spot(3,1, Queen(false))
        boxes[4][0] = Spot(4,0, Queen(false))
        boxes[5][1] = Spot(5,1, Queen(false))
        boxes[6][0] = Spot(6,0, Queen(false))

        boxes[0][4] = Spot(0,4, Knight(false))
        boxes[0][6] = Spot(0,6, Knight(false))
        boxes[2][4] = Spot(2,4, Knight(false))
        boxes[2][6] = Spot(2,6, Knight(false))

        boxes[4][4] = Spot(4,4, Pawn(false))
        boxes[5][5] = Spot(5,5, Pawn(false))
        boxes[5][3] = Spot(5,3, Pawn(false))
        boxes[6][4] = Spot(6,4, Pawn(false))

    }

    fun assassinTutorial() {
        for (i in 0 until 8)
            for (j in 0 until 8)
                boxes[i][j] = Spot(i, j, null)

        boxes[7][0] = Spot(7, 0, King(false))
        boxes[7][7] = Spot(7,7,King(true))

        boxes[3][4] = Spot(3,4, Assassin(true))

        boxes[1][3] = Spot(1,3, Rook(false))
        boxes[1][4] = Spot(1,4, Rook(false))
        boxes[1][5] = Spot(1,5, Rook(false))
        boxes[2][2] = Spot(2,2, Rook(false))
        boxes[2][4] = Spot(2,4, Rook(false))
        boxes[2][6] = Spot(2,6, Rook(false))
        boxes[3][2] = Spot(3,2, Rook(false))
        boxes[3][5] = Spot(3,5, Rook(false))
        boxes[3][6] = Spot(3,6, Rook(false))
        boxes[4][2] = Spot(4,2, Rook(false))
        boxes[4][6] = Spot(4,6, Rook(false))
        boxes[5][3] = Spot(5,3, Rook(false))
        boxes[5][4] = Spot(5,4, Rook(false))
        boxes[5][5] = Spot(5,5, Rook(false))
    }

    fun cardinalTutorial() {
        for (i in 0 until 8)
            for (j in 0 until 8)
                boxes[i][j] = Spot(i, j, null)

        boxes[7][0] = Spot(7, 0, King(false))
        boxes[7][7] = Spot(7,7, King(true))

        boxes[3][5] = Spot(3,5, Cardinal(true))

        boxes[0][4] = Spot(0,4, Rook(false))
        boxes[1][1] = Spot(1,1, Rook(false))
        boxes[1][6] = Spot(1,6, Rook(false))
        boxes[3][6] = Spot(3,6, Rook(false))
        boxes[5][0] = Spot(5,0, Rook(false))
        boxes[7][3] = Spot(7,3, Bishop(false))
    }

    fun paladinTutorial() {
        for (i in 0 until 8)
            for (j in 0 until 8)
                boxes[i][j] = Spot(i, j, null)

        boxes[7][0] = Spot(7, 0, King(false))
        boxes[7][7] = Spot(7,7, King(true))

        boxes[4][4] = Spot(4,4, Paladin(true))

        boxes[2][4] = Spot(2,4, Rook(false))
        boxes[4][3] = Spot(4,3, Pawn(false))
        boxes[4][6] = Spot(4,6, Rook(false))
        boxes[6][4] = Spot(6,4, Pawn(false))
    }

    fun evaluate(maximizingColor: Boolean): Int {
        var sum = 0
        if (!maximizingColor) {
            for (i in 0 until 8)
                for (j in 0 until 8) {
                    val piece = getBox(i, j).getPiece()
                    if(piece!= null && piece.isWhite())
                        sum+=piece.getValue()
                    else if(piece!= null && !piece.isWhite())
                        sum-=piece.getValue()
                }
        }
        else {
            for (i in 0 until 8)
                for (j in 0 until 8) {
                    val piece = getBox(i, j).getPiece()
                    if(piece!= null && piece.isWhite())
                        sum-=piece.getValue()
                    else if(piece!= null && !piece.isWhite())
                        sum+=piece.getValue()
                }
        }

        return sum
    }

    fun getMoves(white: Boolean, board: Board): MutableList<Pair<Spot, Spot>> {
        val moves = mutableListOf<Pair<Spot, Spot>>() // Initialize the list

        for (i in 0 until 8) {
            for (j in 0 until 8) {
                val pieceSpot = getBox(i, j)
                val piece = pieceSpot.getPiece()
                if (piece != null && piece.isWhite() == white) {
                    val possibleMove = piece.moveOptions(board,  pieceSpot)
                    val possibleKill = piece.killOptions(board,  pieceSpot)
                    val options = validMoves(possibleMove,  pieceSpot, board, white) + validKills(possibleKill,  pieceSpot, board, white)
                    for (option in options)
                        moves.add(Pair(pieceSpot, option))
                }
            }
        }

        return moves // Return the list of moves
    }

}
