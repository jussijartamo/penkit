package penkit

import org.flywaydb.core.Flyway
import penkit.db.Migration
import penkit.db.PostgreDataAccessLayer
import penkit.db.PostgresConfig
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres

data class PostgreConfigForTesting(
        val host: String = "localhost",
        val port: Int  =5432,
        val db: String = "dbName",
        val user: String = "userName",
        val password: String = "password"
)

val CONFIG = PostgreConfigForTesting()

object PostgreForTesting {
    fun url(): String = "jdbc:postgresql://${CONFIG.host}:${CONFIG.port}/${CONFIG.db}"

    fun dao(): PostgreDataAccessLayer {
        return PostgreDataAccessLayer(PostgresConfig(url(), CONFIG.user, CONFIG.password))
    }

    fun cleanAndMigrate() {
        val flyway = Flyway()
        flyway.setDataSource(url(), CONFIG.user, CONFIG.password)
        flyway.clean()
        Migration.migrate(url(), CONFIG.user, CONFIG.password)
    }

    fun startEmbeddedPostgreSQL(): EmbeddedPostgres {
        val postgres = EmbeddedPostgres()
        val url = postgres.start(CONFIG.host, CONFIG.port, CONFIG.db, CONFIG.user, CONFIG.password)
        return postgres
    }
}

fun main(args: Array<String>) {
    val postgres = PostgreForTesting.startEmbeddedPostgreSQL()
    Thread.sleep(100000000)
    postgres.stop()
}