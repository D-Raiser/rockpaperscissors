import kotlin.random.Random

interface Strategy {
    fun getNextAction() : Action
}

class StaticStrategy(private val action: Action) : Strategy {
    override fun getNextAction() = action
}

class RandomStrategy(private val random: Random) : Strategy {
    override fun getNextAction(): Action {
        val actions = Action.values()
        return actions[random.nextInt(actions.size)]
    }
}