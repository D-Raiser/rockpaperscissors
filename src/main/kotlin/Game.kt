import kotlin.random.Random

interface Strategy {
    fun getNextAction(): Action
}

enum class MatchResult {
    WIN, DRAW, LOSS
}

enum class Action {
    ROCK, PAPER, SCISSOR
}

data class GameResults(val rounds: Int, val player1wins: Int, val player2wins: Int) {
    val draws = rounds - player1wins - player2wins;
}

fun playGame(player1Strategy: Strategy, player2Strategy: Strategy, rounds: Int): GameResults {
    var player1wins = 0
    var player2wins = 0
    for (i in 1..rounds) {
        when (playMatch(player1Strategy, player2Strategy)) {
            MatchResult.WIN -> player1wins++
            MatchResult.LOSS -> player2wins++
            else -> {
                // do nothing
            }
        }
    }

    return GameResults(rounds, player1wins, player2wins)
}

fun playMatch(strategy1: Strategy, strategy2: Strategy): MatchResult =
    playMatch(strategy1.getNextAction(), strategy2.getNextAction())

// returns the result of playing `action1` vs `action2` from the perspective of `action1`
fun playMatch(action1: Action, action2: Action): MatchResult {
    if (action1 == action2) return MatchResult.DRAW
    val wins = when (action1) {
        Action.ROCK -> action2 == Action.SCISSOR
        Action.PAPER -> action2 == Action.ROCK
        Action.SCISSOR -> action2 == Action.PAPER
    }
    return if (wins) MatchResult.WIN else MatchResult.LOSS
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