package com.example.chessdelux.pieces

import android.app.AlertDialog
import android.util.Log
import android.widget.GridLayout
import com.example.chessdelux.MainActivity
import com.example.chessdelux.board.*
import com.example.chessdelux.R
import com.example.chessdelux.game.*
import kotlin.math.abs

class Pawn(white: Boolean) : Piece(white, PieceType.PAWN, 1, 10) {
    // the pawn image
    override var imageResource: Int? = if (white) R.drawable.pawn_white else R.drawable.pawn_black

    // list of promote options
    private val promoteOptions = listOf(PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN)

    override val evolutionOptions = listOf(PieceType.ASSASSIN, PieceType.THIEF)

    // pawn moved at least once
    private var pawnMoved = false

    // pawn had moved two spaces last turn
    private var pawnSkipped = false

    // returns the list of promote options
    private fun getPromoteOptions(): List<PieceType> {
        return promoteOptions
    }


    // set if pawn moved two spaces last turn
    fun setPawnSkipped(skipped: Boolean){
        pawnSkipped = skipped
    }

    // set if pawn moved at least once
    fun setPawnMoved(moved: Boolean) {
        pawnMoved = moved
    }

    fun isPawnMoved():Boolean{
        return pawnMoved
    }

    // return the pawn possible moves
    override fun moveOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        // set the direction of the pawn based on the color
        val direction = if (isWhite()) -1 else 1

        // check if the pawn can move one space forward
        if(start.getX()+direction in 0 .. 7) {
            val spot = board.getBox(start.getX() + direction, start.getY())
            if (spot.getPiece() == null)
                options.add(spot)
        }

        // check if the pawn can move two spaces forward if it hasn't moved yet
        if(!pawnMoved && board.getBox(start.getX() + 2 * direction, start.getY()).getPiece() == null && board.getBox(start.getX()+direction, start.getY()).getPiece() == null)
            options.add(board.getBox(start.getX() + 2 * direction, start.getY()))

