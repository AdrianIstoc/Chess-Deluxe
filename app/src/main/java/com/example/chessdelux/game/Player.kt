package com.example.chessdelux.game

import android.util.Log
import com.example.chessdelux.board.Board
import com.example.chessdelux.board.Spot
import com.example.chessdelux.pieces.King
import com.example.chessdelux.pieces.Pawn
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random
import kotlin.time.measureTime

abstract class Player(private val whiteSide: Boolean, private val humanPlayer: Boolean) {
    fun isWhiteSide(): Boolean {    // return the player color
        return whiteSide
    }

    fun isHumanPlayer(): Boolean {  // return if player is a human
        return humanPlayer
    }
}

// human
class HumanPlayer(whiteSide: Boolean) : Player(whiteSide, true)

// computer
class ComputerPlayer(whiteSide: Boolean) : Player(whiteSide, false){
    private var difficulty: Int = 1

    fun setDifficulty(dif: Int){
        difficulty = dif
    }

    fun getDifficulty(): Int{
        return difficulty
    }

    fun minimax(game: Game, board: Board, depth: Int, alpha: Int, beta: Int, maximizingPlayer: Boolean, maximizingColor: Boolean, value: Int = Int.MIN_VALUE): Pair<Pair<Spot, Spot>?, Int> {
        var valueN = value
        if(valueN == Int.MIN_VALUE)
            valueN = board.evaluate(maximizingColor)


        if (depth == 0) {
            return Pair(null, valueN)
        }
        var moves: MutableList<Pair<Spot, Spot>> = emptyList<Pair<Spot, Spot>>().toMutableList()
        moves = board.getMoves(maximizingPlayer, board)

        var bestMove: Pair<Spot, Spot>? = null
        if (moves.isNotEmpty() == true) {
            bestMove = moves[Random.nextInt(moves.size)]
        }

        var alphaVar = alpha
        var betaVar = beta

        if (maximizingPlayer) {
            var maxEval = Int.MIN_VALUE
            try {
                for (move in moves) {
                    Log.i("ObscureMove", "depth: $depth")
                    Log.i("ObscureMove", "minimax white problem move -> first: ${move.first.getPiece()?.getType()} ${move.first.getX()},${move.first.getY()}, second: ${move.second.getX()},${move.second.getY()}")
                    val start = move.first
                    val startPiece = start.getPiece()
                    var moved : Boolean = false
                    if (start.getPiece() is Pawn) {
                        moved = (start.getPiece() as Pawn).isPawnMoved()
                        (start.getPiece() as Pawn).setPawnMoved(true)
                    }
                    val end = move.second
                    val endPiece = end.getPiece()
                    board.movePiece(move.first, move.second)
                    val king = board.getKingSpot(false)
                    if((king.getPiece() as King).checkIfKingInCheck(board, king))
                        valueN+=45
                    if(endPiece != null)
                        valueN += endPiece.getValue()
                    val currentEval =
                        minimax(game, board, depth - 1, alphaVar, betaVar, false, maximizingColor, valueN).second
                    if((king.getPiece() as King).checkIfKingInCheck(board, king))
                        valueN-=45
                    start.setPiece(startPiece)
                    if (start.getPiece() is Pawn)
                        (start.getPiece() as Pawn).setPawnMoved(moved)
                    end.setPiece(endPiece)
                    end.getPiece()?.setKilled(false)
                    if(endPiece != null)
                        valueN -= endPiece.getValue()
                    if (currentEval > maxEval) {
                        maxEval = currentEval
                        bestMove = move
                    }
                    alphaVar = max(alphaVar, currentEval)
                    if (betaVar <= alphaVar)
                        break
                }
            }catch (e: Exception){
                Log.e("ObscureMove", "minimax white problem e -> ${e.message}")
            }
            return Pair(bestMove, maxEval)
        } else {
            var minEval = Int.MAX_VALUE
            try {
                for (move in moves) {
                    Log.i("ObscureMove", "depth: $depth")
                    Log.i("ObscureMove", "minimax black problem move -> first: ${move.first.getPiece()?.getType()} ${move.first.getX()},${move.first.getY()}, second: ${move.second.getX()},${move.second.getY()}")
                    val start = move.first
                    val startPiece = start.getPiece()
                    var moved : Boolean = false
                    if (start.getPiece() is Pawn) {
                        moved = (start.getPiece() as Pawn).isPawnMoved()
                        (start.getPiece() as Pawn).setPawnMoved(true)
                    }
                    val end = move.second
                    val endPiece = end.getPiece()
                    board.movePiece(move.first, move.second)
                    val king = board.getKingSpot(false)
                    if((king.getPiece() as King).checkIfKingInCheck(board, king))
                        valueN-=45
                    if(endPiece != null)
                        valueN -= endPiece.getValue()
                    val currentEval =
                        minimax(game, board, depth - 1, alphaVar, betaVar, true, maximizingColor, valueN).second
                    if((king.getPiece() as King).checkIfKingInCheck(board, king))
                        valueN+=45
                    start.setPiece(startPiece)
                    if (start.getPiece() is Pawn)
                        (start.getPiece() as Pawn).setPawnMoved(moved)
                    end.setPiece(endPiece)
                    end.getPiece()?.setKilled(false)
                    if(endPiece != null)
                        valueN += endPiece.getValue()
                    if (currentEval < minEval) {
                        minEval = currentEval
                        bestMove = move
                    }
                    betaVar = min(betaVar, currentEval)
                    if (betaVar <= alphaVar)
                        break
                }
            }catch (e: Exception){
                Log.e("ObscureMove", "minimax black problem e -> ${e.message}")
            }
            return Pair(bestMove, minEval)
        }
    }

}