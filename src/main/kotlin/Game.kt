import kotlin.random.Random

interface Strategy {
    fun getNextAction(): Action
}

enum class Result {
    WIN, DRAW, LOSS
}

enum class Action {
    ROCK, PAPER, SCISSOR
}

fun play(strategy1: Strategy, strategy2: Strategy): Result = play(strategy1.getNextAction(), strategy2.getNextAction())

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

class StaticStrategy(private val action: Action) : Strategy {
    override fun getNextAction() = action
}

class RandomStrategy(private val random: Random) : Strategy {
    override fun getNextAction(): Action {
        val actions = Action.values()
        return actions[random.nextInt(actions.size)]
    }
}