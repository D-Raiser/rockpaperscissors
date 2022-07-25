import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

// run `mvn test`
class MainTest {
    @TestFactory
    fun testPlay() = listOf(
        Pair(Action.PAPER, Action.PAPER) to Result.DRAW,
        Pair(Action.PAPER, Action.ROCK) to Result.WIN,
        Pair(Action.PAPER, Action.SCISSOR) to Result.LOSS,
        Pair(Action.ROCK, Action.PAPER) to Result.LOSS,
        Pair(Action.ROCK, Action.ROCK) to Result.DRAW,
        Pair(Action.ROCK, Action.SCISSOR) to Result.WIN,
        Pair(Action.SCISSOR, Action.PAPER) to Result.WIN,
        Pair(Action.SCISSOR, Action.ROCK) to Result.LOSS,
        Pair(Action.SCISSOR, Action.SCISSOR) to Result.DRAW,
    ).map { (input, expected) ->
        DynamicTest.dynamicTest("${input.first} vs ${input.second} should produce $expected") {
            Assertions.assertEquals(expected, play(input.first, input.second))
        }
    }
}