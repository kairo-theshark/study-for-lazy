package com.artem.studyforlazy.alarm

import kotlin.random.Random

data class MathProblem(val expression: String, val answer: Int)

object MathChallenge {

    fun generate(count: Int = 2): List<MathProblem> = List(count) { randomProblem() }

    private fun randomProblem(): MathProblem {
        val a = Random.nextInt(10, 99)
        val b = Random.nextInt(10, 99)
        return when (Random.nextInt(3)) {
            0 -> MathProblem("$a + $b", a + b)
            1 -> {
                val (big, small) = if (a >= b) a to b else b to a
                MathProblem("$big − $small", big - small)
            }
            else -> {
                val x = Random.nextInt(2, 12)
                val y = Random.nextInt(2, 12)
                MathProblem("$x × $y", x * y)
            }
        }
    }
}
