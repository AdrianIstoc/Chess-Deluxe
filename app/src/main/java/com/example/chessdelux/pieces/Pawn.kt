package com.example.chessdelux.pieces

import com.example.chessdelux.board.*
import com.example.chessdelux.R

class Pawn(white: Boolean) : Piece(white, PieceType.PAWN) {
    // the pawn image
    override var imageResource: Int? = if (white) R.drawable.pawn_white else R.drawable.pawn_black

    // pawn moved at least once
    private var pawnMoved = false

    // pawn had moved two spaces last turn
    var pawnSkipped = false
        private set

    // set if pawn moved two spaces last turn
    fun setPawnSkipped(skipped: Boolean){
        pawnSkipped = skipped
    }

    // set if pawn moved at least once
    fun setPawnMoved() {
        pawnMoved = true
    }

    // return the pawn possible moves
    override fun moveOptions(board: Board, start: Spot): List<Spot> {
        val options = mutableListOf<Spot>()
        // set the direction of the pawn based on the color
        val direction = if (isWhite()) -1 else 1

        // check if the pawn can move one space forward
        if(start.getX()+direction in 0 .. 7 && board.getBox(start.getX() + direction, start.getY()).getPiece() == null)
            options.add(board.getBox(start.getX() + direction, start.getY()))

        // check if the pawn can move two spaces forward if it hasn't moved yet
        if(!pawnMoved && board.getBox(start.getX() + 2 * direction, start.getY()).getPiece() == null && board.getBox(start.getX()+direction, start.getY()).getPiece() == null)
            options.add(board.getBox(start.getX() + 2 * direction, start.getY()))



        return options
    }

    // return the pawn kill options
    override fun killOptions(board: Board, start: Spot): List<Spot> {
        val options = mutableListOf<Spot>()
        // set the direction of the pawn based on the color
        val direction = if (isWhite()) -1 else 1

        // check if the pawn can capture a piece in the right
        if(start.getX()+direction in 0 .. 7 && start.getY()+1 in 0 .. 7 && board.getBox(start.getX() +direction, start.getY()+1).getPiece() != null && board.getBox(start.getX() +direction, start.getY()+1).getPiece()?.isWhite() != isWhite())
            options.add(board.getBox(start.getX() +direction, start.getY()+1))

        // check if the pawn can capture a piece in the left
        if(start.getX()+direction in 0 .. 7 && start.getY()-1 in 0 .. 7 && board.getBox(start.getX() +direction, start.getY()-1).getPiece() != null && board.getBox(start.getX() +direction, start.getY()-1).getPiece()?.isWhite() != isWhite())
            options.add(board.getBox(start.getX() +direction, start.getY()-1))


        // check if a white pawn can en passant to the left
        if(start.getPiece()?.isWhite() == true && start.getX() == 3 ){
            if(start.getY()-1 in 0 .. 7 && board.getBox(start.getX(), start.getY()-1).getPiece() is Pawn)
                if((board.getBox(start.getX(), start.getY()-1).getPiece() as Pawn).pawnSkipped)
                    options.add(board.getBox(start.getX()+direction, start.getY()-1))
        }
        // check if a black pawn can en passant to the left
        else if(start.getPiece()?.isWhite() == false && start.getX() == 4)
            if(start.getY()-1 in 0 .. 7 && board.getBox(start.getX(), start.getY()-1).getPiece() is Pawn)
                if((board.getBox(start.getX(), start.getY()-1).getPiece() as Pawn).pawnSkipped)
                    options.add(board.getBox(start.getX()+direction, start.getY()-1))

        // check if a white pawn can en passant to the right
        if(start.getPiece()?.isWhite() == true && start.getX() == 3){
            if(start.getY()+1 in 0 .. 7 && board.getBox(start.getX(), start.getY()+1).getPiece() is Pawn)
                if((board.getBox(start.getX(), start.getY()+1).getPiece() as Pawn).pawnSkipped)
                    options.add(board.getBox(start.getX()+direction, start.getY()+1))
        }
        // check if a black pawn can en passant to the right
        else if(start.getPiece()?.isWhite() == false && start.getX() == 4)
            if(start.getY()+1 in 0 .. 7 && board.getBox(start.getX(), start.getY()+1).getPiece() is Pawn)
                if((board.getBox(start.getX(), start.getY()+1).getPiece() as Pawn).pawnSkipped)
                    options.add(board.getBox(start.getX()+direction, start.getY()+1))

        return options
    }
}