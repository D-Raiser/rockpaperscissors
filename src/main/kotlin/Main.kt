enum class Result {
    WIN, DRAW, LOSS
}

enum class Action {
    ROCK,
    PAPER,
    SCISSOR
}

// run `mvn exec:java`
fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}

// retuns the result of playing `action1` vs `action2` from the perspective of `action1`
fun play( action1: Action, action2: Action) : Result {
    TODO()
}