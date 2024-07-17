package com.example.chessdelux.game

import android.widget.GridLayout
import android.widget.ImageView
import com.example.chessdelux.MainActivity
import com.example.chessdelux.R
import com.example.chessdelux.pieces.PieceType

fun createPopUp(grid: GridLayout, popupList: List<PieceType>, white: Boolean, context: MainActivity, cellSize: Int){
    val matrix = listToMatrix(popupList, 7)

    grid.removeAllViews()

    for (i in matrix.indices)
        for (j in 0 until matrix[i].size){
            if(matrix[i][j] != null) {
                val cell = ImageView(context)
                val img = getImgResource(matrix[i][j] as PieceType, white)
                cell.setImageResource(img)

                val params = GridLayout.LayoutParams()
                params.rowSpec = GridLayout.spec(i,1)
                params.columnSpec = GridLayout.spec(j,1)
                params.width = cellSize
                params.height = cellSize
                params.setMargins(1,1,1,1)
                grid.addView(cell, params)
            }
        }
}

fun listToMatrix(list: List<Any>, columns: Int): List<List<Any?>> {
    val paddedList = if(list.size % columns == 0) list else list + List(columns - list.size % columns) { null }

    return paddedList.windowed(columns, columns)
}

fun getImgResource(type: PieceType, white: Boolean): Int {
    return when(type){
        PieceType.ROOK -> if (white) R.drawable.rook_white else R.drawable.rook_black
        PieceType.QUEEN -> if (white) R.drawable.queen_white else R.drawable.queen_black
        PieceType.KNIGHT -> if (white) R.drawable.knight_white else R.drawable.knight_black
        PieceType.BISHOP -> if (white) R.drawable.bishop_white else R.drawable.bishop_black
        PieceType.THIEF -> if (white) R.drawable.thief_white else R.drawable.thief_black
        PieceType.ASSASSIN -> if (white) R.drawable.assassin_white else R.drawable.assassin_black
        PieceType.CARDINAL -> if (white) R.drawable.cardinal_white else R.drawable.cardinal_black
        else -> R.drawable.chess_delux_icon
    }
}