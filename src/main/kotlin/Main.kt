import kotlin.random.Random

fun main() {
    val player1Strategy = RandomStrategy(Random)
    val player2Strategy = StaticStrategy(Action.ROCK)

    val gameResult = playGame(player1Strategy, player2Strategy, 100)

    println("Played ${gameResult.rounds} games")
    println("Player 1 wins: ${gameResult.player1wins}")
    println("Player 2 wins: ${gameResult.player2wins}")
    println("Draws: ${gameResult.draws}")
}
