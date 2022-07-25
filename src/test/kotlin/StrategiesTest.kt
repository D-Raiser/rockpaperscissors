import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import kotlin.random.Random
import kotlin.test.Test

class StrategiesTest {
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
        for(i in 1..1000) {
            Assertions.assertEquals(actions[rnd], strategy.getNextAction(), "actions didn't match for index $rnd")
            rnd = (rnd+1)%actions.size
        }
    }
}