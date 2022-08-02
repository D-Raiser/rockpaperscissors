import kotlin.random.Random

enum class MatchResult {
    WIN, DRAW, LOSS
}

enum class Action(val winsVs: () -> Set<Action>) {
    ROCK({ setOf(SCISSOR, LIZARD) }),
    PAPER({ setOf(ROCK, SPOCK) }),
    SCISSOR({ setOf(PAPER, LIZARD) }),
    LIZARD({ setOf(PAPER, SPOCK) }),
    SPOCK({ setOf(ROCK, SCISSOR) });
}

data class GameResults(val rounds: Int, val player1wins: Int, val player2wins: Int) {
    val draws = rounds - player1wins - player2wins
}

fun playGame(player1Strategy: () -> Action, player2Strategy: () -> Action, rounds: Int): GameResults {
    assert(rounds > 0)

    var player1wins = 0
    var player2wins = 0
    repeat(rounds) {
        when (playMatch(player1Strategy, player2Strategy)) {
            MatchResult.WIN -> player1wins++
            MatchResult.LOSS -> player2wins++
            else -> {} // do nothing
        }
    }

    return GameResults(rounds, player1wins, player2wins)
}

// returns the result of playing `action1` vs `action2` from the perspective of `action1`
fun playMatch(strategy1: () -> Action, strategy2: () -> Action): MatchResult {
    val action1 = strategy1()
    return when (strategy2()) {
        action1 -> MatchResult.DRAW
        in action1.winsVs() -> MatchResult.WIN
        else -> MatchResult.LOSS
    }
}

val randomStrategy = { Action.values().random(Random) }
