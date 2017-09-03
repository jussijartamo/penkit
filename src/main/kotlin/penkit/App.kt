package penkit

import penkit.db.*
import spark.Spark.staticFileLocation
import spark.Spark.*

val PORT = "PORT"

fun start(config: PostgresConfig) {
    val db: DataAccessLayer by lazy {
        CacheDataAccessLayer(PostgreDataAccessLayer(config))
    }
    val socketHandler = Socket(db)
    port(System.getenv(penkit.PORT).toInt())
    staticFileLocation("/public")
    webSocket("/chat", socketHandler)
    init()
}

fun main(args: Array<String>) {
    start(HerokuPostgresConfig.postgresConfigFromEnv())
}