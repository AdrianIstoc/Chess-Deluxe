package com.example.chessdelux.game

import android.util.Log
import com.example.chessdelux.board.Board
import com.example.chessdelux.board.Spot
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

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
    fun minimax(game: Game, board: Board, depth: Int, alpha: Int, beta: Int, maximizingPlayer: Boolean, maximizingColor: Boolean, value: Int = Int.MIN_VALUE): Pair<Pair<Spot, Spot>?, Int> {
        var valueN = value
        if(valueN == Int.MIN_VALUE)
            valueN = board.evaluate(maximizingColor)


        if (depth == 0) {
            return Pair(null, valueN)
        }
        val moves = board.getMoves(maximizingPlayer, board)
        var bestMove: Pair<Spot, Spot>? = null
        if (moves.isNotEmpty()) {
            bestMove = moves[Random.nextInt(moves.size)]
        }

        var alphaVar = alpha
        var betaVar = beta

        if (maximizingPlayer) {
            var maxEval = Int.MIN_VALUE
            try {
                for (move in moves) {
                    val start = move.first
                    val startPiece = start.getPiece()
                    val end = move.second
                    val endPiece = end.getPiece()
                    board.movePiece(move.first, move.second)
                    if(endPiece != null)
                        valueN += endPiece.getValue()
                    val currentEval =
                        minimax(game, board, depth - 1, alpha, beta, false, maximizingColor, valueN).second
                    start.setPiece(startPiece)
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
                    val start = move.first
                    val startPiece = start.getPiece()
                    val end = move.second
                    val endPiece = end.getPiece()
                    board.movePiece(move.first, move.second)
                    if(endPiece != null)
                        valueN -= endPiece.getValue()
                    val currentEval =
                        minimax(game, board, depth - 1, alpha, beta, true, maximizingColor, valueN).second
                    start.setPiece(startPiece)
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