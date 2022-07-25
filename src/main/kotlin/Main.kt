import java.time.Clock
import kotlin.random.Random

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