        return options
    }

    // return the pawn kill options
    override fun killOptions(board: Board, start: Spot): MutableList<Spot> {
        val options = mutableListOf<Spot>()
        // set the direction of the pawn based on the color
        val direction = if (isWhite()) -1 else 1
        val x = start.getX()
        val y = start.getY()
        var spot: Spot?

        // check if the pawn can capture a piece in the right
        if(start.getX()+direction in 0 .. 7 && start.getY()+1 in 0 .. 7) {
            spot = board.getBox(start.getX() + direction, start.getY() + 1)
            if (spot.getPiece() != null &&
                spot.getPiece()?.isWhite() == !isWhite() &&
                spot.getPiece() !is Fortress
            )
                options.add(spot)
        }

        // check if the pawn can capture a piece in the left
        if(start.getX()+direction in 0 .. 7 && start.getY()-1 in 0 .. 7) {
            spot = board.getBox(start.getX() + direction, start.getY() - 1)
            if (spot.getPiece() != null &&
                spot.getPiece()?.isWhite() == !isWhite() &&
                spot.getPiece() !is Fortress
            )
                options.add(spot)
        }


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

    // promote the pawn
    fun checkIfPawnPromoting(end: Spot, context: MainActivity, cellSize: Int, board: Board, chessboard: GridLayout){
        val pawn = end.getPiece() as Pawn
        if((end.getX() == 0 && pawn.isWhite()) || (end.getX() == 7 && !pawn.isWhite())){
            val builder = AlertDialog.Builder(context)
            val popUpView = context.createPopUpView()
            builder.setView(popUpView)
            builder.setCancelable(false)

            val promotions = pawn.getPromoteOptions()
            val grid = popUpView.findViewById<GridLayout>(R.id.popup_grid)
            grid?.let { createPopUp(it, promotions, pawn.isWhite(), context, cellSize) }

            val dialog = builder.create()
            try{
                dialog.show()
            }catch (e: Exception){
                Log.e("ObscureMove", "checkIfPawnPromoting problem e -> ${e.message}")
            }

            for(i in 0 until grid!!.rowCount)
                for(j in 0 until grid.columnCount) {
                    try{
                        val child = grid.getChildAt(i * grid.columnCount + j)
                        child.setOnClickListener {
                            val pieceType = promotions[i * grid.columnCount + j]
                            val piece = makePiece(pieceType, pawn.isWhite())
                            board.getBox(end.getX(), end.getY()).setPiece(piece)
                            // render pieces after any move for a better preview when a piece changes types
                            renderPieces(chessboard, board)
                            dialog.dismiss()
                        }
                    }catch (e: Exception){
                        Log.e("ObscureMove", "setOnClickListener (checkIfPawnPromoting) problem e -> ${e.message}")
                    }
                }
        }
    }


    // check if the pawn is doing an en passant
    fun checkIfEnPassant(start: Spot, end: Spot, board: Board){
        // check if a white pawn is going to the left
        if(start.getPiece()?.isWhite() == true && start.getX() == 3 ){
            if(start.getY()-1 in 0 .. 7 && board.getBox(start.getX(), start.getY()-1).getPiece() is Pawn)
                if((board.getBox(start.getX(), start.getY()-1).getPiece() as Pawn).pawnSkipped)
                    if(end.getX()==start.getX()-1 && end.getY() == start.getY()-1) {
                        board.getBox(start.getX(), start.getY() - 1).getPiece()?.setKilled(true)
                        board.getBox(start.getX(), start.getY() - 1).setPiece(null)
                        return
                    }
        }
        // check if a black pawn is going to the left
        else if(start.getPiece()?.isWhite() == false && start.getX() == 4)
            if(start.getY()-1 in 0 .. 7 && board.getBox(start.getX(), start.getY()-1).getPiece() is Pawn)
                if((board.getBox(start.getX(), start.getY()-1).getPiece() as Pawn).pawnSkipped)
                    if(end.getX() == start.getX()+1 && end.getY() == start.getY()-1){
                        board.getBox(start.getX(), start.getY()-1).getPiece()?.setKilled(true)
                        board.getBox(start.getX(), start.getY() - 1).setPiece(null)
                        return
                    }
        // check if a white pawn is going to the right
        if(start.getPiece()?.isWhite() == true && start.getX() == 3){
            if(start.getY()+1 in 0 .. 7 && board.getBox(start.getX(), start.getY()+1).getPiece() is Pawn)
                if((board.getBox(start.getX(), start.getY()+1).getPiece() as Pawn).pawnSkipped)
                    if(end.getX() == start.getX()-1 && end.getY() == start.getY()+1){
                        board.getBox(start.getX(), start.getY()+1).getPiece()?.setKilled(true)
                        board.getBox(start.getX(), start.getY() + 1).setPiece(null)
                        return
                    }
        }
        // check if a black pawn is going to the right
        else if(start.getPiece()?.isWhite() == false && start.getX() == 4)
            if(start.getY()+1 in 0 .. 7 && board.getBox(start.getX(), start.getY()+1).getPiece() is Pawn)
                if((board.getBox(start.getX(), start.getY()+1).getPiece() as Pawn).pawnSkipped)
                    if(end.getX() == start.getX()+1 && end.getY() == start.getY()+1){
                        board.getBox(start.getX(), start.getY()+1).getPiece()?.setKilled(true)
                        board.getBox(start.getX(), start.getY() + 1).setPiece(null)
                        return
                    }

    }


    // check if the piece is a pawn that moved two spaces
    fun checkPawnSkipped(start: Spot, end: Spot, board: Board, white: Boolean){
        //check if the pawn moved two spaces and stets the pawn as "skipped"
        if (abs(start.getX() - end.getX()) == 2 && start.getPiece() is Pawn)
            (start.getPiece() as Pawn).setPawnSkipped(true)
    }
}