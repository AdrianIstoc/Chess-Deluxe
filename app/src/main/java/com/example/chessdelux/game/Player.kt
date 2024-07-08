package com.example.chessdelux.game

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
class ComputerPlayer(whiteSide: Boolean) : Player(whiteSide, false)