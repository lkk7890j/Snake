package com.example.snake

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.fixedRateTimer
import kotlin.random.Random

class SnakeViewModel : ViewModel() {

    val body = MutableLiveData<List<Position>>()
    val apple = MutableLiveData<Position>()
    val score = MutableLiveData<Int>()
    val gameState = MutableLiveData<GameState>()
    private val snakeBody = mutableListOf<Position>()
    private var direction = Direction.LEFT

    private var applePosition: Position? = null
    private  var myPoint = 0

    fun start() {
        snakeBody.apply {
            add(Position(10, 10))
            add(Position(11, 10))
            add(Position(12, 10))
            add(Position(13, 10))
        }.also {
            body.value = it
        }
        generateApple()
        fixedRateTimer("timer", true, 500, 500) {
            //蛇移動
            val nextPosition = snakeBody.first().copy().apply {
                when (direction) {
                    Direction.LEFT -> x--
                    Direction.RIGHT -> x++
                    Direction.TOP -> y--
                    Direction.DOWN -> y++
                }
                //判斷是否撞牆
                if (snakeBody.contains(this) || x < 0 || x >= 20 || y < 0 || y >= 20 ) {
                    cancel()
                    gameState.postValue(GameState.GAMEOVER)
                }
            }
            snakeBody.add(0, nextPosition)

            if (nextPosition != applePosition) {
                //沒吃
                snakeBody.removeLast()
            } else {
                myPoint =+100
                score.postValue(myPoint)
                //吃了產生下一個
                generateApple()
            }
            body.postValue(snakeBody)
        }
    }

    fun generateApple() {
        //0~19
        //碰到身體
        val spots = mutableListOf<Position>().apply {
            for (i in 0..19) {
                for (j in 0..19)
                {
                    add(Position(i, j))
                }
            }
        }
        spots.removeAll(snakeBody)
        //打亂
        spots.shuffle()
        applePosition = spots[0]

        apple.postValue(applePosition!!)
    }

    fun reset() {
        snakeBody.removeAll(snakeBody)
        start()
    }

    fun  move(dir: Direction) {
        direction = dir
    }


}

data class Position(var x: Int, var y :Int)

enum class Direction {
    TOP, DOWN, LEFT, RIGHT
}

enum class GameState {
    ONGOING, GAMEOVER
}