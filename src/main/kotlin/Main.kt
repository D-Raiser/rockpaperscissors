import java.time.Clock
import kotlin.random.Random

enum class Result {
    WIN, DRAW, LOSS
}

enum class Action {
    ROCK,
    PAPER,
    SCISSOR
}

const val games = 100

// run `mvn clean compile exec:java`
fun main() {
    val player1Strategy = RandomStrategy(Random(Clock.systemUTC().millis()))
    val player2Strategy = StaticStrategy(Action.ROCK)

    var player1wins = 0
    var player2wins = 0
    for(i in 1..games) {
        when(play(player1Strategy, player2Strategy)) {
            Result.WIN -> player1wins++
            Result.LOSS -> player2wins++
        }
    }

    println("Played $games games")
    println("Player 1 wins: $player1wins")
    println("Player 2 wins: $player2wins")
    println("Draws: ${games-player1wins-player2wins}")
}

fun play(strategy1: Strategy, strategy2: Strategy) : Result = play(strategy1.getNextAction(), strategy2.getNextAction())

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