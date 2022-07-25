import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

// run `mvn clean test`
class MainTest {
    private val cases = listOf(
        Pair(Action.PAPER, Action.PAPER) to Result.DRAW,
        Pair(Action.PAPER, Action.ROCK) to Result.WIN,
        Pair(Action.PAPER, Action.SCISSOR) to Result.LOSS,
        Pair(Action.ROCK, Action.PAPER) to Result.LOSS,
        Pair(Action.ROCK, Action.ROCK) to Result.DRAW,
        Pair(Action.ROCK, Action.SCISSOR) to Result.WIN,
        Pair(Action.SCISSOR, Action.PAPER) to Result.WIN,
        Pair(Action.SCISSOR, Action.ROCK) to Result.LOSS,
        Pair(Action.SCISSOR, Action.SCISSOR) to Result.DRAW,
    )

    @TestFactory
    fun testPlay() = cases.map { (input, expected) ->
        DynamicTest.dynamicTest("${input.first} vs ${input.second} should produce $expected") {
            Assertions.assertEquals(expected, play(input.first, input.second))
        }
    }

    @TestFactory
    // testing the overloaded variant of `play()` the same way the original was tested via `StaticStrategy`
    fun testPlayStrategies() = cases.map { (input, expected) ->
        DynamicTest.dynamicTest("${input.first} vs ${input.second} should produce $expected") {
            Assertions.assertEquals(expected, play(StaticStrategy(input.first), StaticStrategy(input.second)))
        }
    }
}