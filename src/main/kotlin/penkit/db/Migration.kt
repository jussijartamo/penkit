package penkit.db

import org.flywaydb.core.Flyway

private val LOCATION = "db"

object Migration {

    fun migrate(url: String, user: String, password: String) {
        val flyway = Flyway()
        flyway.setDataSource(url, user, password)
        flyway.setLocations(LOCATION)
        flyway.migrate()
    }
}