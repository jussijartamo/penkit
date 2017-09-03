package penkit

import spark.Spark.staticFileLocation
import spark.Spark.*

fun main(args: Array<String>) {
    staticFileLocation("/public")
    webSocket("/chat", Socket(null))
    init()
}