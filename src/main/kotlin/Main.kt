import switch_operator.switch

fun main(args: Array<String>) {
    switch("a") {
        case("b") {
            println("a is b")
        }
        case("c") {
            println("a is c")
        }
        default {
            println("a is a")
        }
    }
}