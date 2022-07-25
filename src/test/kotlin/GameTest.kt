import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import kotlin.random.Random
import kotlin.test.Test

class GameTest {
    private val cases = listOf(
        Pair(Action.PAPER, Action.PAPER) to MatchResult.DRAW,
        Pair(Action.PAPER, Action.ROCK) to MatchResult.WIN,
        Pair(Action.PAPER, Action.SCISSOR) to MatchResult.LOSS,
        Pair(Action.ROCK, Action.PAPER) to MatchResult.LOSS,
        Pair(Action.ROCK, Action.ROCK) to MatchResult.DRAW,
        Pair(Action.ROCK, Action.SCISSOR) to MatchResult.WIN,
        Pair(Action.SCISSOR, Action.PAPER) to MatchResult.WIN,
        Pair(Action.SCISSOR, Action.ROCK) to MatchResult.LOSS,
        Pair(Action.SCISSOR, Action.SCISSOR) to MatchResult.DRAW,
    )

    @TestFactory
    fun testPlay() = cases.map { (input, expected) ->
        DynamicTest.dynamicTest("${input.first} vs ${input.second} should produce $expected") {
            Assertions.assertEquals(expected, playMatch(input.first, input.second))
        }
    }

    @TestFactory
    // testing the overloaded variant of `play()` the same way the original was tested via `StaticStrategy`
    fun testPlayStrategies() = cases.map { (input, expected) ->
        DynamicTest.dynamicTest("${input.first} vs ${input.second} should produce $expected") {
            Assertions.assertEquals(expected, playMatch(StaticStrategy(input.first), StaticStrategy(input.second)))
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

    @TestFactory
    fun testPlayGame() = listOf(
        Pair(Action.ROCK, Action.ROCK) to GameResults(0, 0, 0),
        Pair(Action.ROCK, Action.ROCK) to GameResults(100, 0, 0),
        Pair(Action.ROCK, Action.SCISSOR) to GameResults(200, 200, 0),
        Pair(Action.ROCK, Action.PAPER) to GameResults(300, 0, 300)
    ).map { (input, expected) ->
        DynamicTest.dynamicTest(
            "StaticStrategy(${input.first}) vs StaticStrategy(${input.second}) " +
                    "${expected.rounds} times should result in ${expected.player1wins} wins and ${expected.draws} draws for player 1"
        ) {
            Assertions.assertEquals(
                expected,
                playGame(StaticStrategy(input.first), StaticStrategy(input.second), expected.rounds)
            )
        }
    }
}