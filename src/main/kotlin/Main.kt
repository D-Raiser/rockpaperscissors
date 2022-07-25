enum class Result {
    WIN, DRAW, LOSS
}

enum class Action {
    ROCK,
    PAPER,
    SCISSOR
}

// run `mvn exec:java`
fun main() {}

// returns the result of playing `action1` vs `action2` from the perspective of `action1`
fun play(action1: Action, action2: Action): Result {
    if (action1 == action2) return Result.DRAW
    val wins = when (action1) {
        Action.ROCK -> action2 == Action.SCISSOR
        Action.PAPER -> action2 == Action.ROCK
        Action.SCISSOR -> action2 == Action.PAPER
    }
    return if (wins) Result.WIN else Result.LOSS
}