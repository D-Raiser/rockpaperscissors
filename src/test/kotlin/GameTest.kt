import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import kotlin.random.Random
import kotlin.test.Test

// run `mvn clean test`
class GameTest {
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

    @TestFactory
    fun testStaticStrategy() = Action.values().map { action ->
        DynamicTest.dynamicTest("Static strategy for $action should always return $action") {
            val strategy = StaticStrategy(action)
            for (i in 1..1000) {
                Assertions.assertEquals(action, strategy.getNextAction())
            }
        }
    }

    @Test
    fun `RandomStrategy should behave according to the underlying random number generator`() {
        val actions = Action.values()

        var rnd = 0
        val random = mock<Random> {
            on { nextInt(actions.size) } doAnswer { rnd }
        }

        val strategy = RandomStrategy(random)
        for (i in 1..1000) {
            Assertions.assertEquals(actions[rnd], strategy.getNextAction(), "actions didn't match for index $rnd")
            rnd = (rnd + 1) % actions.size
        }
    }
}