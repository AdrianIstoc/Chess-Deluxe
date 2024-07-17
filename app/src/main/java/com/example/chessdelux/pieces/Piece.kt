package com.example.chessdelux.pieces

import android.app.AlertDialog
import android.util.Log
import android.widget.GridLayout
import com.example.chessdelux.MainActivity
import com.example.chessdelux.R
import com.example.chessdelux.board.*
import com.example.chessdelux.game.createPopUp
import com.example.chessdelux.game.makePiece
import com.example.chessdelux.game.renderPieces

abstract class Piece(private var white: Boolean, private var type: PieceType, private var value: Int, private val expCap: Int) {
    private var killed: Boolean = false     // true if the piece was killed
    open var imageResource: Int? = null     // the piece image
    private var selected: Boolean = false   // true if the piece is selected
    private var exp: Int = 0

    abstract val evolutionOptions: List<PieceType>

    fun readyToEvolve():Boolean{
        return exp >= expCap
    }

    fun addExp(xp: Int){
        exp+=xp
    }

    fun getValue(): Int{
        return value
    }

    fun getExp(): Int{
        return exp
    }

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

    fun checkIfPieceEvolves(spot: Spot, context: MainActivity, cellSize: Int, board: Board, chessboard: GridLayout, onEvolutionSelected: () -> Unit){
        val builder = AlertDialog.Builder(context)
        val popUpView = context.createPopUpView()
        builder.setView(popUpView)

        val evolutions = this.evolutionOptions
        val grid = popUpView.findViewById<GridLayout>(R.id.popup_grid)
        grid?.let { createPopUp(it, evolutions, this.isWhite(), context, cellSize)}

        val dialog = builder.create()
        try{
            dialog.show()
        }catch (e: Exception){
            Log.e("ObscureMove", "checkIfPieceEvolves problem e -> ${e.message}")
        }

        for (i in 0 until grid!!.rowCount)
            for (j in 0 until grid.columnCount) {
                try {
                    val child = grid.getChildAt(i * grid.columnCount + j)
                    child.setOnClickListener {
                        val pieceType = evolutions[i * grid.columnCount + j]
                        val piece = makePiece(pieceType, this.isWhite())
                        board.getBox(spot.getX(), spot.getY()).setPiece(piece)
                        renderPieces(chessboard, board)
                        dialog.dismiss()
                        onEvolutionSelected()
                    }
                }catch (e: Exception){
                    Log.e("ObscureMovement", "setOnClickListener (checkIfPieceEvolves) problem e -> ${e.message}")
                }
            }
    }


    // return the piece possible moves
    abstract fun moveOptions(board: Board, start: Spot): MutableList<Spot>

    // return the piece kill options
    abstract fun killOptions(board: Board, start: Spot): MutableList<Spot>
}
