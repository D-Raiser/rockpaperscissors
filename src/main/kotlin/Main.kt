import kotlin.random.Random

fun main() {
    val gameResult = playGame(randomStrategy , { Action.ROCK }, 100)

    println("Played ${gameResult.rounds} games")
    println("Player 1 wins: ${gameResult.player1wins}")
    println("Player 2 wins: ${gameResult.player2wins}")
    println("Draws: ${gameResult.draws}")
}
